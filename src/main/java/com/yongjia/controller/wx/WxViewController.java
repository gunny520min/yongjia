package com.yongjia.controller.wx;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yongjia.controller.web.BaseController;
import com.yongjia.dao.AppointmentAndMemberMapper;
import com.yongjia.dao.GiftMapper;
import com.yongjia.dao.MemberPointMapper;
import com.yongjia.dao.MemberSignMapper;
import com.yongjia.dao.MessageMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.PotentialCustomerAndMemberMapper;
import com.yongjia.dao.PotentialCustomerMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserAndMemberMapper;
import com.yongjia.model.Appointment;
import com.yongjia.model.AppointmentAndMember;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarHallPic;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.Gift;
import com.yongjia.model.Member;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.MemberSign;
import com.yongjia.model.Message;
import com.yongjia.model.PointPool;
import com.yongjia.model.PotentialCustomer;
import com.yongjia.model.PotentialCustomerAndMember;
import com.yongjia.model.User;
import com.yongjia.model.WxMsgItem;
import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.model.WxUserEmail;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/view")
public class WxViewController extends BaseController {

    private static Logger log = Logger.getLogger(WxViewController.class);

    @Autowired
    WxUserAndMemberMapper wxUserAndMemberMapper;

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MemberSignMapper memberSignMapper;

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private MemberPointMapper memberPointMapper;

    @Autowired
    private AppointmentAndMemberMapper appointmentAndMemberMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private PotentialCustomerAndMemberMapper potentialCustomerAndMemberMapper;

    @Autowired
    private PotentialCustomerMapper potentialCustomerMapper;
    
    

    /**
     * 我的首页
     * 
     * @param model
     * @param openid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/mineHome")
    public String mineHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        model.addAttribute("wxUser", wxUserAndMember);

        User user = userMapper.selectByOpenid(openid);
        model.addAttribute("saler", user);
        return "weixin/mineHome";
    }

    /**
     * 用户注册
     * 
     * @param model
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "weixin/wxuserRegister";
    }

    /**
     * 查看用户信息
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/wxuserInfo")
    public String wxuserInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        model.addAttribute("wxUser", wxUserAndMember);
        return "/weixin/wxuserInfo";
    }

    /**
     * 验证为车主
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/verifyCarOwner")
    public String verifyCarOwner(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        model.addAttribute("wxUser", wxUserAndMember);
        return "weixin/verifyCarOwner";
    }

    /**
     * 添加车
     * 
     * @param model
     * @return
     */
    @RequestMapping("/addCar")
    public String memberAddCar(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "weixin/addCar";
    }

    /**
     * 签到页面
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/sign")
    public String sign(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        MemberSign memberSign = memberSignMapper.selectByMemberId(memberId);
        model.addAttribute("memberSign", memberSign);
        return "sign";
    }

    /**
     * 积分换礼
     * 
     * @param model
     * @return
     */
    // TODO selectAll or tobe json
    @RequestMapping("/gift")
    public String gift(Model model, HttpServletRequest request, HttpServletResponse response) {
        // Long totalCount = giftMapper.countByNameAndStatus(null, Gift.StatusPubnish);
        // List<Gift> giftList = null;
        // if (totalCount > 0) {
        // giftList = giftMapper.selectByNameAndStatus(null, Gift.StatusPubnish, getPageMap(pageNo, pageSize));
        // }
        return "gift";
    }

    /**
     * 礼物详情
     * 
     * @param model
     * @param giftId
     * @return
     */
    @RequestMapping("/giftDetail")
    public String sign(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        Gift gift = giftMapper.selectByPrimaryKey(id);
        model.addAttribute("gift", gift);
        return "giftDetail";
    }

    /**
     * 如何赚取积分界面
     * 
     * @param model
     * @return
     */
    @RequestMapping("/pointNotice")
    public String pointNotice(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "pointNotice";
    }

    /**
     * 我的积分
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myPoint")
    public String myPoint(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);

        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberId, pointPoolId);
        model.addAttribute("memberPoint", memberPoint);

        return "myPoint";
    }

    /**
     * 我的预约
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myAppointment")
    public String myAppointment(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        List<AppointmentAndMember> appointmentList = appointmentAndMemberMapper.selectByMemberId(memberId);
        model.addAttribute("appointmentList", appointmentList);
        return "myAppointment";
    }

    /**
     * 资讯吧
     * 
     * @param model
     * @return
     */
    @RequestMapping("/news")
    public String news(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "news";
    }

    /**
     * 资讯详情
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/newsDetail")
    public String newsDetail(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        Message message = messageMapper.selectByPrimaryKey(id);
        model.addAttribute("newsDetail", message);

        return "newsDetail";
    }

    /**
     * 我的消息
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myEmail")
    public String myEmail(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "myEmail";
    }

    /**
     * 销售登录
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerLogin")
    public String salerLogin(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "salerLogin";
    }

    /**
     * 销售重置密码
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerResetPwd")
    public String salerResetPwd(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "salerResetPwd";
    }

    /**
     * 抢客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/grabCustomer")
    public String grabCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {
        
        return "grabCustomer";
    }

    /**
     * 我的客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomer")
    public String salerCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {
        
        return "salerCustomer";
    }

    /**
     * 客户详情
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomerDetail")
    public String salerCustomerDetail(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        PotentialCustomerAndMember potentialCustomer = potentialCustomerAndMemberMapper.selectById(id);
        model.addAttribute("potentialCustomer", potentialCustomer);
        return "salerCustomerDetail";
    }

    /**
     * 车管家首页
     * 
     * @param model
     * @return
     */
    @RequestMapping("/carHome")
    public String carHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Message> messageList = messageMapper.selectManager();
        model.addAttribute("activity", messageList);
        return "carHome";
    }

    /**
     * 道路救援
     * 
     * @param model
     * @return
     */
    @RequestMapping("/roadRescue")
    public String roadRescue(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "roadRescue";
    }

    /**
     * 预约首页
     * 
     * @param model
     * @return
     */
    @RequestMapping("/appointmentHome")
    public String appointmentHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "appointmentHome";
    }

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping("/appointmentDetail")
    public String appointmentDetail(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        AppointmentAndMember appointmentAndMember = appointmentAndMemberMapper.selectById(id);
        return "appointmentDetail";
    }

    /**
     * 购车计划首页
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/buycarHome")
    public String buycarHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        List<PotentialCustomer> potentialCustomers = potentialCustomerMapper.selectByMemberId(memberId);
        model.addAttribute("potentialCustomers", potentialCustomers);

        return "buycarHome";
    }

    /**
     * 新增购车计划
     * 
     * @param model
     * @return
     */
    @RequestMapping("/buycarAdd")
    public String buycarAdd(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "buycarAdd";
    }

    @RequestMapping("/hallHome")
    public String hallHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("hall", new CarHall());
        return "hallHome";
    }

    @RequestMapping("/hallDetail")
    public String hallDetail(Model model, int hallId, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("hall", new CarHall());
        model.addAttribute("hallPic", new CarHallPic());
        model.addAttribute("hallCarModel", new CarModel());
        return "hallDetail";
    }

    @RequestMapping("/carParam")
    public String carParam(Model model, int carModelId, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("hallCarModel", new CarModelParam());
        return "carParam";
    }
}