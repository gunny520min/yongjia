<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>抢客户</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="plans bg-muted">
  <jsp:useBean id="date" class="java.util.Date" />
    <section class="has-footer">
      <div class="list padding compact">
        <c:choose>
          <c:when test="${pCustomer != null}">
            <div class="item" data-id="${pCustomer.id}">
              <div class="title">
                客户姓名：${pCustomer.name}
              </div>
              <div class="summary">
                <p>联系电话：${pCustomer.connectMobile }</p>
                <p>意向车型：${pCustomer.carModel} - ${pCustomer.carColor}</p>
                <p>购车预算：${pCustomer.buyBudget}万</p>
                <p>购车方式：${paytypeStrs[pCustomer.payType]}</p>
                <p>购车次数：${buytypeStrs[pCustomer.buyType]}</p>
                <p>购车用途：${buyforStrs[pCustomer.buyFor]} </p>
                <p>购车时间：
                  <jsp:setProperty name="date" property="time" value="${pCustomer.buyDate}" />
                  <fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
                </p>
                <i class="icon icon-detail"></i>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="padding text-center small">暂时还没有客户</div>
          </c:otherwise>
        </c:choose>
      </div>
    </section>
    <c:if test="${pCustomer != null}">
	    <c:if test="${pCustomer.serviceBy == null}">
	      <footer class="bar fixed noborder bar-button">
	        <button class="button button-primary button-block">抢客户</button>
	      </footer>
	    </c:if>
	    <c:if test="${pCustomer.serviceBy != null}">
	      <footer class="bar fixed noborder bar-button">
	        <button class="button button-primary button-block" disabled>客户已经被抢走了</button>
	      </footer>
	    </c:if>
    </c:if>
    <script id="success" type="text/swig-template">
      <div class="masklayer flex">
        <div class="modal col">
          <div class="wrap">
            <h3 class="title text-center">温馨提示</h3>
            <div class="content text-center small">抢客户成功啦！</div>
          </div>
          <a class="footer center-block text-center" href="/wx/view/salerCustomer">
  			<span class="text-primary">确定</span>
		  </a>
        </div>
      </div>
    </script>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/grab.js"></script>
  </body>
</html>