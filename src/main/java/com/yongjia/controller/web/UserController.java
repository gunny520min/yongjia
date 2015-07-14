package com.yongjia.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.BaseController;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/user")
public class UserController extends BaseController {

    private static Logger log = Logger.getLogger(UserController.class);
    private static final String defaultPwd = "123456";

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, String phone, HttpServletRequest request, HttpServletResponse response) {

//        List<User> userList = userMapper.selectByNameAndPhone(name, phone);
        List<User> userList = new ArrayList<User>();
        User user = new User();
        user.setAccount("admin");
        user.setId(1L);
        user.setCreateAt(System.currentTimeMillis());
        user.setCreateBy(1L);
        user.setUpdateAt(System.currentTimeMillis());
        user.setUpdateBy(1L);
        user.setMobile("");
        user.setName("系统管理员");
        user.setPwd("");
        user.setRoleId(User.RoleAdmin);
        user.setStatus(User.StatusActive);
        userList.add(user);
        
        user = new User();
        user.setAccount("guojing");
        user.setId(2L);
        user.setCreateAt(System.currentTimeMillis());
        user.setCreateBy(1L);
        user.setUpdateAt(System.currentTimeMillis());
        user.setUpdateBy(1L);
        user.setMobile("18511896713");
        user.setName("郭靖");
        user.setPwd("");
        user.setRoleId(User.RoleAdmin);
        user.setStatus(User.StatusActive);
        userList.add(user);
        return ToJsonUtil.toListMap(200, "success", userList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(User user, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        user.setStatus(User.StatusActive);
        user.setPwd(PasswordUtils.encode(defaultPwd));
        user.setCreateAt(System.currentTimeMillis());
        user.setCreateBy(userId);
        user.setUpdateAt(System.currentTimeMillis());
        user.setUpdateBy(userId);

        if (userMapper.insertSelective(user) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(User user, HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        user.setUpdateAt(new Date().getTime());
        user.setUpdateBy(userId);

        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/toggle")
    @ResponseBody
    // TODO userId to long
    public Map toggle(Integer userId, HttpServletRequest request, HttpServletResponse response) {

        Long webUserId = CookieUtil.getUserID(request);
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getStatus().equals(User.StatusActive)) {
            user.setStatus(User.StatusStop);
        } else {
            user.setStatus(User.StatusActive);
        }
        user.setUpdateAt(new Date().getTime());
        user.setUpdateBy(webUserId);

        if (userMapper.updateByPrimaryKeySelective(user)>0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }


    @RequestMapping("/resetPwd")
    @ResponseBody
    // TODO userId to long
    public Map resetPwd(Integer userId, HttpServletRequest request, HttpServletResponse response) {

        Long webUserId = CookieUtil.getUserID(request);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setUpdateAt(new Date().getTime());
        user.setUpdateBy(webUserId);
        user.setPwd(PasswordUtils.encode(defaultPwd));

        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

}