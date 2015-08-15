<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>购车计划</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="plan bg-muted">
    <form method="post" onsubmit="return false;">
      <section class="has-footer">
        <div class="list">
          <div class="item item-divider">联系方式</div>
          <div class="item item-input">
            <input type="tel" name="connectMobile" placeholder="您的联系电话" value="${wxuser.mobile}" />
          </div>
          <div class="item item-divider">选择车型</div>
          <div class="item item-oneline item-icon-right ellipsis action choose">
            <span>尚未选择</span><i class="icon icon-more"></i>
          </div>
          <div class="item item-divider">第几次购车</div>
          <div class="item item-input">
            <select name="buyType">
              <option value="0">首次</option>
              <option value="1">增购</option>
              <option value="2">换购</option>
            </select>
          </div>
          <div class="item item-divider">购车用途</div>
          <div class="item item-input">
            <select name="buyFor">
              <option value="0">家用</option>
              <option value="1">公司</option>
              <option value="2">其他</option>
            </select>
          </div>
          <div class="item item-divider">购车预算 <small class="text-muted">(万元)</small></div>
          <div class="item item-input">
            <input type="text" name="buyBudget" placeholder="您的购车预算" />
          </div>
          <div class="item item-divider">购车时间</div>
          <div class="item item-input">
            <input type="date" name="buyDate" placeholder="您的购车时间" />
          </div>
          <div class="item item-divider">付款方式</div>
          <div class="item item-input">
            <select name="payType">
              <option value="0">全款</option>
              <option value="1">贷款</option>
            </select>
          </div>
        </div>
      </section>
      <footer class="bar fixed noborder bar-button">
        <button class="button button-primary button-block">提交</button>
      </footer>
    </form>
    <script id="commonSelect" type="text/swig-template">
      <div class="masklayer">
        <div class="popup">
          <h3 class="title text-center">{{ title }}</h3>
          <div class="panel">
            <div class="list compact">
              {% for item in list %}
                <a class="item item-oneline item-icon-right action" data-id="{{ item[key] }}" data-name="{{ item[value] }}" data-colors="{{item.colors}}">{{ item[value] }}<i class="icon icon-more"></i></a>
              {% endfor %}
            </div>
          </div>
        </div>
      </div>
    </script>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/plans.js"></script>
  </body>
</html>
