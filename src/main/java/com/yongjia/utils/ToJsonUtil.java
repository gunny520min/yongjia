package com.yongjia.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ToJsonUtil {
    private static final Log log = LogFactory.getLog(ToJsonUtil.class);

    public static final String toEntityStr(Integer code, String message, Object entity) {
        return JSONObject.fromObject(toEntityMap(code, message, entity)).toString();
    }

    public static final Map toEntityMap(Integer code, String message, Object entity) {
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

    public static final Map toListMap(Integer code, String message, List list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        Map<String, Object> entityMap = new HashMap<String, Object>();
        if (list != null) {
            entityMap.put("list", list);
        }
        entityMap.put("message", message);
        map.put("data", entityMap);

        return map;
    }

    public static final Map toPageMap(Integer code, String message, Integer pageNo, Integer pageSize,
            Long totalCount, List list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("pageNo", pageNo);
        entityMap.put("pageSize", pageSize);
        entityMap.put("totalCount", totalCount);
        if (list != null) {
            entityMap.put("list", list);
        }
        entityMap.put("message", message);
        map.put("data", entityMap);

        return map;
    }

    public static final String toListStr(Integer code, String message, List list) {
        return JSONObject.fromObject(toListMap(code, message, list)).toString();
    }

}
