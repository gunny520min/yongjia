package com.yongjia.wxkit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.yongjia.wxkit.bean.WeiXinBean;
import com.yongjia.wxkit.parser.WxMsgKit;
import com.yongjia.wxkit.vo.recv.WxRecvMsg;
import com.yongjia.wxkit.vo.send.WxSendMsg;

public final class WeiXin {
	public static boolean access(String token, String signature,
			String timestamp, String nonce) {
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);

		Collections.sort(ss);

		StringBuilder builder = new StringBuilder();
		for (String s : ss) {
			builder.append(s);
		}
		return signature.equalsIgnoreCase(HashKit.sha1(builder.toString()));
	}

	public static boolean access(WeiXinBean weixinBean) {
		if (null != weixinBean) {
			return access(weixinBean.getToken(), weixinBean.getSignature(),
					weixinBean.getTimestamp(), weixinBean.getNonce());
		} else {
			return false;
		}
	}

	public static WxRecvMsg recv(InputStream in) throws JDOMException,
			IOException {
		return WxMsgKit.parse(in);
	}

	public static void send(WxSendMsg msg, OutputStream out)
			throws JDOMException, IOException {
		Document doc = WxMsgKit.parse(msg);
		if (null != doc) {
			XMLOutputter xmlout = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");
			xmlout.setFormat(format);
			xmlout.output(doc, out);
		} else {
			Logger.getAnonymousLogger().warning("发送消息时,解析出dom为空 msg :" + msg);
		}
	}

	public static String doc2String(Document doc) {
		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
			XMLOutputter xmlout = new XMLOutputter(format);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			xmlout.output(doc, bo);
			return bo.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static WxSendMsg builderSendByRecv(WxRecvMsg msg) {
		WxRecvMsg m = new WxRecvMsg(msg);
		String from = m.getFromUser();
		m.setFromUser(m.getToUser());
		m.setToUser(from);
		m.setCreateDt((System.currentTimeMillis() / 1000) + "");
		return new WxSendMsg(m);
	}

}
