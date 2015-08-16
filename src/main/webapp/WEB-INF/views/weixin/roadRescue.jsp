<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>道路救援</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="rescue bg-muted">
    <section class="has-footer">
      <a class="adviser text-center">
        <div class="figure center-block">
          <img src="${shost}/weixin/images/holder/adviser3.png" />
        </div>
      </a>
      <div class="list padding">
        <div class="item item-divider text-center">救援电话</div>
        <a class="button button-block button-primary" href="tel:010-87654321">010-87654321</a>
        <a class="button button-block button-primary" href="tel:010-87654321">010-87654321</a>
      </div>
    </section>
    <script src="${shost}/weixin/js/base.js"></script>
</body>
</html>