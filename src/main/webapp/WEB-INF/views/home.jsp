<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="include/header.jsp" %>

<div class="container" id="main">
	<div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
		<div class="panel panel-default content-main">
			<p>Index Page</p>
			<c:choose>
				<c:when test="${empty member }">
					<a href="/user/login.do" class="btn btn-default">로그인</a>
				</c:when>
				<c:otherwise>
					<form action="<c:url value='/logout' />" method="post">
						<sec:csrfInput/>
						<button class="btn btn-default">로그아웃</button>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<%@ include file="include/resources.jsp" %>
<%@ include file="include/footer.jsp" %>