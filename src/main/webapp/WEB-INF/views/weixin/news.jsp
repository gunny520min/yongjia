<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>资讯吧</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="notices bg-muted">
    <section>
      <div class="list panel">
        <c:forEach var="item" items="${messageList}">
          <a class="item item-thumbnail" href="/wx/view/newsDetail?id=${item.id}">
            <div class="thumbnail">
              <img src="${item.pic}" />
            </div>
            <div class="information">
              <h3 class="ellipsis">${item.title}</h3>
              <small class="text-muted">${item.descipition}</small>
            </div>
          </a>
        </c:forEach>
      </div>
    </section>

    <script id="notice" type="text/swig-template">
      {% for item in list %}
        <a class="item item-thumbnail">
          <div class="thumbnail">
            <img src="{{ item.pic }}" />
          </div>
          <div class="information">
            <h3 class="ellipsis">{{ item.title }}</h3>
            <small class="text-muted">{{ item.descipition }}</small>
          </div>
        </a>
      {% endfor %}
    </script>

    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/notices.js"></script>
  </body>
</html>