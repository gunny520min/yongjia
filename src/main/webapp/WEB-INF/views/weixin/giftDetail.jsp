<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 积分换礼详情 -->
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>详情</title>
    <link rel="shortcut icon" href="images/favicon.ico"/>
    <link rel="stylesheet" href="css/base.css"/>
    <link rel="stylesheet" href="css/app.css"/>
  </head>
  <body class="gift bg-muted">
    <section class="has-footer">
      <div class="banner">
        <div class="figure center-block">
          <img src="images/holder/gift.png" />
        </div>
      </div>
      <div class="details">
        <h3>
          商品或服务的主题名称
          <a class="pull-right text-warning text-center action" id="howto">
            <span>3000</span>
            <small>怎么赚积分</small>
          </a>
        </h3>
        <div class="rewrite padding">
          <p>某某DVD导航主线路板由6层环氧树脂板构成，系统方案均由世界顶级品牌公司提供。</p>
          <p>国内首创-国内唯一全数字处理技术，采用夏普800*480高清数字真彩触摸屏某某高清数字真彩触摸屏清晰度高，色彩逼真，触摸屏不震动，采用下拉式，折叠式菜单，防静电。</p>
          <p>采用日本东芝原装激光头，防尘设计，三年无需清洗某某产品30大特色。采用Wince 5.0最新操作系统，支持多种地图版本，支持车友DIY,可实现</p>
        </div>
      </div>
    </section>
    
    <footer class="bar fixed noborder bar-button">
      <a class="button button-warning button-block">我要兑换</a>
    </footer>
    
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

    <script src="js/base.js"></script>
    <script>
      $('#howto').on('click', function() {
        Template.render('#methods');
      });
    </script>
</body>
</html>