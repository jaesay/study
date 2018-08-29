<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/tagLibrary.jsp" %>
<div class="container" id="main">
   <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-default content-main">
          <form:form modelAttribute="memberVO" action="/user/signup.do" method="post">
              <div class="form-group">
                  <form:label for="memberId" path="memberId">사용자 아이디</form:label>
                  <form:errors path="memberId" cssClass="help-block"/>
                  <form:input cssClass="form-control" id="memberId" path="memberId" placeholder="ID" />
              </div>
              <div class="form-group">
                  <form:label for="password" path="password">비밀번호</form:label>
                  <form:errors path="password" cssClass="help-block"/>
                  <form:input type="password" cssClass="form-control" id="password" path="password" placeholder="Password" />
              </div>
              <div class="form-group">
                  <form:label for="memberName" path="memberName">이름</form:label>
                  <form:errors path="memberName" cssClass="help-block"/>
                  <form:input cssClass="form-control" id="memberName" path="memberName" placeholder="이름" />
              </div>
              <div class="form-group">
                  <form:label for="email" path="email">이메일</form:label>
                  <form:errors path="email" cssClass="help-block"/>
                  <form:input type="email" cssClass="form-control" id="email" path="email" placeholder="Email" />
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">회원가입</button>
              <div class="clearfix" />
          </form:form>
        </div>
    </div>
</div>