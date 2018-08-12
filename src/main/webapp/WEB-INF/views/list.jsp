<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>글 목록</h1>
<table border="1" cellpadding="0" cellspacing="0" width="600">
<tr>
	<th bgcolor="orange" width="200">제목</th>
	<th bgcolor="orange" width="150">작성자</th>
	<th bgcolor="orange" width="150">등록일</th>
	<th bgcolor="orange" width="100">조회수</th>
</tr>

<c:forEach items="${boardList }" var="board">
<tr>
	<td align="left"><a href="#">${board.title }</a></td>
	<td>${board.userid }</td>
	<td>${board.regdate }</td>
	<td>${board.viewcnt }</td>
</tr>
</c:forEach>
</table>

</body>
</html>