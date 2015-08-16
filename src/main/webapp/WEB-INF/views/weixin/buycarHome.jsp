<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 购车计划 -->
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>购车计划</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="plans bg-muted">
  	<jsp:useBean id="date" class="java.util.Date" />
      <section class="has-footer">
        <div class="list padding compact">
	        <c:forEach var="item" items="${potentialCustomers}">
	          <div class="item">
	            <div class="title">
	              <span class="pull-left">
	              <jsp:setProperty name="date" property="time" value="${item.createAt}" />
                  <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
	              </span>
	              <span class="pull-right text-danger">
	              <c:if test="${item.serviceBy == null}">
	              等待销售顾问联络
	              </c:if>
	              <c:if test="${item.serviceBy != null}">
	              销售顾问：${item.serviceByName }
	              </c:if>
	              </span>
	            </div>
	            <div class="summary">
	              <p>${item.carModel } - ${item.carColor }</p>
	              <p>预算 ${item.buyBudget } ${paytypeStrs[item.payType] }</p>
	              <p>${buytypeStrs[item.buyType] } ${buyforStrs[item.buyFor] } </p>
	              <p>计划 
	              <jsp:setProperty name="date" property="time" value="${item.buyDate}" />
                  <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
                  	购车</p>
	              <i class="icon icon-detail"></i>
	            </div>
	          </div>
	        </c:forEach>
        </div>
      </section>
      <footer class="bar fixed noborder bar-button">
      	<a class="button button-primary button-block" href="/wx/view/buycarAdd">新增购车计划</a>
      </footer>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/plans.js"></script>
  </body>
</html>
