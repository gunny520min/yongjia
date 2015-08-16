<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>登录</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="login bg-muted">
    <form method="post" onsubmit="return false;">
      <section>
        <div class="list">
          <div class="item item-divider">账户</div>
          <div class="item item-input">
            <input type="text" name="account" placeholder="请输入您的账户" />
          </div>
          <div class="item item-divider">密码</div>
          <div class="item item-input">
            <input type="password" name="pwd" placeholder="请输入您的密码" />
          </div>
        </div>
        <div class="padding">
          <button class="button button-primary button-block submit">登录</button>
        </div>
      </section>
    </form>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/login.js"></script>
  </body>
</html>