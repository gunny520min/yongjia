package com.yongjia.wxkit.parser;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.yongjia.wxkit.vo.recv.WxRecvMsg;


public interface WxRecvMsgParser {
	public WxRecvMsg parser(Document doc) throws JDOMException;
}
