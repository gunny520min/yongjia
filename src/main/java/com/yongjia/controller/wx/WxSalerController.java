package com.yongjia.controller.wx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.web.WebBaseController;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/saler")
public class WxSalerController extends WebBaseController {

    private static Logger log = Logger.getLogger(WxSalerController.class);

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login")
    @ResponseBody
    public Map login(String account, String pwd, HttpServletRequest request, HttpServletResponse response) {
        String openid = CookieUtil.getOpenid(request);
        User user = userMapper.selectByOpenid(openid);
        if (user!=null) {
            return ToJsonUtil.toEntityMap(400, "您已经登录了", null);
        }
        
        User saler = userMapper.selectByAccount(account);
        if (saler != null) {
            if (saler.getRoleId() == User.RoleSaler) {
                if (PasswordUtils.authenticatePassword(saler.getPwd(), pwd)) {
                    saler.setOpenid(openid);
                    if (userMapper.updateByPrimaryKeySelective(saler) > 0) {
                        return ToJsonUtil.toEntityMap(200, "success", saler);
                    } else {
                        return ToJsonUtil.toEntityMap(500, "update user error", null);
                    }
                } else {
                    return ToJsonUtil.toEntityMap(400, "账号或密码错误！", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(403, "您不是销售！", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(404, "账号不存在！", null);
        }
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public Map resetPwd(Long id, String oldpwd, String newpwd, HttpServletRequest request, HttpServletResponse response) {

        User saler = userMapper.selectByPrimaryKey(id);
        if (saler != null) {
            if (saler.getRoleId() == User.RoleSaler) {
                if (PasswordUtils.authenticatePassword(saler.getPwd(), oldpwd)) {
                    saler.setPwd(PasswordUtils.encode(newpwd));
                    userMapper.updateByPrimaryKeySelective(saler);
                    return ToJsonUtil.toEntityMap(200, "success", saler);
                } else {
                    return ToJsonUtil.toEntityMap(400, "密码错误！", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(403, "您不是销售！", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(404, "账号不存在！", null);
        }
    }

}