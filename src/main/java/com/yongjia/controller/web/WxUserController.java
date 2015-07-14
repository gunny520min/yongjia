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
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/wxuser")
public class WxUserController extends BaseController {

    private static Logger log = Logger.getLogger(WxUserController.class);

    @Autowired
    private WxUserMapper wxuserMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, String phone, HttpServletRequest request, HttpServletResponse response) {

//        List<WxUserAndMember> wxuserList = wxuserMapper.selectByNameAndPhone(name, phone);
        List<WxUserAndMember> wxuserList = new ArrayList<WxUserAndMember>();
        WxUserAndMember wxuser = new WxUserAndMember();
        wxuser.setHeadimgurl("http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0");
        wxuser.setNickname("微信小王子");
        wxuser.setOpenid("111111");
        wxuser.setSex(WxUser.SexMale);
        wxuser.setId(null);
        wxuser.setMobile(null);
        wxuser.setName(null);
        wxuser.setStatus(Member.StatusMember);
        wxuser.setValiFlag(Member.NoVali);
        wxuserList.add(wxuser);
        
        wxuser = new WxUserAndMember();
        wxuser.setHeadimgurl("http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0");
        wxuser.setNickname("band");
        wxuser.setOpenid("111111");
        wxuser.setSex(WxUser.SexUnknown);
        wxuser.setId(2L);
        wxuser.setMobile("18511896713");
        wxuser.setName("郭靖");
        wxuser.setStatus(Member.StatusCarOwner);
        wxuser.setValiFlag(Member.ToVali);
        wxuserList.add(wxuser);
        return ToJsonUtil.toListMap(200, "success", wxuserList);
    }

    @RequestMapping("/getMemberCar")
    @ResponseBody
    public Map getMemberCar(Long memberId, HttpServletRequest request, HttpServletResponse response) {

//        List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId);
        List<MemberCar> memberCars = new ArrayList<MemberCar>();
        MemberCar memberCar = new MemberCar();
        memberCar.setId(1L);
        memberCar.setMemberId(2L);
        memberCar.setCarNumber("京A123456");
        memberCar.setModelName("奥迪2015");
        memberCar.setTypeName("奥迪");
        memberCar.setVinNo("");
        memberCar.setStatus(MemberCar.StatusToVali);
        memberCars.add(memberCar);
        
        memberCar = new MemberCar();
        memberCar.setId(2L);
        memberCar.setMemberId(2L);
        memberCar.setCarNumber("京A123452");
        memberCar.setModelName("奥迪2015");
        memberCar.setTypeName("奥迪2");
        memberCar.setVinNo("");
        memberCar.setStatus(MemberCar.StatusYes);
        memberCars.add(memberCar);
        
        memberCar = new MemberCar();
        memberCar.setId(2L);
        memberCar.setMemberId(2L);
        memberCar.setCarNumber("京A123453");
        memberCar.setModelName("奥迪2015");
        memberCar.setTypeName("奥迪3");
        memberCar.setVinNo("");
        memberCar.setStatus(MemberCar.StatusNo);
        memberCars.add(memberCar);

        return ToJsonUtil.toEntityMap(200, "success", memberCars);
    }

    @RequestMapping("/passMemberCar")
    @ResponseBody
    public Map passMemberCar(int memberCarId, HttpServletRequest request, HttpServletResponse response) {

        MemberCar memberCar = memberCarMapper.selectByPrimaryKey(memberCarId);
        memberCar.setStatus(MemberCar.StatusYes);
        if (memberCarMapper.updateByPrimaryKeySelective(memberCar) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/nopassMemberCar")
    @ResponseBody
    public Map nopassMemberCar(int memberCarId, HttpServletRequest request, HttpServletResponse response) {

        MemberCar memberCar = memberCarMapper.selectByPrimaryKey(memberCarId);
        memberCar.setStatus(MemberCar.StatusNo);
        if (memberCarMapper.updateByPrimaryKeySelective(memberCar) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    

}