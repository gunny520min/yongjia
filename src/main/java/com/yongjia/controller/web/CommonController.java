package com.yongjia.controller.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.UserMapper;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.DataUtils;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;
import com.yongjia.utils.UpYunUtil;

@Controller
@RequestMapping("/web")
public class CommonController extends BaseController {

    private static Logger log = Logger.getLogger(CommonController.class);

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    @ResponseBody
    public Map login(String account, String pwd, HttpServletRequest request, HttpServletResponse response) {

        User user = userMapper.selectByAccount(account);
        if (user != null) {
            if (PasswordUtils.authenticatePassword(user.getPwd(), pwd)) {
                if (user.getRoleId() == 4) {
                    return ToJsonUtil.toEntityMap(403, "权限不够！", null);
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(CookieUtil.USER_ID, user.getId().toString());
                    map.put(CookieUtil.ROLE_ID, user.getRoleId().toString());
                    map.put(CookieUtil.USER_NAME, user.getName());
                    CookieUtil.setIdentity(request, response, map, user.getId());
                    return ToJsonUtil.toEntityMap(200, "success", user);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "账号或密码错误！", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "账号不存在！", null);
        }
    }

    @RequestMapping("/getCurrentUser")
    @ResponseBody
    public Map getCurrentUser(HttpServletRequest request, HttpServletResponse response) {

        Long userId = CookieUtil.getUserID(request);
        User user = userMapper.selectByPrimaryKey(userId);
        if (user==null) {
            return ToJsonUtil.toEntityMap(401, "error", null);
        }
        return ToJsonUtil.toEntityMap(200, "success", user);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map login(Model model, HttpServletRequest request, HttpServletResponse response) {

        CookieUtil.clearCookie(response);
        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/modifyPwd")
    @ResponseBody
    public Map modifyPwd(String oldpwd, String newpwd, HttpServletRequest request,
            HttpServletResponse response) {

        Long id = CookieUtil.getUserID(request);
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            if (PasswordUtils.authenticatePassword(user.getPwd(), oldpwd)) {
                user.setPwd(PasswordUtils.encode(newpwd));
                userMapper.updateByPrimaryKey(user);
                return ToJsonUtil.toEntityMap(200, "success", user);
            } else {
                return ToJsonUtil.toEntityMap(400, "密码错误！", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "账号不存在！", null);
        }
    }

    @RequestMapping("/uploadPic")
    @ResponseBody
    public Map uploadPic(File file, HttpServletRequest request, HttpServletResponse response) {

        String imgName = DataUtils.getUUID();
        try {
            String picUrl = UpYunUtil.uploadPic(file, imgName);
            if (picUrl != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("url", picUrl);
                return ToJsonUtil.toEntityMap(200, "success", picUrl);
            } else {
                return ToJsonUtil.toEntityMap(400, "上传失败", null);
            }
        } catch (IOException e) {
            return ToJsonUtil.toEntityMap(400, "上传失败", null);
        }
    }

}