package com.yongjia.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yongjia.controller.BaseController;

@Controller
@RequestMapping("/")
public class WebBaseController extends BaseController{

    @RequestMapping("")
    private String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
    
    
}