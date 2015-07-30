package com.yongjia.sms.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yongjia.sms.bean.SmsResultBean;
import com.yongjia.utils.HttpClientUtil;
import com.yongjia.wxkit.utils.WxPropertiesUtil;

/**
 * 
 * @ClassName: SmsUtil
 * @Description: TODO
 * @author Comsys-guj
 * @date 2015年7月30日 下午3:03:31
 * 
 */
public class SmsUtil {

    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    // 通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    // 发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    private static String apikey = WxPropertiesUtil.getProperty("sms_api_key");

    public static String[] SmsContent = new String[] { "【永佳丰田】您正在注册成为永佳会员，验证码是#code#。如不是本人操作请忽略。" };
    public static String[] SmsTpl = new String[] { "注册" };
    public static Long ReSendTime = 60 * 1000L;
    public static Long OverDueTime = 5 * 60 * 1000L;

    public static void main(String[] args) throws IOException, URISyntaxException {
        // 修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后用户中心首页看到
        // 修改为您要发送的手机号
        String mobile = "18511896713";

        /**************** 查账户信息调用示例 *****************/
        // System.out.println(SmsUtil.getUserInfo());

        /**************** 使用通用接口发短信(推荐) *****************/
        // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【云片网】您的验证码是1234";
        String code = "123456";
        // 发短信调用示例
        SmsResultBean result = SmsUtil.sendSms(code, mobile, 0);
        if (result == null) {
            System.out.println("result is null");
        } else {
            System.out.println(result.getCode() + "---" + result.getMsg() + "---" + result.getResult().getSid());
        }

        /**************** 使用接口发语音验证码 *****************/
        // String code = "1234";
        // System.out.println(SmsUtil.sendVoice(mobile, code));
    }

    /**
     * 取账户信息
     * 
     * @return json格式字符串
     * @throws java.io.IOException
     */
    // public static String getUserInfo() throws IOException, URISyntaxException {
    // Map<String, String> params = new HashMap<String, String>();
    // params.put("apikey", apikey);
    // return HttpClientUtil.post(URI_GET_USER_INFO, params);
    // }
    /**
     * 发送注册验证码
     * 
     * @param code
     * @param mobile
     * @return
     * @throws IOException
     */
    public static SmsResultBean sendSms(String code, String mobile, Integer type) throws IOException {
        String tpl = SmsContent[type];
        String text = tpl.replace("#code#", code);
        return sendSms(text, mobile);
    }

    /**
     * 通用接口发短信
     * 
     * @param apikey apikey
     * @param text 　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static SmsResultBean sendSms(String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        String result = HttpClientUtil.post(URI_SEND_SMS, params);
        return JSONObject.parseObject(result, SmsResultBean.class);
    }

    /**
     * 生成6位随机数
     * 
     * @return
     */
    public static String genCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 10);
        }

        return code;
    }
    /**
     * 通过接口发送语音验证码
     * 
     * @param apikey apikey
     * @param mobile 接收的手机号
     * @param code 验证码
     * @return
     */
    // public static String sendVoice(String mobile, String code) {
    // Map<String, String> params = new HashMap<String, String>();
    // params.put("apikey", apikey);
    // params.put("mobile", mobile);
    // params.put("code", code);
    // return HttpClientUtil.post(URI_SEND_VOICE, params);
    // }

}
