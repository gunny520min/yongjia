<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <c:choose>
      <c:when test="${wxuser.name != null}">
        <title>${wxuser.name}</title>
      </c:when>
      <c:when test="${wxuser.nickname != null}">
        <title>${wxuser.nickname}</title>
      </c:when>
      <c:otherwise>
        <title>我的资料</title>
      </c:otherwise>
    </c:choose>
    <link rel="shortcut icon" href="${shost }/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost }/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost }/weixin/css/app.css"/>
  </head>
  <body class="profile bg-muted">
    <section>
      <div class="banner">
        <div class="figure center-block">
          <img src="${wxuser.headimgurl}" />
        </div>
        <div class="nav">
          <c:choose>
            <c:when test="${wxuser.id != null}">
              <a href="/wx/view/wxuserInfo"><i class="icon icon-user"></i> 会员信息</a>
              <a href="/wx/view/myPoint"><i class="icon icon-point"></i> 我的积分</a>
            </c:when>
            <c:otherwise>
              <a href="/wx/view/wxuserEdit"><i class="icon icon-user"></i> 会员信息</a>
              <a class="action login-required"><i class="icon icon-point"></i> 我的积分</a>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div class="list border compact">
      	<c:choose>
            <c:when test="${wxuser.id != null}">
            <a class="item item-oneline item-icon-right" href="/wx/view/myCars">
          <i class="icon icon-nav-sign-small"></i>
         我的车辆
          <i class="icon icon-more"></i>
        </a>
            </c:when>
            <c:otherwise>
            <a class="item item-oneline item-icon-right action login-required">
          <i class="icon icon-nav-sign-small"></i>
         我的车辆
          <i class="icon icon-more"></i>
        </a>
            </c:otherwise>
          </c:choose>
          
        <c:choose>
            <c:when test="${wxuser.id != null}">
            <a class="item item-oneline item-icon-right" href="/wx/view/sign">
          <i class="icon icon-nav-sign-small"></i>
          签到得积分
          <i class="icon icon-more"></i>
        </a>
            </c:when>
            <c:otherwise>
            <a class="item item-oneline item-icon-right action login-required" >
          <i class="icon icon-nav-sign-small"></i>
          签到得积分
          <i class="icon icon-more"></i>
        </a>
            </c:otherwise>
          </c:choose>
        
        <a class="item item-oneline item-icon-right" href="/wx/view/gift">
          <i class="icon icon-nav-gift-small"></i>
          积分换礼
          <i class="icon icon-more"></i>
        </a>
        <a class="item item-oneline item-icon-right" href="/wx/view/news">
          <i class="icon icon-nav-news-small"></i>
          资讯吧
          <i class="icon icon-more"></i>
        </a>
        <c:choose>
            <c:when test="${wxuser.id != null}">
            <a class="item item-oneline item-icon-right" href="/wx/view/myAppointment">
          <i class="icon icon-nav-msg-small"></i>
          我的预约
          <i class="icon icon-more"></i>
        </a>
            </c:when>
            <c:otherwise>
            <a class="item item-oneline item-icon-right action login-required" >
          <i class="icon icon-nav-msg-small"></i>
          我的预约
          <i class="icon icon-more"></i>
        </a>
            </c:otherwise>
          </c:choose>
        
      </div>
    </section>
    <script id="loginRequired" type="text/swig-template">
      <div class="masklayer flex">
        <div class="modal col">
          <div class="wrap">
            <h3 class="title text-center">温馨提示</h3>
            <div class="content text-center small">您需要先注册成为会员才可以哟~</div>
          </div>
          <div class="footer">
            <button class="text-center button text-primary cancel">取消</button>
            <a class="text-center button text-primary confirm" href="/wx/view/wxuserEdit">确定</a>
          </div>
        </div>
      </div>
    </script>
    <script src="${shost }/weixin/js/base.js"></script>
    <script src="${shost }/weixin/js/home.js"></script>
  </body>
</html>