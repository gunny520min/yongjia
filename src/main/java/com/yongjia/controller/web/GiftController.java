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
public class GiftController extends BaseController {

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
    public Map list(HttpServletRequest request, HttpServletResponse response) {

//        List<Gift> giftList = giftMapper.selectAll();
        List<Gift> giftList = new ArrayList<Gift>();
        Gift gift = new Gift();
        gift.setContent("");
        gift.setId(1L);
        gift.setName("礼品1");
        gift.setPic("");
        gift.setPoint(2000);
        gift.setPrividerName("aaa");
        gift.setStatus(Gift.StatusPubnish);
        giftList.add(gift);
        
        gift = new Gift();
        gift.setContent("");
        gift.setId(2L);
        gift.setName("礼品2");
        gift.setPic("");
        gift.setPoint(2000);
        gift.setPrividerName("aaaaa");
        gift.setStatus(Gift.StatusStop);
        giftList.add(gift);
        
        return ToJsonUtil.toListMap(200, "success", giftList);
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
        
        if (giftMapper.insert(gift)>0) {
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
        
        if (giftMapper.updateByPrimaryKey(gift)>0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/toggle")
    @ResponseBody
    public Map toggle(int giftId, HttpServletRequest request, HttpServletResponse response) {
        
        Gift gift = giftMapper.selectByPrimaryKey(giftId);
        if (gift.getStatus().equals(Gift.StatusPubnish)) {
            gift.setStatus(Gift.StatusStop);
        } else {
            gift.setStatus(Gift.StatusPubnish);
        }
        Long userId = CookieUtil.getUserID(request);
        gift.setUpdateAt(System.currentTimeMillis());
        gift.setUpdateBy(userId);
        
        if (giftMapper.updateByPrimaryKey(gift)>0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(int giftId, HttpServletRequest request, HttpServletResponse response) {
        
        if (giftMapper.deleteByPrimaryKey(giftId)>0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }
    
    

}