package com.yongjia.controller.wx;

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
import com.yongjia.dao.AppointmentAndMemberMapper;
import com.yongjia.dao.AppointmentMapper;
import com.yongjia.dao.CarHallMapper;
import com.yongjia.model.Appointment;
import com.yongjia.model.AppointmentAndMember;
import com.yongjia.model.CarHall;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/appointment")
public class WxAppointmentController extends BaseController {

    private static Logger log = Logger.getLogger(WxAppointmentController.class);

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentAndMemberMapper appointmentAndMemberMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        Long memberId = CookieUtil.getMemberId(request);
        List<AppointmentAndMember> appointmentList = null;
        if (memberId != null) {
            Long totalCount = appointmentAndMemberMapper.countByMemberId(memberId);
            if (totalCount > 0) {
                appointmentList = appointmentAndMemberMapper.selectByMemberId(memberId, getPageMap(pageNo, pageSize));
            }
            return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, appointmentList);
        }

        return ToJsonUtil.toEntityMap(400, "非会员", null);
    }
    
    @RequestMapping("/add")
    @ResponseBody
    public Map add(Appointment appointment, HttpServletRequest request, HttpServletResponse response) {

        Long memberId = CookieUtil.getMemberId(request);
        appointment.setMemberId(memberId);
        
        appointment.setStatus(Appointment.StatusToConfirm);
        appointment.setCreateAt(new Date().getTime());
        appointment.setUpdateAt(new Date().getTime());

        if (appointmentMapper.insertSelective(appointment) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }
}