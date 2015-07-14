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
import com.yongjia.dao.AppointmentMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.Appointment;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/appointment")
public class AppointmentController extends BaseController {

    private static Logger log = Logger.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentMapper appointmentMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(HttpServletRequest request, HttpServletResponse response) {

//        List<Appointment> appointmentList = appointmentMapper.selectAll();
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        Appointment appointment = new Appointment();
        appointment.setAppointContent("");
        appointment.setAppointTime(System.currentTimeMillis());
        appointment.setArriveTime(null);
        appointment.setCarType("奥迪");
        appointment.setConnectPhone("18511896713");
        appointment.setId(1L);
        appointment.setIsTestDrive(Appointment.NotTestCar);
        appointment.setKilo("1000");
        appointment.setProblemDesc("");
        appointment.setStatus(Appointment.StatusToConfirm);
        appointment.setType(Appointment.TypeBaoyang);
        appointmentList.add(appointment);
        return ToJsonUtil.toListMap(200, "success", appointmentList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(Appointment appointment, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        appointment.setStatus(Appointment.StatusToDo);
        appointment.setCreateAt(new Date().getTime());
        appointment.setCreateBy(userId);
        appointment.setUpdateAt(new Date().getTime());
        appointment.setUpdateBy(userId);

        if (appointmentMapper.insert(appointment) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/setStatus")
    @ResponseBody
    public Map update(int appointmentId, int status, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);
        appointment.setStatus(status);
        appointment.setUpdateAt(new Date().getTime());
        appointment.setUpdateBy(userId);

        if (appointmentMapper.updateByPrimaryKeySelective(appointment) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}