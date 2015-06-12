package com.yongjia.wxkit.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.yongjia.wxkit.vo.recv.WxRecvMsg;
import com.yongjia.wxkit.vo.recv.WxRecvTextMsg;


public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected WxRecvTextMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvTextMsg(msg, getElementText(root, "Content"));
	}

	
}
