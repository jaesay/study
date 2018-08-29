<%@ include file="./tagLibrary.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tilesx:useAttribute name="current"/>
<nav class="navbar navbar-fixed-top header">
    <div class="col-md-12">
        <div class="navbar-header">

            <a href="../index.html" class="navbar-brand">MyBOARD</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse1">
                <i class="glyphicon glyphicon-search"></i>
            </button>

        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse1">
            <form class="navbar-form pull-left">
                <div class="input-group" style="max-width:470px;">
                    <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
                    <div class="input-group-btn">
                        <button class="btn btn-default btn-primary" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-bell"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="https://github.com/jaesay/my-spring" target="_blank">Github</a></li>
                        <li><a href="https://facebook.com" target="_blank">Facebook</a></li>
                    </ul>
                </li>
                <c:choose>
                	<c:when test="${empty member }">
		                <li><a href="/user/login.do"><spring:message code="message.header.nav.login"/></a></li>
		                <li><a href="/user/signup.do"><spring:message code="message.header.nav.signup"/></a></li>
                	</c:when>
                	<c:otherwise>
                		<li><a href="/user/view-profile.do"><spring:message code="message.header.nav.profile"/></a></li>
                	</c:otherwise>
                </c:choose>
                <li><a href="?lang=ko"><spring:message code="message.header.nav.language.ko"/></a></li>
                <li><a href="?lang=en"><spring:message code="message.header.nav.language.en"/></a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="navbar navbar-default" id="subnav">
    <div class="col-md-12">
        <div class="navbar-header">
            <a href="#" style="margin-left:15px;" class="navbar-btn btn btn-default btn-plus dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-home" style="color:#dd1111;"></i> Home <small><i class="glyphicon glyphicon-chevron-down"></i></small></a>
            <ul class="nav dropdown-menu">
                <li><a href="../user/profile.html"><i class="glyphicon glyphicon-user" style="color:#1111dd;"></i> Profile</a></li>
                <li class="nav-divider"></li>
                <li><a href="#"><i class="glyphicon glyphicon-cog" style="color:#dd1111;"></i> Settings</a></li>
            </ul>
            
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse2">
            	<span class="sr-only">Toggle navigation</span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse2">
            <ul class="nav navbar-nav navbar-right">
                <li class="${current=='home' ? 'active' : '' }"><a href="/"><spring:message code="message.header.nav.home"/></a></li>
                <li class="${current=='board' ? 'active' : '' }"><a href="/board/getBoardList.do" role="button"><spring:message code="message.header.nav.board"/></a></li>
            </ul>
        </div>
    </div>
</div>