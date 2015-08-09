package com.yongjia.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected static final int defaultPageSize = 20;

    protected Integer getPageNo(Integer pageNo) {
        if (pageNo == null || pageNo == 0) {
            pageNo = 1;
        }
        return pageNo;
    }
    
    protected Integer getPageSize(Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            pageSize = defaultPageSize;
        }
        return pageSize;
    }

    protected Map<String, Integer> getPageMap(Integer pageNo, Integer pageSize) {
        
        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        Integer pageStart = (getPageNo(pageNo) - 1) * getPageSize(pageSize);
        pageMap.put("pageStart", pageStart);
        pageMap.put("pageSize", getPageSize(pageSize));

        return pageMap;
    }

    protected void render(Object data, HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}