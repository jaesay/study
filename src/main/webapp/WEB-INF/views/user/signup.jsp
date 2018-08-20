<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원가입</h2>
	<div>
		<form action="/user/signup.do" method="post">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
			<label for="userid">아이디</label>
			<input type="text" name="userid" id="userid"/><br>
			<label for="password" >비밀번호</label>
			<input type="password" name="password" id="password"/><br>
			<label for="username">이름</label>
			<input type="text" name="username" id="username"/><br>
			<label for="email">이메일</label>
			<input type="email" name="email" id="email"/><br>
			<input type="submit" value="가입">
		</form>
	</div>
</body>
</html>