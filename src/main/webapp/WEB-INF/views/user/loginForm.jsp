<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h3>로그인폼</h3>
		<c:if test="${param.containsKey('error') }">
			<span style="color:red;">
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message }" />
			</span>
		</c:if>
		<c:url var="loginUrl" value="/login" />
		<form:form action="${loginUrl }" method="post">
			<table>
				<tr>
					<td><label for="username">사용자명</label></td>
					<td><input type="text" id="username" name="username" /></td>
				</tr>
				<tr>
					<td><label for="password">비밀번호</label></td>
					<td><input type="password" id="password" name="password" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><button>로그인</button></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>