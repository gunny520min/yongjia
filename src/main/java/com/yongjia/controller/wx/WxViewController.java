package com.yongjia.controller.wx;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yongjia.controller.BaseController;
import com.yongjia.model.Appointment;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarHallPic;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.Gift;
import com.yongjia.model.Member;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.PotentialCustomer;
import com.yongjia.model.User;
import com.yongjia.model.WxMsgItem;
import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserEmail;

@Controller
@RequestMapping("/wx/view")
public class WxViewController extends BaseController {

    private static Logger log = Logger.getLogger(WxViewController.class);

    /**
     * 我的首页
     * 
     * @param model
     * @param openid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/mine")
    public String mine(Model model, String openid) {
        // TODO
        model.addAttribute("wxUser", new WxUser());
        model.addAttribute("saler", new User());
        return "";
    }

    /**
     * 用户注册
     * 
     * @param model
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model) {
        return "";
    }

    /**
     * 查看用户信息
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/userInfo")
    public String userInfo(Model model, String openid) {
        model.addAttribute("member", new Member());
        return "";
    }

    /**
     * 验证为车主
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/verifyOwner")
    public String verifyOwner(Model model, String openid) {
        model.addAttribute("member", new Member());
        return "";
    }

    /**
     * 添加车
     * 
     * @param model
     * @return
     */
    @RequestMapping("/userAddCar")
    public String verifyOwner(Model model) {

        return "";
    }

    /**
     * 签到页面
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/sign")
    public String sign(Model model, String openid) {
        model.addAttribute("memberPointRecord", new MemberPointRecord());
        return "";
    }

    /**
     * 积分换礼
     * 
     * @param model
     * @return
     */
    @RequestMapping("/gift")
    public String sign(Model model) {
        return "";
    }

    /**
     * 礼物详情
     * 
     * @param model
     * @param giftId
     * @return
     */
    @RequestMapping("/giftDetail")
    public String sign(Model model, int giftId) {
        model.addAttribute("gift", new Gift());
        return "";
    }

    /**
     * 如何赚取积分界面
     * 
     * @param model
     * @return
     */
    @RequestMapping("/pointNotice")
    public String pointNotice(Model model) {
        return "";
    }

    /**
     * 我的积分
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myPoint")
    public String myPoint(Model model, String openid) {

        model.addAttribute("memberPoint", new MemberPoint());
        model.addAttribute("memberPointRecord", new MemberPointRecord());
        return "";
    }

    /**
     * 我的预约
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myAppointment")
    public String myAppointment(Model model, String openid) {

        model.addAttribute("memberAppointment", new Appointment());
        return "";
    }

    /**
     * 资讯吧
     * 
     * @param model
     * @return
     */
    @RequestMapping("/news")
    public String news(Model model) {

        model.addAttribute("news", new WxMsgItem());
        return "";
    }

    /**
     * 资讯详情
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/newsDetail")
    public String newsDetail(Model model, int id) {

        model.addAttribute("newsDetail", new WxMsgItem());
        return "";
    }

    /**
     * 我的消息
     * 
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/myEmail")
    public String myEmail(Model model, String openid) {

        model.addAttribute("wxUserEmail", new WxUserEmail());
        return "";
    }

    /**
     * 销售登录
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerLogin")
    public String salerLogin(Model model) {

        return "";
    }

    /**
     * 销售重置密码
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerResetPwd")
    public String salerResetPwd(Model model) {

        return "";
    }

    /**
     * 抢客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/grabCustomer")
    public String grabCustomer(Model model) {

        return "";
    }

    /**
     * 我的客户
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomer")
    public String salerCustomer(Model model) {

        return "";
    }

    /**
     * 客户详情
     * 
     * @param model
     * @return
     */
    @RequestMapping("/salerCustomerDetail")
    public String salerCustomerDetail(Model model) {

        return "";
    }

    /**
     * 车管家首页
     * @param model
     * @return
     */
    @RequestMapping("/carHome")
    public String carHome(Model model) {
        model.addAttribute("activity", new WxMsgItem());
        return "";
    }

    /**
     * 道路救援
     * @param model
     * @return
     */
    @RequestMapping("/roadRescue")
    public String roadRescue(Model model) {
        return "";
    }
    
    /**
     * 预约首页
     * @param model
     * @return
     */
    @RequestMapping("/appointmentHome")
    public String appointmentHome(Model model) {
        return "";
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping("/appointmentDetail")
    public String appointmentDetail(Model model) {
        return "";
    }
    
    /**
     * 购车计划首页
     * @param model
     * @param openid
     * @return
     */
    @RequestMapping("/buycarHome")
    public String buycarHome(Model model, String openid) {
        model.addAttribute("plan", new PotentialCustomer());
        
        return "";
    }
    
    /**
     * 新增购车计划
     * @param model
     * @return
     */
    @RequestMapping("/buycarAdd")
    public String buycarAdd(Model model) {
        return "";
    }
    

    @RequestMapping("/hallHome")
    public String hallHome(Model model) {
        model.addAttribute("hall", new CarHall());
        return "";
    }


    @RequestMapping("/hallDetail")
    public String hallDetail(Model model, int hallId) {
        model.addAttribute("hall", new CarHall());
        model.addAttribute("hallPic", new CarHallPic());
        model.addAttribute("hallCarModel", new CarModel());
        return "";
    }

    @RequestMapping("/carParam")
    public String carParam(Model model, int carModelId) {
        model.addAttribute("hallCarModel", new CarModelParam());
        return "";
    }
}