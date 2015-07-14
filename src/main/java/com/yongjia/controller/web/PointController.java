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
public class PointController extends BaseController {

    private static Logger log = Logger.getLogger(PointController.class);

    @Autowired
    private MemberPointRecordMapper memberPointRecordMapper;
    @Autowired
    private SignPointConfigMapper signPointConfigMapper;

    @RequestMapping("/listExchange")
    @ResponseBody
    public Map listExchange(Long pointPoolId, HttpServletRequest request, HttpServletResponse response) {

//        List<MemberPointRecord> memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
//                MemberPointRecord.TypeUse);
        List<MemberPointRecord> memberPointRecordList = new ArrayList<MemberPointRecord>();
        MemberPointRecord memberPointRecord = new MemberPointRecord();
        memberPointRecord.setAction("兑换礼品");
        memberPointRecord.setId(1L);
        memberPointRecord.setMemberId(2L);
        memberPointRecord.setPointPoolId(1L);
        memberPointRecord.setPoint(100);
        memberPointRecord.setType(MemberPointRecord.TypeUse);
        memberPointRecord.setRemark("");
        memberPointRecordList.add(memberPointRecord);
        
        memberPointRecord = new MemberPointRecord();
        memberPointRecord.setAction("兑换服务");
        memberPointRecord.setId(1L);
        memberPointRecord.setMemberId(2L);
        memberPointRecord.setPointPoolId(1L);
        memberPointRecord.setPoint(100);
        memberPointRecord.setType(MemberPointRecord.TypeUse);
        memberPointRecord.setRemark("");
        memberPointRecordList.add(memberPointRecord);
        
        return ToJsonUtil.toListMap(200, "success", memberPointRecordList);
    }

    @RequestMapping("/listPoint")
    @ResponseBody
    public Map listPoint(int pointPoolId, HttpServletRequest request, HttpServletResponse response) {

//        List<MemberPointRecord> memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
//                MemberPointRecord.TypeGet);
        List<MemberPointRecord> memberPointRecordList = new ArrayList<MemberPointRecord>();
        MemberPointRecord memberPointRecord = new MemberPointRecord();
        memberPointRecord.setAction("签到");
        memberPointRecord.setId(1L);
        memberPointRecord.setMemberId(2L);
        memberPointRecord.setPointPoolId(1L);
        memberPointRecord.setPoint(100);
        memberPointRecord.setType(MemberPointRecord.TypeGet);
        memberPointRecord.setRemark("");
        memberPointRecordList.add(memberPointRecord);
        
        memberPointRecord = new MemberPointRecord();
        memberPointRecord.setAction("注册");
        memberPointRecord.setId(1L);
        memberPointRecord.setMemberId(2L);
        memberPointRecord.setPointPoolId(1L);
        memberPointRecord.setPoint(100);
        memberPointRecord.setType(MemberPointRecord.TypeGet);
        memberPointRecord.setRemark("");
        memberPointRecordList.add(memberPointRecord);
        return ToJsonUtil.toListMap(200, "success", memberPointRecordList);
    }

    @RequestMapping("/addExchange")
    @ResponseBody
    public Map addExchange(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeUse);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());
        if (memberPointRecordMapper.insert(memberPointRecord) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }

    }

    @RequestMapping("/addPoint")
    @ResponseBody
    public Map addPoint(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeGet);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());
        if (memberPointRecordMapper.insert(memberPointRecord) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}