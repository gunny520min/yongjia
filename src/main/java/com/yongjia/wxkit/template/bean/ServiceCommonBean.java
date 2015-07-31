package com.yongjia.wxkit.template.bean;

import java.util.HashMap;
import java.util.Map;


public class ServiceCommonBean {
	private String touser;
	private String template_id;
	private String topcolor;
	private String url;
	private Map<String, StructBean> data = new HashMap<String, StructBean>();

	public String getTouser() {
		return touser;
	}

	public void setTouser(String openid) {
		this.touser = openid;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, StructBean> getData() {
		return data;
	}

	public void setData(Map<String, StructBean> data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
