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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.MemberPointMapper;
import com.yongjia.dao.MemberPointRecordMapper;
import com.yongjia.dao.PointPoolConfigMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.SignPointConfigMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.PointPool;
import com.yongjia.model.PointPoolConfig;
import com.yongjia.model.SignPointConfig;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/point")
public class PointController extends WebBaseController {

    private static Logger log = Logger.getLogger(PointController.class);

    @Autowired
    private MemberPointRecordMapper memberPointRecordMapper;

    @Autowired
    private MemberPointMapper memberPointMapper;

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @RequestMapping("/listExchange")
    @ResponseBody
    public Map listExchange(Long pointPoolId, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = memberPointRecordMapper.countByPoolIdAndType(pointPoolId, MemberPointRecord.TypeUse);
        List<MemberPointRecord> memberPointRecordList = null;
        if (totalCount > 0) {
            memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
                    MemberPointRecord.TypeUse, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                memberPointRecordList);
    }

    @RequestMapping("/listPoint")
    @ResponseBody
    public Map listPoint(Long pointPoolId, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = memberPointRecordMapper.countByPoolIdAndType(pointPoolId, MemberPointRecord.TypeGet);
        List<MemberPointRecord> memberPointRecordList = null;
        if (totalCount > 0) {
            memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
                    MemberPointRecord.TypeGet, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                memberPointRecordList);
    }

    @RequestMapping("/addExchange")
    @ResponseBody
    public Map addExchange(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {
        if (memberPointRecord.getMemberId() == null) {
            return ToJsonUtil.toEntityMap(400, "memberId 不能为空", null);
        }

        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        if (pointPool == null) {
            return ToJsonUtil.toEntityMap(400, "没有激活的积分池", null);
        }
        memberPointRecord.setPointPoolId(pointPool.getId());
        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeUse);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());

        MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberPointRecord.getMemberId(),
                pointPool.getId());
        if (memberPoint == null || memberPoint.getPoint() < memberPointRecord.getPoint()) {
            return ToJsonUtil.toEntityMap(400, "积分不足", null);
        } else {
            memberPoint.setPoint(memberPoint.getPoint() - memberPointRecord.getPoint());
            if (memberPointMapper.updateByPrimaryKeySelective(memberPoint) > 0) {
                if (memberPointRecordMapper.insertSelective(memberPointRecord) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        }

    }

    @RequestMapping("/addPoint")
    @ResponseBody
    @Transactional
    public Map addPoint(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {
        if (memberPointRecord.getMemberId() == null) {
            return ToJsonUtil.toEntityMap(400, "memberId 不能为空", null);
        }

        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        if (pointPool == null) {
            return ToJsonUtil.toEntityMap(400, "没有激活的积分池", null);
        }
        memberPointRecord.setPointPoolId(pointPool.getId());
        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeGet);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());
        if (memberPointRecordMapper.insertSelective(memberPointRecord) > 0) {
            MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberPointRecord.getMemberId(),
                    pointPool.getId());
            if (memberPoint == null) {
                memberPoint = new MemberPoint();
                memberPoint.setMemberId(memberPointRecord.getMemberId());
                memberPoint.setPointPoolId(pointPool.getId());
                memberPoint.setPoint(memberPointRecord.getPoint());
                if (memberPointMapper.insertSelective(memberPoint) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                memberPoint.setPoint(memberPoint.getPoint() + memberPointRecord.getPoint());
                if (memberPointMapper.updateByPrimaryKeySelective(memberPoint) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            }

        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}