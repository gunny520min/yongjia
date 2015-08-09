<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>${gify.name}</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="gift bg-muted">
    <section>
      <div class="banner">
        <div class="figure center-block">
          <img src="${gift.pic}" />
        </div>
      </div>
      <div class="details">
        <h3>
          ${gift.name}
          <a class="pull-right text-warning text-center action" id="howto">
            <span>${gift.point}</span>
            <small>怎么赚积分</small>
          </a>
        </h3>
        <div class="rewrite padding">
          ${gift.content}
        </div>
      </div>
    </section>
    
    <script id="methods" type="text/swig-template">
      <div class="masklayer flex">
        <div class="list method padding col">
          <i class="icon icon-close cancel"></i>
          <a class="item item-oneline">注册会员</a>
          <a class="item item-oneline">验证车主</a>
          <a class="item item-oneline">每日签到得积分</a>
          <a class="item item-oneline">在线预约</a>
          <a class="item item-oneline">阅读分享资讯</a>
          <a class="item item-oneline">其他活动</a>
        </div>
      </div>
    </script>

    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/gifts.js"></script>
</body>
</html>