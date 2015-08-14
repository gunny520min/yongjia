<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>车管家</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="keeper bg-muted">
    <section>
      <div class="banner">
        <div class="slider swipe">
          <div class="swipe-wrap">
            <c:forEach var="item" items="${activity }">
              <a href="/wx/view/newsDetail?id=${item.id}"><img src="${ item.pic }"></a>
            </c:forEach>
          </div>
        </div>
      </div>

      <div class="list border compact">
        <a class="item item-oneline item-icon-right" href="/wx/view/roadRescue">
          <i class="icon icon-nav-help-small"></i>
          道路救援
          <i class="icon icon-more"></i>
        </a>
        <c:choose>
            <c:when test="${wxuser.id != null}">
            <a class="item item-oneline item-icon-right reserve">
          <i class="icon icon-nav-reserve-small"></i>
          预约服务
          <i class="icon icon-more"></i>
        </a>
            </c:when>
            <c:otherwise>
            <a class="item item-oneline item-icon-right login-required">
          <i class="icon icon-nav-reserve-small"></i>
          预约服务
          <i class="icon icon-more"></i>
        </a>
            </c:otherwise>
          </c:choose>
        
        <c:choose>
            <c:when test="${wxuser.id != null}">
            <a class="item item-oneline item-icon-right" href="/wx/view/buycarHome">
          		<i class="icon icon-nav-buy-small"></i>
          购车计划
          		<i class="icon icon-more"></i>
        	</a>
            </c:when>
            <c:otherwise>
            <a class="item item-oneline item-icon-right login-required" >
        		<i class="icon icon-nav-buy-small"></i>
          购车计划
          		<i class="icon icon-more"></i>
        	</a>
            </c:otherwise>
          </c:choose>
      </div>
    </section>
    <script id="reserve" type="text/swig-template">
      <div class="masklayer">
        <div class="popup services padding">
	<c:choose>
		<c:when test="${wxuser.status == 2}">
          <a class="button button-block button-primary" href="/wx/view/appointWeixiu">预约维修</a>
          <a class="button button-block button-primary" href="/wx/view/appointBaoyang">预约保养</a>
          <a class="button button-block button-primary" href="/wx/view/appointKanche">预约看车</a>
          <a class="button button-block button-primary" href="/wx/view/appointNianshen">预约年审</a>
          <a class="button button-block button-primary" href="/wx/view/appointQita">其他</a>
		</c:when>
		<c:otherwise>
		  <a class="button button-block button-primary owner-required">预约维修</a>
          <a class="button button-block button-primary owner-required">预约保养</a>
          <a class="button button-block button-primary" href="/wx/view/appointKanche">预约看车</a>
          <a class="button button-block button-primary owner-required">预约年审</a>
          <a class="button button-block button-primary owner-required">其他</a>
		</c:otherwise>
	</c:choose>
        </div>
      </div>
    </script>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/keeper.js"></script>
    <script id="loginRequired" type="text/swig-template">
      <div class="masklayer flex">
        <div class="modal col">
          <div class="wrap">
            <h3 class="title text-center">温馨提示</h3>
            <div class="content text-center small">您需要先注册成为会员才可以哟~</div>
          </div>
          <div class="footer">
            <button class="text-center button text-primary cancel">取消</button>
            <a class="text-center button text-primary confirm" href="/wx/view/wxuserEdit">确定</a>
          </div>
        </div>
      </div>
    </script>
    <script id="ownerRequired" type="text/swig-template">
      <div class="masklayer flex">
        <div class="modal col">
          <div class="wrap">
            <h3 class="title text-center">温馨提示</h3>
            <div class="content text-center small">您需要绑定车辆成为车主才可以哟~</div>
          </div>
          <div class="footer">
            <button class="text-center button text-primary cancel">取消</button>
            <a class="text-center button text-primary confirm" href="/wx/view/addCar">确定</a>
          </div>
        </div>
      </div>
    </script>
  </body>
</html>