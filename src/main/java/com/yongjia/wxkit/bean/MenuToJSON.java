package com.yongjia.wxkit.bean;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 将MenuBean转为JSON对象
 * 
 * @author magenm
 * 
 */
public class MenuToJSON {

	List<MenuBean> menus;

	public MenuToJSON(List<MenuBean> menus) {
		super();
		this.menus = menus;
	}

	/**
	 * 构造菜单json
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		for (MenuBean menu : menus) {
			JSONObject obj = new JSONObject();
			obj.put(MenuBean.NAME, menu.getName());

			if (menu.isHaveChild()) {
				JSONArray chirldAry = new JSONArray();
				for (MenuBean chirld : menu.getChilds()) {
					JSONObject chirldObj = new JSONObject();
					chirldObj.put(MenuBean.NAME, chirld.getName());
					chirldObj.put(MenuBean.TYPE, chirld.getType());
					if (chirld.isVIEW()) {
						chirldObj.put(MenuBean.URL, chirld.getUrl());
					} else {
						chirldObj.put(MenuBean.KEY, chirld.getKey());
					}
					chirldAry.add(chirldObj);
				}
				obj.put(MenuBean.SUB_BUTTON, chirldAry);
			} else {
				obj.put(MenuBean.TYPE, menu.getType());
				if (menu.isVIEW()) {
					obj.put(MenuBean.URL, menu.getUrl());
				} else {
					obj.put(MenuBean.KEY, menu.getKey());
				}
			}
			ary.add(obj);
		}

		json.put(MenuBean.BUTTON, ary);
		return json;
	}
}
