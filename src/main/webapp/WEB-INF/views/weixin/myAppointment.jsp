<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <title>我的预约</title>
    <link rel="shortcut icon" href="${shost}/weixin/images/favicon.ico"/>
    <link rel="stylesheet" href="${shost}/weixin/css/base.css"/>
    <link rel="stylesheet" href="${shost}/weixin/css/app.css"/>
  </head>
  <body class="reserve bg-muted">
  	<jsp:useBean id="date" class="java.util.Date" />
    <section>
      <div class="list border compact">
        <c:forEach var="item" items="${appointmentList}">
          <c:if test="${item.type == 1}">
            <div class="item action">
              <div class="summary">
                <span class="name">预约维修</span>
                <%@include file="reserve/status.jsp" %>
                <small class="text-muted pull-right">
                  <jsp:setProperty name="date" property="time" value="${item.arriveTime == null ? item.appointTime : item.arriveTime}" />
                  <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" />
                </small>
              </div>
              <div class="details">
                <p>车型：${item.carType}</p>
                <p>联系电话：${item.connectPhone}</p>
                <p>问题描述：${item.problemDesc}</p>
              </div>
            </div>
          </c:if>

          <c:if test="${item.type == 2}">
            <div class="item action">
              <div class="summary">
                <span class="name">预约保养</span>
                <%@include file="reserve/status.jsp" %>
                <small class="text-muted pull-right">
                  
                  <jsp:setProperty name="date" property="time" value="${item.arriveTime == null ? item.appointTime : item.arriveTime}" />
                  <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" />
                </small>
              </div>
              <div class="details">
                <p>车型：${item.carType}</p>
                <p>联系电话：${item.connectPhone}</p>
                <p>当前里程：${item.kilo}/公里</p>
              </div>
            </div>
          </c:if>

          <c:if test="${item.type == 3}">
            <div class="item action">
              <div class="summary">
                <span class="name">预约看车</span>
                <%@include file="reserve/status.jsp" %>
                <small class="text-muted pull-right">
                  <jsp:setProperty name="date" property="time" value="${item.arriveTime == null ? item.appointTime : item.arriveTime}" />
                  <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" />
                </small>
              </div>
              <div class="details">
                <p>车型：${item.carType}</p>
                <p>联系电话：${item.connectPhone}</p>
                <p>是否试驾：${item.isTestDrive == 1 ? '是' : '否'}</p>
              </div>
            </div>
          </c:if>

          <c:if test="${item.type == 4}">
            <div class="item action">
              <div class="summary">
                <span class="name">预约年审</span>
                <%@include file="reserve/status.jsp" %>
                <small class="text-muted pull-right">
                  <jsp:setProperty name="date" property="time" value="${item.arriveTime == null ? item.appointTime : item.arriveTime}" />
                  <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" />
                </small>
              </div>
              <div class="details">
                <p>车型：${item.carType}</p>
                <p>联系电话：${item.connectPhone}</p>
                <p>当前里程：${item.kilo}/公里</p>
              </div>
            </div>
          </c:if>

          <c:if test="${item.type == 5}">
            <div class="item action">
              <div class="summary">
                <span class="name">其他</span>
                <%@include file="reserve/status.jsp" %>
                <small class="text-muted pull-right">
                  <jsp:setProperty name="date" property="time" value="${item.arriveTime == null ? item.appointTime : item.arriveTime}" />
                  <fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm" />
                </small>
              </div>
              <div class="details">
                <p>联系电话：${item.connectPhone}</p>
                <p>预约内容：${item.appointContent}</p>
              </div>
            </div>
          </c:if>

        </c:forEach>
      </div>
    </section>
    <script id="reserve" type="text/swig-template">
    {% for item in list %}
      <div class="item action">
        <div class="summary">
          <span class="name">{{ item.type|reserveType }}</span>
          {% if item.status === 0 %}
            <span class="status text-danger">{{ item.status|reserveStatus }}</span>
          {% elif item.status === 1 %}
            <span class="status">{{ item.status|reserveStatus }}</span>
          {% elif item.status === 2 %}
            <span class="status text-success">{{ item.status|reserveStatus }}</span>
          {% elif item.status === 3 %}
            <span class="status text-muted">{{ item.status|reserveStatus }}</span>
          {% endif %}
          {% if item.arriveTime %}
            <small class="text-muted pull-right">{{ item.arriveTime|date('Y-m-d H:i') }}</small>
          {% else %}
            <small class="text-muted pull-right">{{ item.appointTime|date('Y-m-d H:i') }}</small>
          {% endif %}
        </div>
        <div class="details">
          {% if item.type === 1%}
            <p>车型：{{ item.carType }}</p>
            <p>联系电话： {{ item.connectPhone }}</p>
            <p>问题描述：{{ item.problemDesc }}</p>
          {% elif item.type === 2%}
            <p>车型：{{ item.carType }}</p>
            <p>联系电话： {{ item.connectPhone }}</p>
            <p>当前里程：{{ item.kilo }}/万公里</p>
          {% elif item.type === 3%}
            <p>车型：{{ item.carType }}</p>
            <p>联系电话： {{ item.connectPhone }}</p>
            <p>是否试驾：{{ item.isTestDrive|isTestDrive }}</p>
          {% elif item.type === 4%}
            <p>车型：{{ item.carType }}</p>
            <p>联系电话： {{ item.connectPhone }}</p>
            <p>当前里程：{{ item.kilo }}/万公里</p>
          {% elif item.type === 5%}
            <p>联系电话： {{ item.connectPhone }}</p>
            <p>预约内容：{{ item.appointContent }}</p>
          {% endif %}
        </div>
      </div>
    {% endfor %}
    </script>
    <script src="${shost}/weixin/js/base.js"></script>
    <script src="${shost}/weixin/js/reserve.js"></script>
  </body>
</html>