package com.yongjia.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.WxMessageMapper;
import com.yongjia.dao.WxMsgItemMapper;
import com.yongjia.dao.WxRuleKeywordMapper;
import com.yongjia.dao.WxRuleMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.WxMessage;
import com.yongjia.model.WxMsgItem;
import com.yongjia.model.WxRule;
import com.yongjia.model.WxRuleKeyword;
import com.yongjia.model.WxUser;
import com.yongjia.utils.DataUtils;
import com.yongjia.utils.HttpClientUtil;
import com.yongjia.wxkit.WeiXin;
import com.yongjia.wxkit.bean.TypeBean;
import com.yongjia.wxkit.bean.WeiXinBean;
import com.yongjia.wxkit.http.HttpUtils;
import com.yongjia.wxkit.utils.ParamString;
import com.yongjia.wxkit.utils.WxPropertiesUtil;
import com.yongjia.wxkit.vo.recv.WxRecvEventMsg;
import com.yongjia.wxkit.vo.recv.WxRecvGeoMsg;
import com.yongjia.wxkit.vo.recv.WxRecvLinkMsg;
import com.yongjia.wxkit.vo.recv.WxRecvMsg;
import com.yongjia.wxkit.vo.recv.WxRecvPicMsg;
import com.yongjia.wxkit.vo.recv.WxRecvTextMsg;
import com.yongjia.wxkit.vo.send.WxSendMsg;
import com.yongjia.wxkit.vo.send.WxSendNewsMsg;
import com.yongjia.wxkit.vo.send.WxSendTextMsg;

@Controller
@RequestMapping("/wx")
public class WeixinController {

    /**
     * TODO
     */
    public static final String AppID = WxPropertiesUtil.getProperty("app_id");
    public static final String AppSecret = WxPropertiesUtil.getProperty("app_secret");
    public static final String Token = WxPropertiesUtil.getProperty("app_token");

    private static Logger log = Logger.getLogger(WeixinController.class);

    @Autowired
    private WxRuleKeywordMapper wxRuleKeywordMapper;
    @Autowired
    private WxRuleMapper wxRuleMapper;
    @Autowired
    private WxMessageMapper wxMessageMapper;
    @Autowired
    private WxMsgItemMapper wxMsgItemMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    /**
     * 注入线程池
     */
    private TaskExecutor taskExecutor;

