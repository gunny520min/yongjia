package com.yongjia.controller.wx;

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
import com.yongjia.dao.MessageMapper;
import com.yongjia.model.Message;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/message")
public class WxMessageController extends BaseController {

    private static Logger log = Logger.getLogger(WxMessageController.class);

    @Autowired
    private MessageMapper messageMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long totalCount = messageMapper.countNews();
            List<Message> messageList = null;
            if (totalCount > 0) {
                messageList = messageMapper.selectNews(getPageMap(pageNo, pageSize));
            }

            return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                    messageList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(500, "server error", null);
        }
    }
}