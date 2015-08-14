package com.yongjia.controller.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.AppointmentAndMemberMapper;
import com.yongjia.dao.AppointmentMapper;
import com.yongjia.dao.WxUser2memberMapper;
import com.yongjia.model.Appointment;
import com.yongjia.model.AppointmentAndMember;
import com.yongjia.model.WxUser2member;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;
import com.yongjia.wxkit.template.uitls.TplMessageUtil;

@Controller
@RequestMapping("/web/appointment")
public class AppointmentController extends WebBaseController {

    private static Logger log = Logger.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentAndMemberMapper appointmentAndMemberMapper;
    @Autowired
    private WxUser2memberMapper wxUser2memberMapper;

    /**
     * 注入线程池
     */
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer status, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = appointmentAndMemberMapper.countByStatus(status);
        List<AppointmentAndMember> appointmentList = null;
        if (totalCount > 0) {
            appointmentList = appointmentAndMemberMapper.selectByStatus(status, getPageMap(pageNo, pageSize));
        }

        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                appointmentList);
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

        if (appointmentMapper.insertSelective(appointment) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/setStatus")
    @ResponseBody
    public Map update(Long id, int status, Long arriveTime, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        Appointment appointment = appointmentMapper.selectByPrimaryKey(id);
        appointment.setStatus(status);
        if (arriveTime != null && arriveTime > 0) {
            appointment.setArriveTime(arriveTime);
            if (status == Appointment.StatusToDo) {
                if (appointment.getType() > 0 && appointment.getType() < Appointment.TypeStr.length) {
                    /**
                     * 发送服务确认通知
                     */
                    taskExecutor.execute(new SendServiceConfirmTplTask(appointment));
                }
            }
        }

        appointment.setUpdateAt(new Date().getTime());
        appointment.setUpdateBy(userId);

        if (appointmentMapper.updateByPrimaryKeySelective(appointment) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    /**
     * 发送服务确认通知
     * 
     * @ClassName: SendServiceChangeTplTask
     * @Description: TODO
     * @author Comsys-guj
     * @date 2015年8月13日 下午10:27:13
     * 
     */
    private class SendServiceConfirmTplTask implements Runnable {

        private Appointment appointment;

        public SendServiceConfirmTplTask(Appointment appointment) {
            this.appointment = appointment;
        }

        public void run() {
            WxUser2member wxUser2member = wxUser2memberMapper.selectByMemberId(appointment.getMemberId());
            TplMessageUtil.sendServiceConfirmTpl(wxUser2member.getOpenid(), Appointment.TypeStr[appointment.getType()],
                    appointment.getArriveTime());
        }

    }
}