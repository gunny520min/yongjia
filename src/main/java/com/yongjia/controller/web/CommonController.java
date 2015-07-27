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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
        if (user == null) {
            return ToJsonUtil.toEntityMap(401, "页面已失效，请重新登录", null);
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
    public Map modifyPwd(String oldpwd, String newpwd, HttpServletRequest request, HttpServletResponse response) {

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
    public Map uploadPic(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {

        String imgName = DataUtils.getUUID()+".jpg";
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String picUrl = UpYunUtil.uploadPic(targetFile, imgName);
            if (picUrl != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("url", picUrl);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                return ToJsonUtil.toEntityMap(200, "success", picUrl);
            } else {
                log.info("upload pic fail!");
                String picUrl2 = request.getContextPath()+"/upload/"+fileName;
                return ToJsonUtil.toEntityMap(200, "success", picUrl2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ToJsonUtil.toEntityMap(400, "上传失败", null);
        }
    }

}