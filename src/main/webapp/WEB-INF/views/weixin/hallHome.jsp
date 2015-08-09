<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>展厅</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="styles bg-muted">
    <section>
      <div class="list compact panel">
        <c:forEach var="item" items="${hallList}">
          <a class="item item-thumbnail item-icon-right" href="/wx/view/hallDetail?id=${item.id}">
            <div class="thumbnail">
              <img src="${item.img}" />
            </div>
            <div class="information">
              <h3 class="ellipsis">${item.typeName}</h3>
              <small>共有${item.carModelCount }款车型</small>
            </div>
            <i class="icon icon-more"></i>
          </a>
        </c:forEach>
      </div>
    </section>
    <script id="hall" type="text/swig-template">
      {% for item in list %}
        <a class="item item-thumbnail item-icon-right" href="/wx/view/hallDetail?id={{ item.id }}">
          <div class="thumbnail">
            <img src="{{ item.img }}" />
          </div>
          <div class="information">
            <h3 class="ellipsis">{{ item.typeName }}</h3>
            <small>共有{{ item.carModelCount }}款车型</small>
          </div>
          <i class="icon icon-more"></i>
        </a>
      {% endfor %}
    </script>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/hall.js"></script>
  </body>
</html>