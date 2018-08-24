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
					<label for="memberId"><spring:message code="message.user.login.id"/></label>
					 <input class="form-control" id="memberId" name="memberId" placeholder="ID">
				</div>
				<div class="form-group">
					<label for="password"><spring:message code="message.user.login.password"/></label> 
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				</div>
				<div class="checkbox">
					<label>
						<input name="remember-me" type="checkbox"/>Remember me
					</label>
				</div>
				<button type="submit" class="btn btn-success clearfix pull-right"><spring:message code="message.user.login.loginBtn"/></button>
				<div class="clearfix" />
			</form:form>
		</div>
	</div>
</div>

<%@ include file="../include/resources.jsp" %>
<%@ include file="../include/footer.jsp"%>