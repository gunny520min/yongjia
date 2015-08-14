package com.yongjia.wxkit.template.uitls;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Data;
import com.yongjia.controller.wx.WxBaseController;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.sms.bean.SmsResultBean;
import com.yongjia.sms.utils.SmsUtil;
import com.yongjia.utils.DateUtil;
import com.yongjia.wxkit.template.bean.ServiceCommonBean;
import com.yongjia.wxkit.template.bean.StructBean;
import com.yongjia.wxkit.utils.WeixinUtil;
import com.yongjia.wxkit.utils.WxPropertiesUtil;

public class TplMessageUtil {
    public static final String TopColor = "#FF0000";
    public static final String DataColor = "#173177";

    public static final String YongjiaAddress = "永佳丰田";

    public static void main(String[] args) throws IOException, URISyntaxException {
        sendServiceConfirmTpl("ovK7Ijrw-yzJMkXMyDQD0X_SG_Nw", "年审", System.currentTimeMillis());
    }

    /**
     * 服务确认通知
     * 
     * @param openid
     * @param serviceContent
     * @param serviceTime
     * @return
     */
    public static boolean sendServiceConfirmTpl(String openid, String serviceContent, Long serviceTime) {
        String tplId = "8nVqJeAh92jIs8sjXGyjhhT7hqs57mKoByfYqSrcVX4";
        ServiceCommonBean serviceCommonBean = new ServiceCommonBean();
        serviceCommonBean.setTemplate_id(tplId);
        serviceCommonBean.setTopcolor(TopColor);
        serviceCommonBean.setTouser(openid);
        serviceCommonBean.setUrl("");
        Map<String, StructBean> data = new HashMap<String, StructBean>();
        StructBean firstStruct = new StructBean("尊敬的业主你好，你申请的" + serviceContent + "已经确认", DataColor);
        data.put("first", firstStruct);
        StructBean keyword1Struct = new StructBean(serviceContent, DataColor);
        data.put("keyword1", keyword1Struct);
        StructBean keyword2Struct = new StructBean(DateUtil.formatDatetime(new Date(serviceTime), "yyyy年MM月dd日 HH点"),
                DataColor);
        data.put("keyword2", keyword2Struct);
        StructBean keyword3Struct = new StructBean(YongjiaAddress, DataColor);
        data.put("keyword3", keyword3Struct);
        StructBean remarkStruct = new StructBean("", DataColor);
        data.put("remark", remarkStruct);
        serviceCommonBean.setData(data);

        String accessToken = WeixinUtil.getToken(WxBaseController.AppID, WxBaseController.AppSecret);
        return WeixinUtil.sendTplMessage(accessToken, JSONObject.toJSONString(serviceCommonBean));
    }

    /**
     * 管理员通知
     * 
     * @param openid
     * @param title
     * @param managerName
     * @param content
     * @param url
     * @return
     */
    public static boolean sendManagerNotifyTpl(String openid, String title, String managerName, String content,
            String url) {
        String tplId = "VwHJaApdca9Ac_5DDvmAkKhTbX6h_4IyExJ1FAgGdtI";
        ServiceCommonBean serviceCommonBean = new ServiceCommonBean();
        serviceCommonBean.setTemplate_id(tplId);
        serviceCommonBean.setTopcolor(TopColor);
        serviceCommonBean.setTouser(openid);
        serviceCommonBean.setUrl(url);
        Map<String, StructBean> data = new HashMap<String, StructBean>();
        StructBean firstStruct = new StructBean(title, DataColor);
        data.put("first", firstStruct);
        StructBean keyword1Struct = new StructBean(managerName, DataColor);
        data.put("keyword1", keyword1Struct);
        StructBean keyword2Struct = new StructBean(content, DataColor);
        data.put("keyword2", keyword2Struct);
        StructBean remarkStruct = new StructBean("", DataColor);
        data.put("remark", remarkStruct);
        serviceCommonBean.setData(data);

        String accessToken = WeixinUtil.getToken(WxBaseController.AppID, WxBaseController.AppSecret);
        return WeixinUtil.sendTplMessage(accessToken, JSONObject.toJSONString(serviceCommonBean));
    }

    /**
     * 积分变化消息
     * 
     * @param openid
     * @param title
     * @param managerName
     * @param content
     * @param url
     * @return
     */
    public static boolean sendPointChangeTpl(String openid, MemberPointRecord memberPointRecord, Integer leftPoint) {
        String tplId = "ed5P-0fG2FwYe9MRMHcq4Y-q2_OpwicY3_9T1JThP4M";
        ServiceCommonBean serviceCommonBean = new ServiceCommonBean();
        serviceCommonBean.setTemplate_id(tplId);
        serviceCommonBean.setTopcolor(TopColor);
        serviceCommonBean.setTouser(openid);
        Map<String, StructBean> data = new HashMap<String, StructBean>();
        StructBean firstStruct = new StructBean("您的积分变更如下", DataColor);
        data.put("first", firstStruct);
        
        StructBean fieldNameStruct = new StructBean("变动原因", DataColor);// {{FieldName.DATA}}:{{Account.DATA}}
        data.put("FieldName", fieldNameStruct);
        StructBean accountStruct = new StructBean(memberPointRecord.getAction(), DataColor);
        data.put("account", accountStruct);
        String typeStr;
        if (memberPointRecord.getType().equals(MemberPointRecord.TypeGet)) {
            typeStr = "新增";
        } else {
            typeStr = "使用";
        }
        StructBean changeStruct = new StructBean(typeStr, DataColor);// {{change.DATA}}积分:{{CreditChange.DATA}}
        data.put("change", changeStruct);
        StructBean creditChangeStruct = new StructBean(memberPointRecord.getPoint()+"", DataColor);
        data.put("CreditChange", creditChangeStruct);

        StructBean totalStruct = new StructBean(leftPoint+"", DataColor);
        data.put("CreditTotal", totalStruct);

        StructBean remarkStruct = new StructBean("", DataColor);
        data.put("remark", remarkStruct);

        serviceCommonBean.setData(data);

        String accessToken = WeixinUtil.getToken(WxBaseController.AppID, WxBaseController.AppSecret);
        return WeixinUtil.sendTplMessage(accessToken, JSONObject.toJSONString(serviceCommonBean));
    }
}
