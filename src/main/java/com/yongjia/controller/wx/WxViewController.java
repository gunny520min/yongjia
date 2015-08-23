package com.yongjia.controller.wx;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.yongjia.controller.web.WebBaseController;
import com.yongjia.dao.AppointmentAndMemberMapper;
import com.yongjia.dao.CarHallMapper;
import com.yongjia.dao.CarHallModelMapper;
import com.yongjia.dao.CarHallPicMapper;
import com.yongjia.dao.CarModelMapper;
import com.yongjia.dao.GiftMapper;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.MemberPointMapper;
import com.yongjia.dao.MemberPointRecordMapper;
import com.yongjia.dao.MemberSignMapper;
import com.yongjia.dao.MemberSignRecordMapper;
import com.yongjia.dao.MessageMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.PotentialCustomerAndMemberMapper;
import com.yongjia.dao.PotentialCustomerMapper;
import com.yongjia.dao.SignPointConfigMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserAndMemberMapper;
import com.yongjia.model.AppointmentAndMember;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarHallModel;
import com.yongjia.model.CarHallPic;
import com.yongjia.model.CarModel;
import com.yongjia.model.Gift;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.MemberSign;
import com.yongjia.model.MemberSignRecord;
import com.yongjia.model.Message;
import com.yongjia.model.PointPool;
import com.yongjia.model.PotentialCustomer;
import com.yongjia.model.PotentialCustomerAndMember;
import com.yongjia.model.SignPointConfig;
import com.yongjia.model.User;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.DateUtil;
import com.yongjia.utils.StringUtils;

@Controller
@RequestMapping("/wx/view")
public class WxViewController extends WebBaseController {

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
    private MemberPointRecordMapper memberPointRecordMapper;

    @Autowired
    private AppointmentAndMemberMapper appointmentAndMemberMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private PotentialCustomerAndMemberMapper potentialCustomerAndMemberMapper;

    @Autowired
    private PotentialCustomerMapper potentialCustomerMapper;

    @Autowired
    private CarHallMapper carHallMapper;

    @Autowired
    private CarHallModelMapper carHallModelMapper;

    @Autowired
    private CarHallPicMapper carHallPicMapper;

    @Autowired
    private CarModelMapper carModelMapper;

    @Autowired
    private SignPointConfigMapper signPointConfigMapper;

