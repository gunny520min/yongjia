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

import com.yongjia.dao.GiftMapper;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.PotentialCustomerMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Gift;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/gift")
public class GiftController extends WebBaseController {

    private static Logger log = Logger.getLogger(GiftController.class);

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @Autowired
    private PotentialCustomerMapper pCustomermMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, Integer status, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {
        if (name != null && name.length() == 0) {
            name = null;
        }
        Long totalCount = giftMapper.countByNameAndStatus(name, status);
        List<Gift> giftList = null;
        if (totalCount > 0) {
            giftList = giftMapper.selectByNameAndStatus(name, status, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, giftList);
    }

    @RequestMapping("/get")
    @ResponseBody
    public Map get(Long id, HttpServletRequest request, HttpServletResponse response) {

        try {
            Gift gift = giftMapper.selectByPrimaryKey(id);
            if (gift != null) {
                return ToJsonUtil.toEntityMap(200, "success", gift);
            } else {
                return ToJsonUtil.toEntityMap(400, "无效id", null);
            }
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(Gift gift, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        gift.setStatus(Gift.StatusStop);
        gift.setCreateAt(System.currentTimeMillis());
        gift.setCreateBy(userId);
        gift.setUpdateAt(System.currentTimeMillis());
        gift.setUpdateBy(userId);

        if (giftMapper.insertSelective(gift) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(Gift gift, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        gift.setUpdateAt(System.currentTimeMillis());
        gift.setUpdateBy(userId);

        if (giftMapper.updateByPrimaryKeySelective(gift) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/toggle")
    @ResponseBody
    public Map toggle(Long id, HttpServletRequest request, HttpServletResponse response) {

        Gift gift = giftMapper.selectByPrimaryKey(id);
        if (gift.getStatus().equals(Gift.StatusPubnish)) {
            gift.setStatus(Gift.StatusStop);
        } else {
            gift.setStatus(Gift.StatusPubnish);
        }
        Long userId = CookieUtil.getUserID(request);
        gift.setUpdateAt(System.currentTimeMillis());
        gift.setUpdateBy(userId);

        if (giftMapper.updateByPrimaryKeySelective(gift) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(Long id, HttpServletRequest request, HttpServletResponse response) {

        if (giftMapper.deleteByPrimaryKey(id) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}