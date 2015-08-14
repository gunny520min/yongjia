<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>预约维修</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="addcar bg-muted">
    <form method="post" onsubmit="return false;">
      <section class="has-footer">
        <div class="list">
          <div class="item item-divider">进口/国产</div>
          <div class="item item-input">
            <select name="source">
              <option value="0">国产</option>
              <option value="1">进口</option>
            </select>
          </div>
          <div class="item item-divider">请选择车型</div>
          <div class="item item-oneline item-icon-right action type">
            <span>尚未选择</span><i class="icon icon-more"></i>
          </div>
          <div class="item item-divider">请选择车款</div>
          <div class="item item-oneline item-icon-right action style">
            <span>尚未选择</span><i class="icon icon-more"></i>
          </div>
          <div class="item item-divider">请输入车牌号</div>
          <label class="item item-input">
            <input type="text" placeholder="请输入车牌号" name="carNumber" maxlength="8" />
          </label>
          <div class="item item-divider">请输入VINNO</div>
          <label class="item item-input">
            <input type="text" placeholder="选填" name="vinNo" />
          </label>
        </div>
      </section>
      <footer class="bar fixed noborder bar-button">
        <input type="hidden" name="typeName" value="" />
        <input type="hidden" name="modelName" value="" />
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
                <a class="item item-oneline item-icon-right action" data-id="{{ item[key] }}" data-name="{{ item[value] }}">{{ item[value] }}<i class="icon icon-more"></i></a>
              {% endfor %}
            </div>
          </div>
        </div>
      </div>
    </script>

    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/profile.js"></script>
</body>
</html>