package com.yongjia.controller.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yongjia.dao.MessageMapper;
import com.yongjia.dao.WxMessageMapper;
import com.yongjia.dao.WxMsgItemMapper;
import com.yongjia.dao.WxRuleKeywordMapper;
import com.yongjia.dao.WxRuleMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.WxMenu;
import com.yongjia.model.WxMessage;
import com.yongjia.model.WxMsgItem;
import com.yongjia.model.WxRule;
import com.yongjia.model.WxRuleKeyword;
import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserList;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.DataUtils;
import com.yongjia.wxkit.WeiXin;
import com.yongjia.wxkit.bean.TypeBean;
import com.yongjia.wxkit.bean.WeiXinBean;
import com.yongjia.wxkit.utils.ParamString;
import com.yongjia.wxkit.utils.WeixinUtil;
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
@RequestMapping("/wx/api")
public class WeixinController extends WxBaseController {

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
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @RequestMapping("")
    @ResponseBody
    public void api(WeiXinBean weixinBean, HttpServletRequest request, HttpServletResponse response) {
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

//                    pubnishMenu(request, response);
                    // getOldUserList();
                    return;
                }
                acceptMsg(request, response);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping("menu")
    @ResponseBody
    public void pubnishMenu(HttpServletRequest request, HttpServletResponse response) {
        taskExecutor.execute(new PubnishMenuTask(request, response));
    }

    @RequestMapping("getWxUserList")
    @ResponseBody
    public void getOldUserList(HttpServletRequest request, HttpServletResponse response) {
        taskExecutor.execute(new getOldWxUserTask(wxUserMapper));
    }

