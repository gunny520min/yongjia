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
<title>${message.title}</title>
<link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico" />
<link rel="stylesheet" href="${shost}/weixin/css/base.css" />
<link rel="stylesheet" href="${shost}/weixin/css/app.css" />
</head>
<body class="notice">
	<section>
		<div class="padding">
			<h2 class="text-center">${message.title}</h2>
			<h4 class="text-center">
				<jsp:useBean id="date" class="java.util.Date" />
				<jsp:setProperty name="date" property="time"
					value="${message.createAt}" />
				<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />

			</h4>
			<div class="details rewrite">${message.content}</div>
		</div>
	</section>

	<script src="${shost}/weixin/js/base.js"></script>
</body>
</html>