package com.yongjia.wxkit.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yongjia.utils.HttpClientUtil;


/**
 * 使用微信高级接口 获取openid等信息
 * 
 * @author magenm
 * 
 */
public class OpenIdUtil {

	private static Logger log = Logger.getLogger(OpenIdUtil.class);

	/**
	 * 获取CODE，在获取OPENID的时候使用
	 * 
	 * @param appid
	 *            公众号对应的appid
	 * @param redirectUrl
	 *            获取成功后的跳转页面
	 * @param scope
	 *            是否弹出授权页面的标识
	 * @param state
	 *            参数
	 * @return code 在跳转至redirectUrl时，以参数code=XX的方式传递
	 */
	public static String getCode(String appid, String redirectUrl,
			String scope, String state) {

		String url = WxPropertiesUtil.getProperty(ParamString.CODE_URL);
		try {
			url = url.replace("{1}", appid);
			url = url.replace("{2}", URLEncoder.encode(redirectUrl,WxPropertiesUtil.getProperty(ParamString.ENCODING)));
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

}
