package com.yongjia.controller.web;

import java.io.File;
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
import com.yongjia.dao.CarHallMapper;
import com.yongjia.dao.CarModelMapper;
import com.yongjia.dao.CarModelParamMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.MessageMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxMessageMapper;
import com.yongjia.dao.WxMsgItemMapper;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.Gift;
import com.yongjia.model.Message;
import com.yongjia.model.User;
import com.yongjia.model.WxMessage;
import com.yongjia.model.WxMsgItem;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/message")
public class MessageController extends BaseController {

    private static Logger log = Logger.getLogger(MessageController.class);

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private WxMessageMapper wxMessageMapper;
    
    @Autowired
    private WxMsgItemMapper wxMsgItemMapper;
    
    
    @RequestMapping("/list")
    @ResponseBody
    public Map list(HttpServletRequest request, HttpServletResponse response) {

//        List<Message> messageList = messageMapper.selectAll();
        List<Message> messageList = new ArrayList<Message>();
        Message message = new Message();
        message.setAuthor("gj");
        message.setContent("11111111ssss");
        message.setDescipition("test");
        message.setId(1L);
        message.setStatus(Message.StatusActive);
        message.setPushFlag(Message.PushActive);
        message.setPic("");
        message.setTitle("标题");
        messageList.add(message);
        
        message = new Message();
        message.setAuthor("gj2");
        message.setContent("11111111ssss2");
        message.setDescipition("test2");
        message.setId(2L);
        message.setStatus(Message.StatusActive);
        message.setPushFlag(Message.PushActive);
        message.setPic("");
        message.setTitle("标题2");
        messageList.add(message);
        
        return ToJsonUtil.toListMap(200, "success", messageList);
    }
    
    @RequestMapping("/add")
    @ResponseBody
    public Map add(Message message, HttpServletRequest request, HttpServletResponse response) {

        return ToJsonUtil.toListMap(200, "success", null);
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Map update(Message message, HttpServletRequest request, HttpServletResponse response) {

        return ToJsonUtil.toListMap(200, "success", null);
    }
    
    @RequestMapping("/statusToggle")
    @ResponseBody
    public Map statusToggle(Integer messageId, HttpServletRequest request, HttpServletResponse response) {

        return ToJsonUtil.toListMap(200, "success", null);
    }
    
    @RequestMapping("/pushToggle")
    @ResponseBody
    public Map pushToggle(Integer messageId, HttpServletRequest request, HttpServletResponse response) {

        return ToJsonUtil.toListMap(200, "success", null);
    }

//    @RequestMapping("/WxMsgList")
//    @ResponseBody
//    public Map WxMsgList(HttpServletRequest request, HttpServletResponse response) {
//        
////        List<WxMessage> wxMessageList =  wxMessageMapper.selectAll();
//        List<WxMessage> wxMessageList = new ArrayList<WxMessage>();
//        WxMessage wxMessage = new WxMessage();
//        wxMessage.setId(1L);
//        wxMessage.setMsgName("消息1");
//        wxMessage.setMsgType(WxMessage.);
//        wxMessage.setMsgName("消息1");
//        wxMessage.setMsgName("消息1");
//        wxMessage.setMsgName("消息1");
//        return ToJsonUtil.toListMap(200, "success", wxMessageList);
//    }

//    @RequestMapping("/addWxMsg")
//    @ResponseBody
//    public Map addWxMsg(WxMessage wxMessage, List<WxMsgItem> wxMsgItemList, HttpServletRequest request, HttpServletResponse response) {
//        
//        Long wxMessageId = wxMessageMapper.insert(wxMessage);
//        if (wxMessageId>0) {
//            for (WxMsgItem wxMsgItem : wxMsgItemList) {
//                wxMsgItem.setMsgId(wxMessageId);
//                wxMsgItemMapper.insert(wxMsgItem);
//            }
//        }
//        return ToJsonUtil.toListMap(200, "success", null);
//    }
    
}