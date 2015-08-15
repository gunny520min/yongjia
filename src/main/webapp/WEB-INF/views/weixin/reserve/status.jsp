<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<c:if test="${item.status == 0}">
  <span class="status text-danger">待确认</span>
</c:if>
<c:if test="${item.status == 1}">
  <span class="status">已确认</span>
</c:if>
<c:if test="${item.status == 2}">
  <span class="status text-success">已完成</span>
</c:if>
<c:if test="${item.status == 3}">
  <span class="status text-muted">已取消</span>
</c:if>