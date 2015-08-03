<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>车管家</title>
</head>
<body>
wxuser
<h1>openid = ${wxUser.openid }</h1>
<h1>nickname = ${wxUser.nickname }</h1>
<h1>memberId = ${wxUser.id }</h1>
<h1>point = ${wxUser.point }</h1>
saler
<h1>salerId = ${saler.id }</h1>
<h1>salerName = ${saler.name }</h1>
</body>
</html>
