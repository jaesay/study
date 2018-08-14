<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default content-main">
          <form name="question" method="post" action="/board/insertBoard.do">
              <div class="form-group">
                  <label for="userid">글쓴이</label>
                  <input class="form-control" id="userid" name="userid" placeholder="글쓴이"/>
              </div>
              <div class="form-group">
                  <label for="title">제목</label>
                  <input type="text" class="form-control" id="title" name="title" placeholder="제목"/>
              </div>
              <div class="form-group">
                  <label for="content">내용</label>
                  <textarea name="content" id="content" rows="5" class="form-control"></textarea>
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">등록</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>

<%@ include file="../include/footer.jsp" %>