<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>签到</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="signin bg-muted">
    <section class="has-footer">
      <div class="summary">
        <div class="row">
          <div class="col small">${week}<em class="text-warning"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd" /></em> </div>
          <div class="col small">共签到<em class="text-warning">${times}次</em></div>
          <c:if test="${status == 0}">
            <div class="col text-warning">今天未签到</div>
          </c:if>
          <c:if test="${status == 1}">
            <div class="col text-muted">今天已签到</div>
          </c:if>
        </div>
      </div>
      <div class="details small">
        <div class="panel clearfix">
          <c:forEach var="item" items="${list }">
            <c:choose>
              <c:when test="${item.times<=times}">
                <span class="box">${item.times}<em class="text-warning"><i class="icon icon-score"></i>x${item.point}</em></span>
              </c:when>
              <c:otherwise>
                <span class="box">${item.times}<em class="text-muted"><i class="icon icon-score-gray"></i>x${item.point}</em></span>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>
    </section>
    <footer class="bar fixed noborder bar-button">
      <c:if test="${status == 0}">
        <button class="button button-warning button-block"><i class="icon icon-signin"></i>立即签到 获得${list[times].point}积分</button>
      </c:if>
      <c:if test="${status == 1}">
        <button class="button button-warning button-block" disabled="disabled"><i class="icon icon-signin"></i>今天已签到</button>
      </c:if>
    </footer>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/signin.js"></script>
  </body>
</html>