package com.yongjia.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ToJsonUtil {
	private static final Log log = LogFactory.getLog(ToJsonUtil.class);

	public static final Map<String, Object> toEntity(Integer code,
			String message, Object entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entity != null) {
			entityMap.put("entity", entity);
		}
		entityMap.put("message", message);
		map.put("data", entityMap);

		return map;
	}
	
	public static final Map<String, Object> toList(Integer code,
			String message, List<Object> entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entity != null) {
			entityMap.put("list", entity);
		}
		entityMap.put("message", message);
		map.put("data", entityMap);

		return map;
	}

}
