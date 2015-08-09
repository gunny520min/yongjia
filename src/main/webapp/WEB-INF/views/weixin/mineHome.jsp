<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<title>个人中心</title>
<link rel="shortcut icon" href="${shost }/weixin/images/favicon.ico" />
<link rel="stylesheet" href="${shost }/weixin/css/base.css" />
<link rel="stylesheet" href="${shost }/weixin/css/app.css" />
</head>
<body class="profile bg-muted">
	<section>
	<div class="banner">
		<div class="figure center-block">
			<img src="${shost }/weixin/images/profile/head.jpg" />
		</div>
		<div class="nav">
			<a href="/wx/view/myEmail"><i class="icon icon-email"></i> 未读消息(28)</a>
			<!-- <a href="settings.html"><i class="icon icon-edit"></i> 编辑资料</a> -->
			<a href="/wx/view/wxuserRegister"><i class="icon icon-user"></i> 验证车主身份</a>
		</div>
	</div>
	<nav class="blocks clearfix">
	<a href="/wx/view/addCar" class="menu"><i class="icon icon-nav-car"></i><span>我的车</span></a>
	<a href="/wx/view/myAppointment" class="menu"><i class="icon icon-nav-car"></i><span>我的预约</span></a>
	<a href="/wx/view/gift" class="menu"><i class="icon icon-nav-gift"></i><span>积分换礼</span></a>
	<a href="/wx/view/sign" class="menu"><i class="icon icon-nav-sign"></i><span>签到得积分</span></a>
	<a href="" class="menu"><i class="icon icon-nav-user"></i><span>用户中心</span></a>
	<a href="" class="menu"><i class="icon icon-nav-share"></i><span>推荐朋友</span></a>
	<a href="/wx/view/news" class="menu"><i class="icon icon-nav-news"></i><span>资讯吧</span></a>
	<a href="" class="menu"><i class="icon icon-nav-msg"></i><span>投诉意见</span></a>
	<a href="" class="menu"><i class="icon icon-nav-love"></i><span>关注车型</span></a>
	<a href="" class="menu"><i class="icon icon-nav-contrast"></i><span>车型对比</span></a>
	<a href="config.html" class="menu"><i class="icon icon-nav-plus"></i></a>
	</nav> </section>
	<script src="${shost }/weixin/js/base.js"></script>
</body>
</html>
