<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>预约看车</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="kanche bg-muted">
    <form method="post" onsubmit="return false;">
      <section class="has-footer">
        <div class="list">
          <div class="item item-divider">预约日期</div>
          <div class="item item-input">
            <input type="date" name="date" />
          </div>
          <div class="item item-divider">预约时间</div>
          <div class="item item-input">
            <input type="time" name="time" />
          </div>
          <div class="item item-divider">选择车型</div>
          <div class="item item-oneline item-icon-right action choose">
  			<span>尚未选择</span><i class="icon icon-more"></i>
		  </div>
          <div class="item item-divider">是否试驾</div>
          <div class="item item-input">
            <select name="isTestDrive">
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
          </div>
          <div class="item item-divider">联系方式</div>
          <div class="item item-input">
            <input type="tel" placeholder="请输入您的联系电话" maxlength="11" value="${wxuser.mobile}" name="connectPhone" />
          </div>
        </div>
        <div class="small text-center text-warning">（预约并准时来店送积分哦~）</div>
      </section>
      <footer class="bar fixed noborder bar-button">
        <button class="button button-primary button-block">提交</button>
      </footer>
    </form>
    <script id="success" type="text/swig-template">
      <div class="masklayer flex">
        <div class="modal col">
          <div class="wrap">
            <h3 class="title text-center">预约成功</h3>
            <div class="content text-center small">请等待客服人员与您联系</div>
          </div>
          <a class="footer center-block text-center" href="/wx/view/myAppointment">
  			<span class="text-primary">确定</span>
		  </a>
        </div>
      </div>
    </script>
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
    <script src="${shost}/weixin/js/reserve.js"></script>
</body>
</html>