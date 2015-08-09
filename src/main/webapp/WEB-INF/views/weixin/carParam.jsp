<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>车辆参数</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="parameters bg-muted">
    <section>
      <div class="list compact">
        <div class="item item-divider">${carModel.carModelName}</div>
        <c:forEach var="group" items="${carParams}">
          <div class="item item-divider group">${group.name}：</div>
          <c:forEach var="item" items="${group.value}">
            <div class="item item-oneline">${item.name}<span class="pull-right">${item.value}</span></div>
          </c:forEach>
        </c:forEach>
      </div>
    </section>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/hall.js"></script>
  </body>
</html>