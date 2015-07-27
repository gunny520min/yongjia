package com.yongjia.controller.web;

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
    public Map list(String name, String mobile, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {
        Long totalCount = userMapper.countByNameAndPhone(name, mobile);
        List<User> userList = null;
        if (totalCount > 0) {
            userList = userMapper.selectByNameAndPhone(name, mobile, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, userList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(User user, HttpServletRequest request, HttpServletResponse response) {

        Long roleId = CookieUtil.getRoleID(request);
        if (roleId < 3) {
            User u = userMapper.selectByAccount(user.getAccount());
            if (u == null) {

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
                    return ToJsonUtil.toEntityMap(400, "插入数据库错误", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "账号重复", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不足", null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(User user, HttpServletRequest request, HttpServletResponse response) {
        Long roleId = CookieUtil.getRoleID(request);
        if (roleId < 3) {
            Long userId = CookieUtil.getUserID(request);
            user.setUpdateAt(new Date().getTime());
            user.setUpdateBy(userId);

            if (userMapper.updateByPrimaryKeySelective(user) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "更新数据库失败", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不足", null);
        }
    }

    @RequestMapping("/toggle")
    @ResponseBody
    public Map toggle(Long id, Integer status, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            return ToJsonUtil.toEntityMap(400, "参数错误", null);
        }
        Long roleId = CookieUtil.getRoleID(request);
        if (roleId < 3) {
            Long webUserId = CookieUtil.getUserID(request);
            User user = userMapper.selectByPrimaryKey(id);
            user.setStatus(status);
            user.setUpdateAt(new Date().getTime());
            user.setUpdateBy(webUserId);

            if (userMapper.updateByPrimaryKeySelective(user) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不足", null);
        }
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public Map resetPwd(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            return ToJsonUtil.toEntityMap(400, "参数错误", null);
        }
        Long webUserId = CookieUtil.getUserID(request);
        User user = userMapper.selectByPrimaryKey(id);
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