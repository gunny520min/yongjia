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
import com.yongjia.dao.PointPoolConfigMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.SignPointConfigMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.PointPool;
import com.yongjia.model.PointPoolConfig;
import com.yongjia.model.SignPointConfig;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/pool")
public class PointPoolController extends BaseController {

    private static Logger log = Logger.getLogger(PointPoolController.class);

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @Autowired
    private PointPoolConfigMapper pointPoolConfigMapper;
    @Autowired
    private SignPointConfigMapper signPointConfigMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(HttpServletRequest request, HttpServletResponse response) {

        // List<PointPool> pointPoolList = pointPoolMapper.selectAll();
        List<PointPool> pointPoolList = new ArrayList<PointPool>();
        PointPool pointPool = new PointPool();
        pointPool.setId(1L);
        pointPool.setName("2015积分池");
        pointPool.setTotalPoint(0);
        pointPool.setStartAt(System.currentTimeMillis());
        pointPool.setEndAt(System.currentTimeMillis());
        pointPoolList.add(pointPool);

        pointPool = new PointPool();
        pointPool.setId(2L);
        pointPool.setName("2014积分池");
        pointPool.setTotalPoint(2000);
        pointPool.setStartAt(System.currentTimeMillis());
        pointPool.setEndAt(System.currentTimeMillis());
        pointPoolList.add(pointPool);
        return ToJsonUtil.toListMap(200, "success", pointPoolList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(PointPool pointPool, HttpServletRequest request, HttpServletResponse response) {

        if (pointPoolMapper.insert(pointPool) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }

    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(PointPool pointPool, HttpServletRequest request, HttpServletResponse response) {

        if (pointPoolMapper.updateByPrimaryKeySelective(pointPool) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/charge")
    @ResponseBody
    public Map charge(int pointPoolId, int point, HttpServletRequest request, HttpServletResponse response) {

        PointPool pointPool = pointPoolMapper.selectByPrimaryKey(pointPoolId);
        pointPool.setTotalPoint(pointPool.getTotalPoint() + point);
        if (pointPoolMapper.updateByPrimaryKeySelective(pointPool) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", pointPool);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(int pointPoolId, HttpServletRequest request, HttpServletResponse response) {

        if (pointPoolMapper.deleteByPrimaryKey(pointPoolId) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/config")
    @ResponseBody
    public Map config(int pointPoolId, int registerPoint, int carOwnerPoint, int toBuyCarPoint,
            HttpServletRequest request, HttpServletResponse response) {

        PointPool pointPool = pointPoolMapper.selectByPrimaryKey(pointPoolId);
        if (registerPoint > 0) {
            pointPool.setRegisterPoint(registerPoint);
        }
        if (carOwnerPoint > 0) {
            pointPool.setCarOwnerPoint(carOwnerPoint);
        }
        if (toBuyCarPoint > 0) {
            pointPool.setToBuyCarPoint(toBuyCarPoint);
        }
        pointPoolMapper.updateByPrimaryKey(pointPool);

        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/getSignConfig")
    @ResponseBody
    public Map getSignConfig(String month, HttpServletRequest request, HttpServletResponse response) {

        // List<SignPointConfig> signPointConfigList = signPointConfigMapper.selectByMonth(month);
        List<SignPointConfig> signPointConfigList = new ArrayList<SignPointConfig>();
        for (int i = 0; i < 31; i++) {
            SignPointConfig signPointConfig = new SignPointConfig();
            signPointConfig.setId(1L);
            signPointConfig.setMonth("2015-07");
            signPointConfig.setTimes(i);
            signPointConfig.setPoint(10);
            signPointConfigList.add(signPointConfig);
        }

        return ToJsonUtil.toListMap(200, "success", signPointConfigList);
    }

    @RequestMapping("/setSignConfig")
    @ResponseBody
    public Map getSignConfig(String month, Integer times, Integer point, HttpServletRequest request,
            HttpServletResponse response) {

        // SignPointConfig signPointConfig = signPointConfigMapper.selectByMonthAndTimes(month, times);
        // if (signPointConfig==null) {
        // signPointConfig = new SignPointConfig();
        // signPointConfig.setMonth(month);
        // signPointConfig.setTimes(times);
        // signPointConfig.setPoint(point);
        // signPointConfigMapper.insert(signPointConfig);
        // } else {
        // signPointConfig.setPoint(point);
        // signPointConfigMapper.updateByPrimaryKey(signPointConfig);
        // }
        return ToJsonUtil.toEntityMap(200, "success", null);
    }
    
    // TODO 添加积分池记录

}