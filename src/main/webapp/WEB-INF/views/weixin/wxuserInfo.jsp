<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>我的资料</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="information bg-muted">
    <section class="has-footer">
      <div class="list">
        <div class="item item-oneline figure">
          <span class="pull-left"><i class="icon icon-profile-about setting-icon-left"></i>头像</span>
          <span class="pull-right head">
            <img src="${wxuser.headimgurl}" />
          </span>
          <input type="file" name="file" id="upload" />
          <i class="icon icon-detail"></i>
        </div>
        <div class="item item-oneline">
          <span class="pull-left"><i class="icon icon-profile-name setting-icon-left"></i>姓名</span>
          <span class="pull-right text-gray">${wxuser.name}</span>
        </div>
        <div class="item item-oneline">
          <span class="pull-left"><i class="icon icon-profile-sex setting-icon-left"></i>性别</span>
          <c:choose>
            <c:when test="${wxuser.memberSex == 1}">
              <span class="pull-right text-gray">先生</span>
            </c:when>
            <c:when test="${wxuser.memberSex == 2}">
              <span class="pull-right text-gray">女士</span>
            </c:when>
            <c:otherwise>
              <span class="pull-right text-gray">保密</span>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="item item-oneline">
          <span class="pull-left"><i class="icon icon-profile-mobile setting-icon-left"></i>手机</span>
          <span class="pull-right text-gray">${wxuser.mobile}</span>
        </div>
      </div>
    </section>
    <footer class="bar fixed noborder bar-buttons">
      <div class="row">
        <div class="col">
          <a class="button button-warning button-block button-outline" href="/wx/view/wxuserEdit">编辑个人资料</a>
        </div>
        <c:if test="${wxuser.status == 1 }">
	        <div class="col">
	          <a class="button button-warning button-block" href="/wx/view/addCar">验证为车主</a>
	        </div>
        </c:if>
        <c:if test="${wxuser.status == 2 }">
	        <div class="col">
	          <a class="button button-warning button-block" href="/wx/view/myCars">查看我的车</a>
	        </div>
        </c:if>
      </div>
    </footer>

    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/profile.js"></script>
</body>
</html>