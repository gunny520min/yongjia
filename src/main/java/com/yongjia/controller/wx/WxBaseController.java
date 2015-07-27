package com.yongjia.controller.wx;

import com.yongjia.wxkit.utils.WxPropertiesUtil;


public class WxBaseController {

    /**
     */
    protected static final String AppID = WxPropertiesUtil.getProperty("app_id");
    protected static final String AppSecret = WxPropertiesUtil.getProperty("app_secret");
    protected static final String Token = WxPropertiesUtil.getProperty("app_token");
}