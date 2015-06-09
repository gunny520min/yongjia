package com.yongjia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	@ResponseBody
	public Map<String,Object> hello() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("xxx", "yyyy");
		map.put("num", 1);
		return map;
	}
}