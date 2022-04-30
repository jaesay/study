<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./tagLibrary.jsp" %>
<!-- script references -->
<script src="/webjars/jquery/3.3.1/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/scripts.js"></script>
<script src="/webjars/mustache/2.2.1/mustache.min.js"></script>
<c:if test="${!empty js}">
   	<script src="/resources/js/${js }.js"></script>
</c:if>