package com.yongjia.wxkit.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserList;
import com.yongjia.utils.HttpClientUtil;

/**
 * 使用微信高级接口 获取openid等信息
 * 
 * @author magenm
 * 
 */
public class WeixinUtil {

    private static Logger log = Logger.getLogger(WeixinUtil.class);

    /**
     * 获取CODE，在获取OPENID的时候使用
     * 
     * @param appid 公众号对应的appid
     * @param redirectUrl 获取成功后的跳转页面
     * @param scope 是否弹出授权页面的标识
     * @param state 参数
     * @return code 在跳转至redirectUrl时，以参数code=XX的方式传递
     */
    public static String getCode(String appid, String redirectUrl, String scope, String state) {

        String url = WxPropertiesUtil.getProperty(ParamString.CODE_URL);
        try {
            url = url.replace("{1}", appid);
            url = url
                    .replace("{2}", URLEncoder.encode(redirectUrl, WxPropertiesUtil.getProperty(ParamString.ENCODING)));
            url = url.replace("{3}", scope);
            url = url.replace("{4}", state);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("getCode result : " + url);

        return url;

    }

    /**
     * 获取openid
     * 
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public static String getOpenid(String appid, String secret, String code) {

        String url = WxPropertiesUtil.getProperty(ParamString.OPENID_URL);
        url = url.replace("{1}", appid);
        url = url.replace("{2}", secret);
        url = url.replace("{3}", code);

        String response = HttpClientUtil.sendPostSSLRequest(url, null);

        log.info("getOpenid result : " + response);

        JSONObject json = JSONObject.fromObject(response);

        // 错误
        if (json.containsKey("errcode")) {
            return null;
        } else {
            return json.getString(ParamString.OPENID);
        }

    }

    /**
     * 获取token
     * 
     * @param appid
     * @param appsecret
     * @return
     */
    public static String getToken(String appid, String appsecret) {
        String accessToken = null;
        try {
            String url = WxPropertiesUtil.getProperty(ParamString.GET_TOKEN_URL);
            url = url.replace("{1}", appid);
            url = url.replace("{2}", appsecret);
            // 发送请求
            String response = HttpClientUtil.sendGetRequest(url, null);
            log.info("getToken:" + response);
            JSONObject jsonArray = JSONObject.fromObject(response);
            accessToken = (String) jsonArray.get(WxPropertiesUtil.getProperty(ParamString.ACCESS_TOKEN));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 生成菜单
     * 
     * @param token
     * @param menuJson
     * @return
     */
    public static boolean generatorMenu(String token, String menuJson) {
        // post方式创建菜单
        String response = HttpClientUtil.sendPostRequestByJava(WxPropertiesUtil.getProperty(ParamString.CREATE_MENU)
                .replace("{1}", token), menuJson);
        log.info("generator:" + response);
        JSONObject jsonObj = JSONObject.fromObject(response);
        if ("0".equals(jsonObj.getString("errcode"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 模板消息
     * 
     * @param token
     * @param json
     * @return
     */
    public static boolean sendTplMessage(String token, String json) {
        System.out.println("send json : " + json);
        String response = HttpClientUtil.sendPostRequestByJava(
                WxPropertiesUtil.getProperty(ParamString.TEMPLATE_MESSAGE).replace("{1}", token), json);
        log.info("templateMessage:" + response);
        System.out.println("templateMessage:" + response);
        JSONObject jo = JSONObject.fromObject(response);
        if ("0".equals(jo.get("errcode"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取微信用户信息
     * 
     * @param token
     * @param openid
     * @return
     */
    public static WxUser getWxUserInfo(String token, String openid) {
        String url = WxPropertiesUtil.getProperty(ParamString.WX_USER_INFO);
        url = url.replace("{1}", token);
        url = url.replace("{2}", openid);
        String res = HttpClientUtil.sendGetRequest(url, WxPropertiesUtil.getProperty(ParamString.ENCODING));
        if (res.contains("errcode")) {
            log.error("获取用户信息异常：" + res);
            return null;
        } else {
            log.info("获取用户信息：" + res);
            WxUser wxUser = (WxUser) JSONObject.toBean(JSONObject.fromObject(res), WxUser.class);
            return wxUser;
        }
    }

    /**
     * 获取用户列表
     * 
     * @param token
     * @param nextOpenid
     * @return
     */
    public static WxUserList getWxUserList(String token, String nextOpenid) {
        String url = WxPropertiesUtil.getProperty(ParamString.GET_USER_LIST_URL);
        url = url.replace("{1}", token);
        url = url.replace("{2}", nextOpenid);
        String res = HttpClientUtil.sendGetRequest(url, WxPropertiesUtil.getProperty(ParamString.ENCODING));
        if (res.contains("errcode")) {
            log.error("获取用户信息异常：" + res);
            return null;
        } else {
            log.info("获取用户列表信息：" + res);
            WxUserList wxUserList = (WxUserList) JSONObject.toBean(JSONObject.fromObject(res), WxUserList.class);
            return wxUserList;
        }
    }

}
