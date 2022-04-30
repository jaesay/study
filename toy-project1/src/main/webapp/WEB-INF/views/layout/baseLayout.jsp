<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ include file="../include/tagLibrary.jsp" %>
<!DOCTYPE html>
<html lang="kr">
	<head>
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <sec:csrfMetaTags/>
	    <title><tiles:getAsString name="title" /></title>
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
	    <!--[if lt IE 9]>
	    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
	    <![endif]-->
	    <link href="/resources/css/styles.css" rel="stylesheet">
	    <c:if test="${!empty css}">
	    	<link href="/resources/css/${css }.css" rel="stylesheet">
	    </c:if>
	</head>
<body>
	<tiles:insertAttribute name="header"/>
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="resources" />
	<tiles:insertAttribute name="footer" />
</body>
</html>