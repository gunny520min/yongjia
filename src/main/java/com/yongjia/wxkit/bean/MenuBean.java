package com.yongjia.wxkit.bean;

import java.util.List;

/**
 * 自定义菜单Bean
 * 
 * @author magenm
 * 
 */
public class MenuBean {

	public final static String BUTTON = "button";
	public final static String SUB_BUTTON = "sub_button";
	public final static String TYPE = "type";
	public final static String NAME = "name";
	public final static String KEY = "key";
	public final static String URL = "url";

	private String type;
	private String name;
	private String key;
	private String url;
	private boolean isHaveChild = false;
	private boolean isVIEW = false;
	private List<MenuBean> childs;

	/**
	 * 有孩子
	 * 
	 * @param name
	 * @param chirlds
	 */
	public MenuBean(String name, List<MenuBean> childs) {
		super();
		this.name = name;
		this.childs = childs;
		isHaveChild = true;
	}

	/**
	 * 没有孩子，CLICK事件
	 * 
	 * @param type
	 * @param name
	 * @param key
	 */
	public MenuBean(String name, String key) {
		super();
		this.type = TypeBean.CLICK;
		this.name = name;
		this.key = key;
	}

	/**
	 * 没有孩子，VIEW事件
	 * 
	 * @param type
	 *            type不用传值，主要是为了和CLICK事件做区分，增加的字段
	 * @param name
	 * @param url
	 */
	public MenuBean(String type, String name, String url) {
		super();
		this.type = TypeBean.VIEW;
		this.name = name;
		this.url = url;
		isVIEW = true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuBean> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuBean> childs) {
		this.childs = childs;
	}

	public boolean isHaveChild() {
		return isHaveChild;
	}

	public void setHaveChirld(boolean isHaveChild) {
		this.isHaveChild = isHaveChild;
	}

	public boolean isVIEW() {
		return isVIEW;
	}

	public void setVIEW(boolean isVIEW) {
		this.isVIEW = isVIEW;
	}

}
