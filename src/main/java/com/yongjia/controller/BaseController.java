package com.yongjia.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class BaseController {
    
    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
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