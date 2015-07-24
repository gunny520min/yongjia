package com.yongjia.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.BaseController;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.WxUserAndMemberMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/wxuser")
public class WxUserController extends BaseController {

    private static Logger log = Logger.getLogger(WxUserController.class);

    @Autowired
    private WxUserAndMemberMapper wxUserAndMemberMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, String mobile, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {
        Long totalCount = wxUserAndMemberMapper.countByNameAndPhone(name, mobile);
        List<WxUserAndMember> wxuserAndMemberList = null;
        if (totalCount > 0) {
            wxuserAndMemberList = wxUserAndMemberMapper.selectByNameAndPhone(name, mobile, getPageMap(pageNo, pageSize));
            log.info("wxuserAndMemberList is :"+JSONArray.fromObject(wxuserAndMemberList).toString());
        }
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                wxuserAndMemberList);
    }

    @RequestMapping("/getMemberCar")
    @ResponseBody
    public Map getMemberCar(Long memberId, HttpServletRequest request, HttpServletResponse response) {
        if (memberId == null) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        } else {
            List<MemberCar> memberCars = memberCarMapper.selectByMemberId(memberId);
            return ToJsonUtil.toListMap(200, "success", memberCars);
        }
    }

    /**
     * TODO
     * 
     * @param memberCarId
     * @param memberId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/passMemberCar")
    @ResponseBody
    @Transactional
    public Map passMemberCar(Long memberCarId, HttpServletRequest request, HttpServletResponse response) {

        MemberCar memberCar = memberCarMapper.selectByPrimaryKey(memberCarId);
        memberCar.setStatus(MemberCar.StatusYes);

        if (memberCarMapper.updateByPrimaryKeySelective(memberCar) > 0) {

            Member member = memberMapper.selectByPrimaryKey(memberCar.getMemberId());
            member.setStatus(Member.StatusCarOwner);
            member.setValiFlag(Member.NoVali);
            if (memberMapper.updateByPrimaryKeySelective(member) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/nopassMemberCar")
    @ResponseBody
    @Transactional
    public Map nopassMemberCar(Long memberCarId, HttpServletRequest request, HttpServletResponse response) {

        MemberCar memberCar = memberCarMapper.selectByPrimaryKey(memberCarId);
        memberCar.setStatus(MemberCar.StatusNo);
        if (memberCarMapper.updateByPrimaryKeySelective(memberCar) > 0) {
            Member member = memberMapper.selectByPrimaryKey(memberCar.getMemberId());
            member.setValiFlag(Member.NoVali);
            if (memberMapper.updateByPrimaryKeySelective(member) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}