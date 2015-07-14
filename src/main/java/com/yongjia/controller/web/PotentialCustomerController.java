package com.yongjia.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.BaseController;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.PotentialCustomerMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.PotentialCustomer;
import com.yongjia.model.PotentialCustomerAndMember;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/customer")
public class PotentialCustomerController extends BaseController {

    private static Logger log = Logger.getLogger(PotentialCustomerController.class);

    @Autowired
    private WxUserMapper wxuserMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;
    
    @Autowired
    private PotentialCustomerMapper pCustomermMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, String phone, HttpServletRequest request, HttpServletResponse response) {

//        List<PotentialCustomer> customerList = pCustomermMapper.selectByNameAndPhone(name, phone);
        List<PotentialCustomerAndMember> customerList = new ArrayList<PotentialCustomerAndMember>();
        PotentialCustomerAndMember pcam = new PotentialCustomerAndMember();
        pcam.setBuyBudget("");
        pcam.setBuyDate(System.currentTimeMillis());
        pcam.setBuyFor("");
        pcam.setBuyTime(System.currentTimeMillis());
        pcam.setCarColor("白色");
        pcam.setCarModel("奥迪A6");
        pcam.setCarType("奥迪");
        pcam.setHeadImgurl("");
        pcam.setId(1L);
        pcam.setMemberId(2L);
        pcam.setMobile("18511896713");
        pcam.setPayType(PotentialCustomer.PayTypeCash);
        pcam.setServiceBy(0L);
        pcam.setServiceByName("");
        pcam.setSex(WxUser.SexMale);
        customerList.add(pcam);
        
        pcam = new PotentialCustomerAndMember();
        pcam.setBuyBudget("100");
        pcam.setBuyDate(System.currentTimeMillis());
        pcam.setBuyFor("结婚");
        pcam.setBuyTime(System.currentTimeMillis());
        pcam.setCarColor("白色");
        pcam.setCarModel("奥迪A6");
        pcam.setCarType("奥迪");
        pcam.setHeadImgurl("");
        pcam.setId(2L);
        pcam.setMemberId(2L);
        pcam.setMobile("18511896713");
        pcam.setPayType(PotentialCustomer.PayTypeCash);
        pcam.setServiceBy(4L);
        pcam.setServiceByName("王佳亮");
        pcam.setSex(WxUser.SexMale);
        customerList.add(pcam);
        
        return ToJsonUtil.toListMap(200, "success", customerList);
    }

}