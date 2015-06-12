package com.yongjia.wxkit.http;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yongjia.wxkit.utils.ParamString;
import com.yongjia.wxkit.utils.WxPropertiesUtil;
import com.yongjia.utils.HttpClientUtil;

/**
 * HTTP请求类
 * 
 * @author magenm
 * 
 */
public class HttpUtils {
	private static Logger log = Logger.getLogger(HttpUtils.class);
	/**
	 * 获取token
	 * 
	 * @param gzh
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
			log.info("getToken:"+response);
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
	 * @param gzh
	 * @return
	 */
	public static boolean generator(String token, String menuJson) {
		// post方式创建菜单
		String response = HttpClientUtil.sendPostRequestByJava(
				WxPropertiesUtil.getProperty(ParamString.CREATE_MENU).replace("{1}", token), menuJson);
		log.info("generator:"+response);
		JSONObject jsonObj = JSONObject.fromObject(response);
		if ("0".equals(jsonObj.getString("errcode"))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 生成菜单
	 * 
	 * @param gzh
	 * @return
	 */
	public static boolean templateMessage(String token, String json) {
		// post方式创建菜单
		String response = HttpClientUtil.sendPostRequestByJava(
				WxPropertiesUtil.getProperty(ParamString.TEMPLATE_MESSAGE).replace("{1}", token), json);
		log.info("templateMessage:"+response);
		JSONObject jo = JSONObject.fromObject(response);
		if ("0".equals(jo.get("errcode"))) {
			return true;
		} else {
			return false;
		}
	}

}
