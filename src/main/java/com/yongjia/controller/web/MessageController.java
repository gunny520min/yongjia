package com.yongjia.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.MessageMapper;
import com.yongjia.model.Message;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/message")
public class MessageController extends WebBaseController {

    private static Logger log = Logger.getLogger(MessageController.class);

    @Autowired
    private MessageMapper messageMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        
        Long totalCount = messageMapper.countAll();
        List<Message> messageList = messageMapper.selectAll(getPageMap(pageNo, pageSize));
        
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, messageList);
    }
    
    @RequestMapping("/add")
    @ResponseBody
    public Map add(Message message, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        String userName = CookieUtil.getUserName(request);
        Long nowTime = System.currentTimeMillis();
        message.setCreateAt(nowTime);
        message.setCreateBy(userId);
        message.setCreateByName(userName);
        message.setUpdateAt(nowTime);
        message.setUpdateBy(userId);
        message.setUpdateByName(userName);
        message.setStatus(Message.StatusActive);
        messageMapper.insertSelective(message);
        return ToJsonUtil.toListMap(200, "success", null);
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map update(Message message, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        String userName = CookieUtil.getUserName(request);
        Long nowTime = System.currentTimeMillis();
        message.setUpdateAt(nowTime);
        message.setUpdateBy(userId);
        message.setUpdateByName(userName);
        messageMapper.updateByPrimaryKeySelective(message);
        return ToJsonUtil.toListMap(200, "success", null);
    }

    @RequestMapping("/toggle")
    @ResponseBody
    public Map statusToggle(Long id, Integer status, HttpServletRequest request, HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        Long nowTime = System.currentTimeMillis();
        String userName = CookieUtil.getUserName(request);
        
        Message message = messageMapper.selectByPrimaryKey(id);
        message.setStatus(status);
        message.setUpdateAt(nowTime);
        message.setUpdateBy(userId);
        message.setUpdateByName(userName);
        messageMapper.updateByPrimaryKeySelective(message);
        return ToJsonUtil.toListMap(200, "success", null);
    }
    
//    @RequestMapping("/pushToggle")
//    @ResponseBody
//    public Map pushToggle(Long id, Integer pushFlag, HttpServletRequest request, HttpServletResponse response) {
//        Message message = messageMapper.selectByPrimaryKey(id);
//        message.setPushFlag(pushFlag);
//        messageMapper.updateByPrimaryKeySelective(message);
//        return ToJsonUtil.toListMap(200, "success", null);
//    }
    
}