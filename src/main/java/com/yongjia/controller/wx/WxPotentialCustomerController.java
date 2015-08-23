package com.yongjia.controller.wx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.BaseController;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.PotentialCustomerAndMemberMapper;
import com.yongjia.dao.PotentialCustomerMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.PotentialCustomer;
import com.yongjia.model.PotentialCustomerAndMember;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;
import com.yongjia.wxkit.template.uitls.TplMessageUtil;
import com.yongjia.wxkit.utils.ParamString;
import com.yongjia.wxkit.utils.WeixinUtil;
import com.yongjia.wxkit.utils.WxPropertiesUtil;

@Controller
@RequestMapping("/wx/pCustomer")
public class WxPotentialCustomerController extends WxBaseController {

    private static Logger log = Logger.getLogger(WxPotentialCustomerController.class);

    @Autowired
    private PotentialCustomerMapper potentialCustomerMapper;

    @Autowired
    private PotentialCustomerAndMemberMapper potentialCustomerAndMemberMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 注入线程池
     */
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    /**
     * type 0 注册
     * 
     * @param mobile
     * @param type
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(PotentialCustomer pCustomer, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        pCustomer.setMemberId(memberId);
        Long now = System.currentTimeMillis();
        pCustomer.setCreateAt(now);
        pCustomer.setUpdateAt(now);
        if (potentialCustomerMapper.insertSelective(pCustomer) > 0) {
            /**
             * 发送抢客户通知
             */
            taskExecutor.execute(new SendManagerNotifyTask(pCustomer));

            return ToJsonUtil.toEntityMap(200, "success", null);
        }

        return ToJsonUtil.toEntityMap(400, "添加失败", null);
    }

    @RequestMapping("/grab")
    @ResponseBody
    public Map grab(Long pCustomerId, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        String userName = CookieUtil.getUserName(request);
        if (userId==null) {
            String openid = CookieUtil.getOpenid(request);
            User user = userMapper.selectByOpenid(openid);
            userId = user.getId();
            userName = user.getName();
        }
        PotentialCustomer pCustomer = potentialCustomerMapper.selectByPrimaryKey(pCustomerId);
        if (pCustomer.getServiceBy() == null || pCustomer.getServiceBy() <= 0) {
            pCustomer.setServiceBy(userId);
            pCustomer.setServiceByName(userName);
            Long now = System.currentTimeMillis();
            pCustomer.setUpdateAt(now);
            if (potentialCustomerMapper.grabCustomer(pCustomer) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            }
        }
        return ToJsonUtil.toEntityMap(400, "客户已经被抢走了", null);
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        if (userId==null) {
            String openid = CookieUtil.getOpenid(request);
            User user = userMapper.selectByOpenid(openid);
            userId = user.getId();
        }
        Long totalCount = potentialCustomerAndMemberMapper.countByUserId(userId);
        List<PotentialCustomerAndMember> potentialCustomers = null;
        if (totalCount > 0) {
            potentialCustomers = potentialCustomerAndMemberMapper.selectByUserId(userId, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                potentialCustomers);
    }

    /**
     * 发送管家通知tpl
     * 
     * @ClassName: SendManagerNotifyTask
     * @Description: TODO
     * @author Comsys-guj
     * @date 2015年8月13日 下午10:27:13
     * 
     */
    private class SendManagerNotifyTask implements Runnable {

        private PotentialCustomer pCustomer;

        public SendManagerNotifyTask(PotentialCustomer pCustomer) {
            this.pCustomer = pCustomer;
        }

        public void run() {
            List<User> salerList = userMapper.selectAllSaler();
            Member member = memberMapper.selectByPrimaryKey(pCustomer.getMemberId());
            for (User user : salerList) {
                TplMessageUtil.sendManagerNotifyTpl(user.getOpenid(), "新客户提醒", user.getName(),
                        "有客户 " + member.getName() + " 提交购车计划", menuUrlFormat("/wx/view/salerCustomerDetail?id="
                                + pCustomer.getId()));
            }
        }
    }

    private String menuUrlFormat(String menuUrl) {
        String url = "";
        try {
            String page = URLEncoder.encode(menuUrl, WxPropertiesUtil.getProperty(ParamString.ENCODING));
            url = WeixinUtil.getCode(AppID, "http://yongjia.tlan.com.cn/wx/api/openid?page=" + page,
                    ParamString.SCOPE_SNSAPI_BASE, "123");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }
}