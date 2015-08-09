package com.yongjia.controller.wx;

import com.yongjia.controller.web.WebBaseController;
import com.yongjia.wxkit.utils.WxPropertiesUtil;


public class WxBaseController extends WebBaseController{

    /**
     */
    public static final String AppID = WxPropertiesUtil.getProperty("app_id");
    public static final String AppSecret = WxPropertiesUtil.getProperty("app_secret");
    public static final String Token = WxPropertiesUtil.getProperty("app_token");
}