    @Autowired
    private MemberSignRecordMapper memberSignRecordMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @Autowired
    private MemberMapper memberMapper;

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("shost", "http://yjstatic.tlan.com.cn");
    }

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
        model.addAttribute("wxuser", wxUserAndMember);

        User user = userMapper.selectByOpenid(openid);
        model.addAttribute("user", user);

        if (user != null) {
            Map<String, String> params = CookieUtil.getIdentity(request);
            params.put(CookieUtil.OPEN_ID, openid);
            if (wxUserAndMember != null && wxUserAndMember.getId() != null) {
                params.put(CookieUtil.MEMBER_ID, wxUserAndMember.getId() + "");
            }
            params.put(CookieUtil.USER_ID, user.getId() + "");
            params.put(CookieUtil.USER_NAME, user.getName());
            CookieUtil.setIdentity(request, response, params, 0);
        }
        return "weixin/mineHome";
    }

    /**
     * 编辑用户信息
     * 
     * @param model
     * @return
     */
    @RequestMapping("/wxuserEdit")
    public String register(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        model.addAttribute("wxuser", wxUserAndMember);
        return "weixin/wxuserEdit";
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
        model.addAttribute("wxuser", wxUserAndMember);
        return "/weixin/wxuserInfo";
    }

    /**
     * 验证为车主
     * 
     * @param model
     * @param openid
     * @return
     */
    // @RequestMapping("/verifyCarOwner")
    // public String verifyCarOwner(Model model, HttpServletRequest request, HttpServletResponse response) {
    // String openid = CookieUtil.getOpenid(request);
    // log.info("openid = " + openid);
    // PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
    // Long pointPoolId = 0L;
    // if (pointPool != null) {
    // pointPoolId = pointPool.getId();
    // }
    // WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
    // model.addAttribute("wxuser", wxUserAndMember);
    // return "weixin/verifyCarOwner";
    // }

    /**
     * 用户添加车辆
     * 
     * @param model
     * @return
     */
    @RequestMapping("/addCar")
    public String addCar(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "weixin/addCar";
    }

    /**
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/myCars")
    public String myCars(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId, null);
        model.addAttribute("cars", memberCars);
        return "weixin/myCars";
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
        String[] weekName = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Long memberId = CookieUtil.getMemberId(request);
        log.info("memberId = " + memberId);
        Long now = System.currentTimeMillis();
        String month = DateUtil.formatDatetime(new Date(now), "yyyy-M");
        MemberSign memberSign = memberSignMapper.selectByMemberIdAndMonth(memberId, month);
        if (memberSign != null) {
            model.addAttribute("times", memberSign.getTimes());
        } else {
            model.addAttribute("times", 0);

        }
        model.addAttribute("date", new Date(now));
        model.addAttribute("week", weekName[DateUtil.dayOfWeek() - 1]);
        MemberSignRecord memberSignRecord = memberSignRecordMapper.selectByMemberIdAndDate(memberId,
                DateUtil.formatDate(new Date(now)));
        if (memberSignRecord == null) {
            model.addAttribute("status", MemberSign.StatusNotSign);
        } else {
            model.addAttribute("status", MemberSign.StatusSigned);
        }
        log.info("month = " + month);
        List<SignPointConfig> signPointConfigListFromDb = signPointConfigMapper.selectByMonth(month);
        Integer monthDayCount = DateUtil.getDayByMonth(month);
        List<SignPointConfig> signPointConfigList = new ArrayList<SignPointConfig>();
        for (int i = 0; i < monthDayCount; i++) {
            SignPointConfig signPointConfig = new SignPointConfig();
            signPointConfig.setMonth(month);
            signPointConfig.setTimes(i + 1);
            signPointConfig.setPoint(0);

            for (SignPointConfig sp : signPointConfigListFromDb) {
                if (sp.getTimes().equals(i + 1)) {
                    signPointConfig.setPoint(sp.getPoint());
                    break;
                }
            }

            signPointConfigList.add(signPointConfig);
        }
        model.addAttribute("list", signPointConfigList);
        return "weixin/sign";
    }

    /**
     * 积分换礼
     * 
     * @param model
     * @return
     */
    @RequestMapping("/gift")
    public String gift(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        log.info("pointPoolId = " + pointPoolId);
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        if (wxUserAndMember == null || wxUserAndMember.getPoint() == null) {
            model.addAttribute("point", 0);
        } else {
            model.addAttribute("point", wxUserAndMember.getPoint());
        }

        Long totalCount = giftMapper.countByNameAndStatus(null, Gift.StatusPubnish);
        List<Gift> giftList = null;
        if (totalCount > 0) {
            giftList = giftMapper.selectByNameAndStatus(null, Gift.StatusPubnish, getPageMap(1, defaultPageSize));
        }
        model.addAttribute("giftList", giftList);

        return "weixin/gift";
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
        return "weixin/giftDetail";
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
        if (memberPoint == null) {
            memberPoint = new MemberPoint();
            memberPoint.setPoint(0);
            memberPoint.setMemberId(memberId);
            memberPoint.setPointPoolId(pointPoolId);
            memberPointMapper.insertSelective(memberPoint);
        }
        model.addAttribute("memberPoint", memberPoint);

        Long totalCount = memberPointRecordMapper.countByPoolIdAndMemberId(pointPoolId, memberId);
        List<MemberPointRecord> memberPointRecords = null;
        if (totalCount > 0) {
            memberPointRecords = memberPointRecordMapper.selectByPoolIdAndMemberId(pointPoolId, memberId,
                    getPageMap(1, defaultPageSize));
        }
        model.addAttribute("memberPointRecords", memberPointRecords);

        return "weixin/myPoint";
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
        List<AppointmentAndMember> appointmentList = null;
        if (memberId != null) {
            Long totalCount = appointmentAndMemberMapper.countByMemberId(memberId);
            if (totalCount != null && totalCount > 0) {
                appointmentList = appointmentAndMemberMapper.selectByMemberId(memberId, getPageMap(1, defaultPageSize));
            }
        }
        model.addAttribute("appointmentList", appointmentList);
        return "weixin/myAppointment";
    }

    /**
     * 资讯吧
     * 
     * @param model
     * @return
     */
    @RequestMapping("/news")
    public String news(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long totalCount = messageMapper.countNews();
        List<Message> messageList = null;
        if (totalCount > 0) {
            messageList = messageMapper.selectNews(getPageMap(1, defaultPageSize));
        }
        model.addAttribute("messageList", messageList);
        return "weixin/news";
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
        model.addAttribute("message", message);

        return "weixin/newsDetail";
    }

    /**
     * 我的消息
     * 
     * @param model
     * @param openid
     * @return
     */
    // @RequestMapping("/myEmail")
    // public String myEmail(Model model, HttpServletRequest request, HttpServletResponse response) {
    //
    // return "weixin/myEmail";
    // }

    /**
     * 销售登录
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerLogin")
    public String salerLogin(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        User user = userMapper.selectByOpenid(openid);
        if (user == null) {
            return "weixin/salerLogin";
        } else {
            return "forward:/wx/view/mineHome";
        }
    }

    /**
     * 销售重置密码
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerResetPwd")
    public String salerResetPwd(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "weixin/salerResetPwd";
    }

    /**
     * 抢客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerGrabCustomer")
    public String grabCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {

        String openid = CookieUtil.getOpenid(request);
        User user = userMapper.selectByOpenid(openid);
        model.addAttribute("user", user);

        if (user != null) {
            Map<String, String> params = CookieUtil.getIdentity(request);
            params.put(CookieUtil.USER_ID, user.getId() + "");
            params.put(CookieUtil.USER_NAME, user.getName());
            CookieUtil.setIdentity(request, response, params, 0);
        }

        List<PotentialCustomerAndMember> potentialCustomers = potentialCustomerAndMemberMapper.selectToService();
        if (potentialCustomers.size() > 0) {
            int index = (int) (Math.random() * potentialCustomers.size());
            while (index < 0 || index >= potentialCustomers.size()) {
                index = (int) (Math.random() * potentialCustomers.size());
            }

            model.addAttribute("pCustomer", potentialCustomers.get(index));
        } else {
            model.addAttribute("pCustomer", null);
        }
        model.addAttribute("buytypeStrs", PotentialCustomer.buytypeStrs);
        model.addAttribute("buyforStrs", PotentialCustomer.buyforStrs);
        model.addAttribute("paytypeStrs", PotentialCustomer.paytypeStrs);

        return "weixin/salerGrabCustomer";
    }

    /**
     * 我的客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomer")
    public String salerCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        Long totalCount = potentialCustomerAndMemberMapper.countByUserId(userId);
        List<PotentialCustomerAndMember> potentialCustomers = null;
        if (totalCount > 0) {
            potentialCustomers = potentialCustomerAndMemberMapper
                    .selectByUserId(userId, getPageMap(1, defaultPageSize));
        }
        model.addAttribute("potentialCustomers", potentialCustomers);

        model.addAttribute("buytypeStrs", PotentialCustomer.buytypeStrs);
        model.addAttribute("buyforStrs", PotentialCustomer.buyforStrs);
        model.addAttribute("paytypeStrs", PotentialCustomer.paytypeStrs);
        return "weixin/salerCustomer";
    }

    /**
     * 客户详情
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomerDetail")
    public String salerCustomerDetail(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        User user = userMapper.selectByOpenid(openid);

        if (user != null) {
            Map<String, String> params = CookieUtil.getIdentity(request);
            params.put(CookieUtil.USER_ID, user.getId() + "");
            params.put(CookieUtil.USER_NAME, user.getName());
            CookieUtil.setIdentity(request, response, params, 0);
        }

        PotentialCustomerAndMember potentialCustomer = potentialCustomerAndMemberMapper.selectById(id);
        if (potentialCustomer.getServiceBy() == null || potentialCustomer.getServiceBy() <= 0) {

            potentialCustomer.setName(StringUtils.getSecurityName(potentialCustomer.getName()));
            potentialCustomer.setMobile(StringUtils.getSecurityMobile(potentialCustomer.getMobile()));
        }
        model.addAttribute("pCustomer", potentialCustomer);
        model.addAttribute("buytypeStrs", PotentialCustomer.buytypeStrs);
        model.addAttribute("buyforStrs", PotentialCustomer.buyforStrs);
        model.addAttribute("paytypeStrs", PotentialCustomer.paytypeStrs);
        return "weixin/salerGrabCustomer";
    }

    /**
     * 车管家首页
     * 
     * @param model
     * @return
     */
    @RequestMapping("/managerHome")
    public String carHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, 0L);
        model.addAttribute("wxuser", wxUserAndMember);
        List<Message> messageList = messageMapper.selectManager();
        model.addAttribute("activity", messageList);
        return "weixin/managerHome";
    }

    /**
     * 道路救援
     * 
     * @param model
     * @return
     */
    @RequestMapping("/roadRescue")
    public String roadRescue(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "weixin/roadRescue";
    }

    /**
     * 预约维修
     * 
     * @param model
     * @return
     */
    @RequestMapping("/appointWeixiu")
    public String appointmentWeixiu(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId, MemberCar.StatusYes);

        model.addAttribute("wxuser", member);
        model.addAttribute("cars", memberCars);
        return "weixin/appointWeixiu";
    }

    @RequestMapping("/appointBaoyang")
    public String appointmentBaoyang(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId, MemberCar.StatusYes);

        model.addAttribute("wxuser", member);
        model.addAttribute("cars", memberCars);
        return "weixin/appointBaoyang";
    }

    @RequestMapping("/appointKanche")
    public String appointmentKanche(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        model.addAttribute("wxuser", member);
        return "weixin/appointKanche";
    }

    @RequestMapping("/appointNianshen")
    public String appointmentNianshen(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId, MemberCar.StatusYes);

        model.addAttribute("wxuser", member);
        model.addAttribute("cars", memberCars);
        return "weixin/appointNianshen";
    }

    @RequestMapping("/appointQita")
    public String appointmentQita(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId, MemberCar.StatusYes);

        model.addAttribute("wxuser", member);
        model.addAttribute("cars", memberCars);
        return "weixin/appointQita";
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

        model.addAttribute("buytypeStrs", PotentialCustomer.buytypeStrs);
        model.addAttribute("buyforStrs", PotentialCustomer.buyforStrs);
        model.addAttribute("paytypeStrs", PotentialCustomer.paytypeStrs);
        return "weixin/buycarHome";
    }

    /**
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/buycarAdd")
    public String buycarAdd(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        Member member = memberMapper.selectByPrimaryKey(memberId);
        model.addAttribute("wxuser", member);

        return "weixin/buycarAdd";
    }

    /**
     * 展厅首页
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/hallHome")
    public String hallHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long totalCount = carHallMapper.countByCondition(null, null, CarHall.StatusActive);
        List<CarHall> carHallList = null;
        if (totalCount > 0) {
            carHallList = carHallMapper.selectByCondition(null, null, CarHall.StatusActive,
                    getPageMap(1, defaultPageSize));
        }
        model.addAttribute("hallList", carHallList);
        return "weixin/hallHome";
    }

    /**
     * 展厅详情
     * 
     * @param model
     * @param hallId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/hallDetail")
    public String hallDetail(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {

        CarHall carHall = carHallMapper.selectByPrimaryKey(id);
        model.addAttribute("hall", carHall);

        List<CarHallPic> carHallPics = carHallPicMapper.selectByHallId(id);
        model.addAttribute("carHallPics", carHallPics);

        List<CarHallModel> carHallModels = carHallModelMapper.selectByHallId(id);
        model.addAttribute("carHallModels", carHallModels);

        return "weixin/hallDetail";
    }

    /**
     * 车型参数
     * 
     * @param model
     * @param carModelId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/carParam")
    public String carParam(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        CarModel carModel = carModelMapper.selectByPrimaryKey(id);
        model.addAttribute("carModel", carModel);
        model.addAttribute("carParams", JSONArray.parse(carModel.getParams()));
        return "weixin/carParam";
    }

    @RequestMapping("/carModel")
    public String carModel(Model model, Long id, HttpServletRequest request, HttpServletResponse response) {
        CarModel carModel = carModelMapper.selectByPrimaryKey(id);
        model.addAttribute("carModel", carModel);
        return "weixin/carParam";
    }
}