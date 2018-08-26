<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default content-main">
          <form:form modelAttribute="boardVO" name="question" method="post" action="/board/insertBoard.do">
              <input type="hidden" name="page" value="${page.page }"/>
              <input type="hidden" name="perPageNum" value="${page.perPageNum }"/>
              <input type="hidden" name="searchType" value="${page.searchType }"/>
              <input type="hidden" name="keyword" value="${page.keyword }"/>
         
              <div class="form-group">
                  <form:label for="title" path="title">제목</form:label>
                  <form:errors path="title" cssClass="help-block"/>
                  <form:input type="text" cssClass="form-control" path="title" id="title" placeholder="제목"/>
              </div>
              <div class="form-group">
                  <form:label for="content" path="content">내용</form:label>
                  <form:errors path="content" cssClass="help-block"/>
                  <form:textarea path="content" id="content" rows="5" cssClass="form-control"></form:textarea>
              </div>
              <a href="/board/getBoardList.do?page=${page.page }&perPageNum=${page.perPageNum}&searchType=${page.searchType}&keyword=${page.keyword}" class="btn btn-default">취소</a>
              <form:button type="submit" class="btn btn-success clearfix pull-right">등록</form:button>
              <div class="clearfix" />
          </form:form>
        </div>
    </div>
</div>
<script src="/resources/js/ckeditor5-build-classic/ckeditor.js"></script>
<script>
ClassicEditor
    .create( document.querySelector( '#content' ) )
    .catch( error => {
        console.error( error );
});
</script>

<%@ include file="../include/resources.jsp" %>
<%@ include file="../include/footer.jsp" %>