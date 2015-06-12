package com.yongjia.wxkit.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.yongjia.utils.DataUtils;
import com.yongjia.wxkit.vo.recv.WxRecvEventMsg;
import com.yongjia.wxkit.vo.recv.WxRecvMsg;

public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {
	private Log log = LogFactory.getLog(WxRecvEventMsgParser.class);

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg)
			throws JDOMException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		String ticket = getElementText(root, "Ticket");
		// 二维码
		if (DataUtils.isNotNullOrEmpty(ticket)) {
			return new WxRecvEventMsg(msg, event, eventKey, ticket);

		}
		// 位置推送
		String lat = getElementText(root, "Latitude");
		String lng = getElementText(root, "Longitude");
		String prec = getElementText(root, "Precision");
		log.info("lat:`" + lat + "`, lng:`" + lng +"`");
		if (DataUtils.isNotNullOrEmpty(lat) && DataUtils.isNotNullOrEmpty(lng)) {
			return new WxRecvEventMsg(msg, event, lat, lng, prec);
		}
		return new WxRecvEventMsg(msg, event, eventKey);
	}

}
