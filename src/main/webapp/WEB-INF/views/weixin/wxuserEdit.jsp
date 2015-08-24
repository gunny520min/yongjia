<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>编辑资料</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="settings bg-muted">
    <form method="post" action="/wx/member/edit">
      <section class="has-footer">
        <div class="list">
          <div class="item item-divider">姓名</div>
          <div class="item item-input">
            <input name="name" value="${wxuser.name == null ? wxuser.nickname : wxuser.name}" type="text" placeholder="请输入您的姓名" maxlength="20" />
          </div>
          <div class="item item-divider">性别</div>
          <c:set var="sex" value="${wxuser.memberSex == null ? wxuser.sex : wxuser.memberSex}" />
          <div class="item item-input">
            <select name="sex">
              <option value="0" ${sex == 0 ? 'selected' : ''}>保密</option>
              <option value="1" ${sex == 1 ? 'selected' : ''}>先生</option>
              <option value="2" ${sex == 2 ? 'selected' : ''}>女士</option>
            </select>
          </div>
          <div class="item item-divider">手机号</div>
          <div class="item item-input">
            <input name="mobile" value="${wxuser.mobile}" type="tel" placeholder="请输入您的手机号" maxlength="11" />
          </div>
          <div class="captcha">
            <div class="item item-divider captcha">短信验证码<a class="pull-right action text-primary">发送验证码</a></div>
            <div class="item item-input">
              <input name="valiCode" type="text" placeholder="请输入您的验证码" maxlength="6" />
            </div>
          </div>
        </div>
      </section>

      <footer class="bar fixed noborder bar-button">
        <button class="button button-primary button-block">保存</button>
      </footer>
    </form>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/profile.js"></script>
</body>
</html>