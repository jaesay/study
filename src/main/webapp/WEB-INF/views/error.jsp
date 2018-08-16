<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<div class="container" id="main">
	<div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
		<div class="panel panel-default content-main">
			<h1>Error Page</h1>
			<p>Application has encountered an error. Please contact support
				on ...</p>

			Failed URL: ${url} Exception: ${exception.message}
			<c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
			    </c:forEach>
		</div>
	</div>
</div>
<%@ include file="./include/footer.jsp" %>