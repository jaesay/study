<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../include/header.jsp" %>
<div class="container" id="main">
   <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-default content-main">
			<c:if test="${param.containsKey('error') }">
				<span style="color:red;">
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message }" />
				</span>
			</c:if>
			<c:url var="loginUrl" value="/login" />
			<form:form action="${loginUrl }" method="post">
				<div class="form-group">
					<label for="memberId">사용자 아이디</label>
					 <input class="form-control" id="memberId" name="memberId" placeholder="ID">
				</div>
				<div class="form-group">
					<label for="password">비밀번호</label> 
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				</div>
				<button type="submit" class="btn btn-success clearfix pull-right">로그인</button>
				<div class="clearfix" />
			</form:form>
		</div>
	</div>
</div>

<%@ include file="../include/resources.jsp" %>
<%@ include file="../include/footer.jsp"%>