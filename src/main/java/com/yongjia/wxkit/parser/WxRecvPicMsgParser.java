package com.yongjia.wxkit.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.yongjia.wxkit.vo.recv.WxRecvMsg;
import com.yongjia.wxkit.vo.recv.WxRecvPicMsg;


public class WxRecvPicMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvPicMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvPicMsg(msg, getElementText(root, "PicUrl"));
	}

}
