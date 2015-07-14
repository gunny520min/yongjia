package com.yongjia.utils;

import org.springframework.ui.Model;

public class SessionUtil {
    public static final String UserID = "yongjia_user_id";
    public static final String OpenID = "yongjia_openid";

    public static void SetUserID(Model model, int userId) {
        model.addAttribute(UserID, userId);
    }
    public static void SetOpenID(Model model, String openid) {
        model.addAttribute(OpenID, openid);
    }
    
    public static void cleanSession(Model model) {
        
    }
}
