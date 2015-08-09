<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>验证车主身份</title>
    <link rel="shortcut icon" href="images/favicon.ico"/>
    <link rel="stylesheet" href="css/base.css"/>
    <link rel="stylesheet" href="css/app.css"/>
  </head>
  <body class="verify bg-muted">
    <form method="post">
      <section class="has-footer">
        <div class="list">
          <div class="item item-divider">车主姓名</div>
          <div class="item item-input">
            <input type="text" placeholder="请输入您的姓名" maxlength="20" />
          </div>
          <div class="item item-divider">车主手机号</div>
          <div class="item item-input">
            <input type="tel" placeholder="请输入您的手机号" maxlength="11" />
          </div>
          <div class="item item-divider">短信验证码<a class="pull-right action text-primary">发送验证码</a></div>
          <div class="item item-input">
            <input type="text" placeholder="请输入您的验证码" maxlength="6" />
          </div>
        </div>
      </section>
      <footer class="bar fixed noborder bar-button">
        <a class="button button-primary button-block">立即验证</a>
      </footer>
    </form>
    <script src="js/base.js"></script>
</body>
</html>
