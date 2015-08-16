<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>我的客户</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="plans bg-muted">
  	<jsp:useBean id="date" class="java.util.Date" />
      <section>
        <div class="list padding compact">
	        <c:forEach var="item" items="${potentialCustomers}">
	          <div class="item">
	            <div class="title">
	              <span class="pull-left">
	              <jsp:setProperty name="date" property="time" value="${item.createAt}" />
                  <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
	              </span>
	            </div>
	            <div class="summary">
	              <p>客户姓名：${item.name }</p>
	              <p>联系电话：${item.connectMobile }</p>
	              <p>意向车型：${item.carModel } - ${item.carColor }</p>
	              <p>购车预算：${item.buyBudget }</p>
	              <p>购车方式：${paytypeStrs[item.payType] }</p>
	              <p>购车次数：${buytypeStrs[item.buyType] }</p>
	              <p>购车用途：${buyforStrs[item.buyFor] } </p>
	              <p>购车时间： 
	              <jsp:setProperty name="date" property="time" value="${item.buyDate}" />
                  <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
                  </p>
	              <i class="icon icon-detail"></i>
	            </div>
	          </div>
	        </c:forEach>
        </div>
      </section>
    <script src="${shost}/weixin/js/base.js"></script>
  </body>
</html>