    @RequestMapping("/openid")
    public String openid(Model model, String code, String page, HttpServletRequest request, HttpServletResponse response) {
        log.info("get openid start :" + request.getRequestURL().toString());
        String openid = WeixinUtil.getOpenid(AppID, AppSecret, code);
        String redirectUrl = "";
        try {
            redirectUrl = URLDecoder.decode(page, WxPropertiesUtil.getProperty(ParamString.ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put(CookieUtil.OPEN_ID, openid);
        CookieUtil.setIdentity(request, response, params, 0);

        WxUser wxUser = wxUserMapper.selectByPrimaryKey(openid);
        model.addAttribute("wxUser", wxUser);
        return redirectUrl;
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
                // 不作处理,使用默认回复
            } else if (msg instanceof WxRecvLinkMsg) {
                // 不作处理,使用默认回复
            } else if (msg instanceof WxRecvPicMsg) {
                // 不作处理,使用默认回复
            }

            // 发送回微信
            log.info("回复消息：");
            log.info(JSONObject.fromObject(sendMsg).toString());
            WeiXin.send(sendMsg, response);
        } catch (JDOMException e) {
            log.info("jdom error");
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.info("io error");
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
    private WxSendMsg obtainContent(WxSendMsg sendMsg, Long ruleId) {

        WxRule rule = wxRuleMapper.selectByPrimaryKey(ruleId);
        WxMessage wxMessage = wxMessageMapper.selectByPrimaryKey(rule.getMsgId());
        List<WxMsgItem> wxMsgItemList = wxMsgItemMapper.selectByWxMsgId(wxMessage.getId());
        // TODO 回复消息内容
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
                sendNewsMsg.addItem(wxMsgItemList.get(i).getTitle(), wxMsgItemList.get(i).getDescription(),
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
     * @param @param sendMsg
     * @param @param msg
     * @return void
     * @throws
     * @Date 2014年3月6日 下午5:54:06
     */
    private WxSendMsg dealTextMsg(WxSendMsg sendMsg, WxRecvMsg msg) {
        String keyword = ((WxRecvTextMsg) msg).getContent();
        WxRuleKeyword ruleKeyword = wxRuleKeywordMapper.selectByKeyword(keyword);
        if (ruleKeyword != null) {
            WxRule rule = wxRuleMapper.selectByPrimaryKey(ruleKeyword.getRuleId());
            if (rule != null) {
                sendMsg = obtainContent(sendMsg, rule.getId());
            } else {
                sendMsg = null;
            }
        } else {
            sendMsg = null;
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
            // TODO 多次关注是否记录关注来源
        }

    }

    /**
     * 
     * @param openid
     */
    private void unSubscribeWxUser(String openid) {
        WxUser wxuser = wxUserMapper.selectByPrimaryKey(openid);
        if (wxuser != null) {
            wxuser.setSubscribe(0);
            wxUserMapper.updateByPrimaryKey(wxuser);
        }
    }

    /**
     * 关注后获取用户信息
     * 
     * @ClassName: SubscribeTask
     * @author Comsys-guj
     * @date 2015年6月15日 上午11:36:12
     * 
     */
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
            String token = WeixinUtil.getToken(AppID, AppSecret);
            WxUser wxUser = WeixinUtil.getWxUserInfo(token, openid);
            if (wxUser != null) {
                saveWxUser(wxUser, ticket);
            }
        }
    }

    private String menuUrlFormat(HttpServletRequest request, HttpServletResponse response, String menuUrl) {
        String url = "";
        try {
            String page = URLEncoder.encode(menuUrl, WxPropertiesUtil.getProperty(ParamString.ENCODING));
            url = WeixinUtil.getCode(AppID, "http://peon.cn/wx/openid?page=" + page, ParamString.SCOPE_SNSAPI_BASE,
                    "123");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * 发布菜单
     * 
     * @ClassName: PubnishMenuTask
     * @author Comsys-guj
     * @date 2015年6月15日 上午11:57:05
     * 
     */
    private class PubnishMenuTask implements Runnable {

        private HttpServletRequest request;
        private HttpServletResponse response;

        public PubnishMenuTask(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        public void run() {
            String accessToken = WeixinUtil.getToken(AppID, AppSecret);

            List<WxMenu> menuList = new ArrayList<WxMenu>();
            WxMenu wxMenu = new WxMenu();
            wxMenu.setType("view");
            wxMenu.setName("我的");
            wxMenu.setUrl(menuUrlFormat(request, response, "weixin/managerHome"));
            menuList.add(wxMenu);
            wxMenu = new WxMenu();
            wxMenu.setType("view");
            wxMenu.setName("车管家");
            wxMenu.setUrl("http://yjstatic.tlan.com.cn/weixin/keeper.html");
            menuList.add(wxMenu);
            wxMenu = new WxMenu();
            wxMenu.setType("view");
            wxMenu.setName("展厅");
            wxMenu.setUrl("http://yjstatic.tlan.com.cn/weixin/styles.html");
            menuList.add(wxMenu);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("button", menuList);
            String menuJson = JSONObject.fromObject(map).toString();
            log.info("access token is " + accessToken);
            log.info("menu json is " + menuJson);

            if (WeixinUtil.generatorMenu(accessToken, menuJson)) {
                log.info("发布成功");
            } else {
                log.info("发布失败");
            }
        }
    }

    /**
     * 
     * @ClassName: getOldWxUserTask
     * @author Comsys-guj
     * @date 2015年6月15日 上午11:52:21
     * 
     */
    private class getOldWxUserTask implements Runnable {
        private WxUserMapper wxUserMapper;
        private String nextOpenid;
        private List<String> openidList;
        private WxUserList wxUserList;

        public getOldWxUserTask(WxUserMapper wxUserMapper) {
            super();
            this.wxUserMapper = wxUserMapper;
            this.nextOpenid = "";
            this.openidList = new ArrayList<String>();
        }

        public void run() {
            String token = WeixinUtil.getToken(AppID, AppSecret);
            do {

                wxUserList = WeixinUtil.getWxUserList(token, nextOpenid);
                if (wxUserList != null) {
                    if (wxUserList.getData() != null) {
                        openidList.addAll(wxUserList.getData().getOpenid());
                    }
                    nextOpenid = wxUserList.getNext_openid();
                }
                log.info("next openid is " + nextOpenid);
            } while (!TextUtils.isEmpty(nextOpenid));

            for (String openid : openidList) {
                WxUser wxUser = WeixinUtil.getWxUserInfo(token, openid);
                if (wxUser != null) {
                    saveWxUser(wxUser, "old");
                }
            }
        }
    }

}