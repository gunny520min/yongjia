package com.yongjia.wxkit.vo.recv;

public class WxRecvEventMsg extends WxRecvMsg {

	private String event;
	private String eventKey;
	private String ticket;
	private String latitude;
	private String longitude;
	private String precision;

	public WxRecvEventMsg(WxRecvMsg msg, String event, String eventKey) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
	}

	public WxRecvEventMsg(WxRecvMsg msg, String event, String eventKey,
			String ticket) {
		super(msg);
		this.event = event;
		this.eventKey = eventKey;
		this.ticket = ticket;
	}

	public WxRecvEventMsg(WxRecvMsg msg, String event, String latitude,
			String longitude, String precision) {
		super(msg);
		this.event = event;
		this.latitude = latitude;
		this.longitude = longitude;
		this.precision = precision;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@Override
	public String toString() {
		return "WxRecvEventMsg [event=" + getEvent() + ", eventKey=" + getEventKey()
				+ ", ticket=" + getTicket() + ", latitude=" + getLatitude()
				+ ", longitude=" + getLongitude() + ", precision=" + getPrecision()
				+ ", fromUser=" + getFromUser() + ", msgType=" + getMsgType() + "]";
	}

}
