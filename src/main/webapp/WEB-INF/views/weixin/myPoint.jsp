<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>我的积分</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="points bg-muted">
    <section>
      <div class="padding">
        当前积分余额：<span class="text-warning">${memberPoint.point}</span>
      </div>
      <div class="list border compact">
      	<c:if test="${fn:length(memberPointRecords) == 0}">
          <div class="text-center padding small">您还没有积分记录</div>
        </c:if>
        <c:forEach var="item" items="${memberPointRecords}">
          <div class="item small">
            <div class="name clearfix">
              <span class="pull-left">${item.action}</span>
              <span class="pull-right text-muted">
                <jsp:useBean id="date" class="java.util.Date" />
                <jsp:setProperty name="date" property="time" value="${item.createAt}" />
                <fmt:formatDate value="${date}" pattern="yyyy/MM/dd HH:mm:ss" />
              </span>
            </div>
            <div class="status clearfix">
              <c:choose>
                <c:when test="${item.type == 0}">
                  <span class="pull-left text-muted">获得</span>
                  <span class="pull-right text-success">+${item.point}</span>
                </c:when>
                <c:otherwise>
                  <span class="pull-left text-muted">花费</span>
                  <span class="pull-right text-warning">-${item.point}</span>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </c:forEach>
      </div>
    </section>
    <script src="${shost}/weixin/js/base.js"></script>
</body>
</html>