    @RequestMapping("")
    @ResponseBody
    public String api(WeiXinBean weixinBean, HttpServletRequest request, HttpServletResponse response) {
        // 设置token
        weixinBean.setToken(Token);
        // 验证签名
        try {
            if (WeiXin.access(weixinBean)) {
                /************* 微信接入 *************/
                if (DataUtils.isNotNullOrEmpty(weixinBean.getEchostr())) {
                    log.info("微信接入。。。");
                    log.info("sss:" + weixinBean.getEchostr());
                    response.setContentType("text/html; charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.println(weixinBean.getEchostr());
                    return null;
                }
                acceptMsg(request, response);
            }
        } catch (Exception e) {

        }

        return null;
    }

    /**
     * 接收消息处理,并进行回复处理
     * 
     */
    private void acceptMsg(HttpServletRequest request, HttpServletResponse response) {

        WxRecvMsg msg;
        try {
            msg = WeiXin.recv(request.getInputStream());
            WxSendMsg sendMsg = WeiXin.builderSendByRecv(msg);
            log.info("wxopenid");
            log.info(sendMsg.getToUser());

            // 目前支持的消息有(
            // WxRecvTextMsg/文本消息,
            // WxRecvEventMsg/事件消息,
            // WxRecvGeoMsg/地理位置消息,
            // WxRecvLinkMsg/连接消息,
            // WxRecvPicMsg/图片消息)
            if (msg instanceof WxRecvEventMsg) {
                WxRecvEventMsg m = (WxRecvEventMsg) msg;
                String event = m.getEvent().toLowerCase();
                if (event.equals(TypeBean.SUBSCRIBE)) {
                    // 根据欢迎消息规则进行回复
                    // 保存关注用户
                    taskExecutor.execute(new SubscribeTask(sendMsg.getToUser(), m.getTicket(), wxUserMapper));
                } else if (event.equals(TypeBean.UNSUBSCRIBE)) {
                    // 保存取消关注用户
                    unSubscribeWxUser(sendMsg.getToUser());
                } else if (event.equals(TypeBean.CLICK)) {
                    // TODO 理论上没有这种情况
                } else if (event.equals(TypeBean.SCAN)) {

                } else if (event.equals(TypeBean.LOCATION)) {

                }
            } else if (msg instanceof WxRecvTextMsg) {
                // 处理文本消息回复
                sendMsg = dealTextMsg(sendMsg, msg);
            } else if (msg instanceof WxRecvGeoMsg) {
                // 不作处理,使用默认回复 TODO
            } else if (msg instanceof WxRecvLinkMsg) {
                // 不作处理,使用默认回复
            } else if (msg instanceof WxRecvPicMsg) {
                // 不作处理,使用默认回复
            }

            // 发送回微信
            log.info("回复消息：");
            log.info(sendMsg.toDocument().toString());
            WeiXin.send(sendMsg, response.getOutputStream());
        } catch (JDOMException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 根据关键字处理回复消息
     * 
     * @param sendMsg
     * @param key
     * @return
     */
    private WxSendMsg obtainContent(WxSendMsg sendMsg, Integer ruleId) {

        WxRule rule = wxRuleMapper.selectByPrimaryKey(ruleId);
        WxMessage wxMessage = wxMessageMapper.selectByPrimaryKey(rule.getMsgId());
        List<WxMsgItem> wxMsgItemList = wxMsgItemMapper.selectByMsgId(wxMessage.getId());

        if (wxMessage.getMsgType() == 1 && wxMsgItemList.size() == 1) {
            sendMsg = new WxSendTextMsg(sendMsg, wxMsgItemList.get(0).getContent());
        } else {
            WxSendNewsMsg sendNewsMsg = new WxSendNewsMsg(sendMsg);
            for (int i = 0; i < wxMsgItemList.size(); i++) {
                String url = wxMsgItemList.get(i).getUrl();
                // 附带openid
                if (url.contains("?")) {
                    url += "&wxopenid=" + sendMsg.getToUser();
                } else {
                    url += "?wxopenid=" + sendMsg.getToUser();
                }
                sendNewsMsg.addItem(wxMsgItemList.get(i).getTitle(), wxMsgItemList.get(i).getDesc(),
                        wxMsgItemList.get(i).getPic(), url);
            }
            sendMsg = sendNewsMsg;
        }

        return sendMsg;
    }

    /**
     * 处理文本消息回复
     * 
     * @Title: dealTextMsg
     * @Description: TODO
     * @param @param sendMsg
     * @param @param msg
     * @return void
     * @throws
     * @Date 2014年3月6日 下午5:54:06
     */
    private WxSendMsg dealTextMsg(WxSendMsg sendMsg, WxRecvMsg msg) {
        String keyword = ((WxRecvTextMsg) msg).getContent();
        String openid = ((WxRecvTextMsg) msg).getFromUser();
        WxRuleKeyword ruleKeyword = wxRuleKeywordMapper.selectByKeyword(keyword);
        if (ruleKeyword != null) {
            WxRule rule = wxRuleMapper.selectByPrimaryKey(ruleKeyword.getRuleId());
            if (rule != null) {
                sendMsg = obtainContent(sendMsg, rule.getId());
            }
        }
        return sendMsg;
    }

    /**
     * 保存用户信息： 关注
     */
    private void saveWxUser(WxUser wxUser, String ticket) {
        WxUser wxuser2 = wxUserMapper.selectByPrimaryKey(wxUser.getOpenid());
        if (wxuser2 == null) {
            wxUser.setTicket(ticket);
            wxUserMapper.insert(wxUser);
        } else {

        }

    }

    private void unSubscribeWxUser(String openid) {
        WxUser wxuser = wxUserMapper.selectByPrimaryKey(openid);
        if (wxuser != null) {
            wxuser.setSubscribe(0);
            wxUserMapper.updateByPrimaryKey(wxuser);
        }
    }

    private class SubscribeTask implements Runnable {
        private WxUserMapper wxUserMapper;
        private String openid;
        private String ticket;

        public SubscribeTask(String openid, String ticket, WxUserMapper wxUserMapper) {
            super();
            this.openid = openid;
            this.ticket = ticket;
            this.wxUserMapper = wxUserMapper;
        }

        public void run() {
            String token = HttpUtils.getToken(AppID, AppSecret);
            String url = WxPropertiesUtil.getProperty(ParamString.WX_USER_INFO);
            url = url.replace("{1}", token);
            url = url.replace("{2}", openid);
            String res = HttpClientUtil.sendGetRequest(url, WxPropertiesUtil.getProperty(ParamString.ENCODING));
            if (res.contains("errcode")) {
                log.error("获取用户信息异常：" + res);
            } else {
                WxUser wxUser = (WxUser) JSONObject.toBean(JSONObject.fromObject(res), WxUser.class);
                saveWxUser(wxUser, ticket);
            }
        }
    }

}