<%@ page contentType="text/html;charset=UTF-8" language="java"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<title>积分换礼</title>
<link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico" />
<link rel="stylesheet" href="${shost}/weixin/css/base.css" />
<link rel="stylesheet" href="${shost}/weixin/css/app.css" />
</head>
<body class="gifts bg-muted">
	<section>
		<div class="banner">
			<img src="${shost}/weixin/images/profile/gift.jpg" />
		</div>

		<div class="board">
			<div class="header clearfix">
				<h3 class="pull-left">
					<small>我的积分</small> <span class="text-warning">${point}</span>
				</h3>
				<a class="pull-right small text-primary action" id="howto">如何赚积分</a>
			</div>
			<div class="panel clearfix">
				<c:forEach var="item" items="${giftList}">
					<div class="goods">
						<div class="wrap">
							<a class="figure" href="/wx/view/giftDetail?id=${item.id}">
								<div class="cell">
									<img src="${item.pic}" />
								</div>
							</a>
							<div class="information">
								<div class="cost clearfix">
									<span class="pull-left">${item.name}</span> <span
										class="text-warning pull-right"><i
										class="icon icon-dollar"></i>${item.point}</span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
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
	<script id="gift" type="text/swig-template">
		{% for item in list %}
        <div class="goods">
          <div class="wrap">
            <a class="figure" href="/wx/view/giftDetail?id={{ item.id }}">
              <div class="cell">
                <img src="{{ item.pic }}" />
              </div>
            </a>
            <div class="information">
              <div class="cost clearfix">
                <span class="pull-left">{{ item.name }}</span>
                <span class="text-warning pull-right"><i class="icon icon-dollar"></i>{{ item.point }}</span>
              </div>
            </div>
          </div>
        </div>
		{% endfor %}
      </script>
	<script src="${shost}/weixin/js/base.js"></script>
	<script src="${shost}/weixin/js/gifts.js"></script>
</body>
</html>