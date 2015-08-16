package com.yongjia.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;

public class WebInterceptor implements HandlerInterceptor {

    private Logger log = Logger.getLogger(WebInterceptor.class);

    public WebInterceptor() {
    }

    private String mappingURL;// 利用正则映射到需要拦截的路径

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    private static final String loginURL = "/web/login";
    private static final String logoutURL = "/web/logout";
    private static final String wxURL = "/wx";
    private static final String wxApiURL = "/wx/api";

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链, 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        log.info("==============执行顺序: 1、preHandle================");
        log.info("mappingURL is " + mappingURL);
        String url = request.getRequestURL().toString();
        log.info("request getRequestURL is " + url);
        log.info("request getContextPath is " + request.getContextPath());
        log.info("request getPathInfo is " + request.getPathInfo());
        if (mappingURL == null || (url.contains(mappingURL) && !url.contains(loginURL) && !url.contains(logoutURL))) {
            log.info("request url match mappingURL");
            if (CookieUtil.getUserID(request) == null) {
                render(ToJsonUtil.toEntityStr(401, "页面已失效，请重新登录", null), response);
                return false;
            } else {
                CookieUtil.refreshCookie(request, response);
            }
        } else if (url.contains(wxURL) && !url.contains(wxApiURL)) {
            if (CookieUtil.getOpenid(request) == null) {
                String openid = request.getParameter("openid");
                if (openid != null && openid.length() > 0) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(CookieUtil.OPEN_ID, openid);
                    CookieUtil.setIdentity(request, response, params, 0);
                    return true;
                }
                render(ToJsonUtil.toEntityStr(401, "请从微信登录", null), response);
                return false;
                // Map<String, String> params = new HashMap<String, String>();
                // params.put(CookieUtil.OPEN_ID, "ovK7Ijrw-yzJMkXMyDQD0X_SG_Nw");
                // params.put(CookieUtil.MEMBER_ID, "5");
                // CookieUtil.setIdentity(request, response, params, 0);
            } else {
                CookieUtil.refreshCookie(request, response);
            }
        }
        return true;
    }

    /**
     * 
     * @param data
     * @param response
     */
    private void render(Object data, HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        log.info("==============执行顺序: 2、postHandle================");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        log.info("==============执行顺序: 3、afterCompletion================");
    }

}
