package com.yongjia.controller.wx;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.SmsSendRecordMapper;
import com.yongjia.model.SmsSendRecord;
import com.yongjia.sms.bean.SmsResultBean;
import com.yongjia.sms.utils.SmsUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx")
public class WxCommonController {

    private static Logger log = Logger.getLogger(WxCommonController.class);

    @Autowired
    private SmsSendRecordMapper smsMapper;

    @RequestMapping("/sendsms")
    @ResponseBody
    /**
     * type 0 注册
     * @param mobile
     * @param type
     * @param request
     * @param response
     * @return
     */
    public Map sendsms(String mobile, Integer type, HttpServletRequest request, HttpServletResponse response) {
        log.info("sendsms params : mobile = " + mobile + " --- type = " + type);
        if (type == null || type < 0 || type >= SmsUtil.SmsTpl.length) {
            return ToJsonUtil.toEntityMap(400, "参数错误！", null);
        }
        String tpl = SmsUtil.SmsTpl[type];
        SmsSendRecord smsSendRecord = smsMapper.selectByMobileAndTpl(mobile, tpl);
        Long now = System.currentTimeMillis();
        if (smsSendRecord != null && now - smsSendRecord.getCreateAt() <= SmsUtil.ReSendTime) {
            return ToJsonUtil.toEntityMap(400, "短信发送太频繁，请稍后再试", null);
        }
        String code = SmsUtil.genCode();
        if (smsSendRecord != null && now - smsSendRecord.getCreateAt() <= SmsUtil.OverDueTime) {
            /**
             * 同一时间发送相同验证码
             */
            code = smsSendRecord.getCode();
        }
        try {
            SmsResultBean result = SmsUtil.sendSms(code, mobile, type);
            if (result.getCode() == 0) {
                SmsSendRecord newSendRecord = new SmsSendRecord();
                newSendRecord.setCode(code);
                newSendRecord.setCreateAt(now);
                newSendRecord.setMobile(mobile);
                newSendRecord.setTpl(SmsUtil.SmsTpl[type]);
                smsMapper.insertSelective(newSendRecord);
                return ToJsonUtil.toEntityMap(200, "发送验证码成功", null);
            } else {
                return ToJsonUtil.toEntityMap(400, result.getMsg(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ToJsonUtil.toEntityMap(400, "发送失败", null);
    }
}