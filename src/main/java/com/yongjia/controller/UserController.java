package com.yongjia.controller;
 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.AdminUserMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.AdminUser;
import com.yongjia.model.User;
import com.yongjia.utils.ToJsonUtil;
 
@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	UserMapper userMapper;
	@Autowired
	AdminUserMapper adminUserMapper;;
	
    @RequestMapping("index")
    public String index(){
    	System.out.println("1111111111");
    	User user = new User();
        user.setNickname("你好22222");
        user.setState(221);
        userMapper.insertSelective(user);
        return "index";
    }
    
    @RequestMapping("get")
    @ResponseBody
    public AdminUser getUser(Integer id) {
    	return adminUserMapper.selectByPrimaryKey(id);
    }
    
    @RequestMapping("add")
    @ResponseBody
    public Map addUser(@ModelAttribute AdminUser adminUser, HttpServletRequest request, HttpServletResponse response) {
    	adminUserMapper.insertSelective(adminUser);
    	return ToJsonUtil.toEntity(200, "success", adminUser);
    }
     
}