<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/tagLibrary.jsp" %>
<div class="container" id="main">
	<div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
		<div class="panel panel-default content-main">
			<form:form modelAttribute="board" name="question" method="post" action="/board/updateBoard.do">
				<input type="hidden" name="page" value="${page.page }"/>
				<input type="hidden" name="perPageNum" value="${page.perPageNum }"/>
				<input type="hidden" name="searchType" value="${page.searchType }"/>
                <input type="hidden" name="keyword" value="${page.keyword }"/>
			
				<form:input type="hidden" path="bid" value="${board.bid }" />
				<div class="form-group">
					<form:label for="userid" path="userid">글쓴이</form:label>
					<input class="form-control" id="userid" name="userid" value="${board.userid }" disabled />
				</div>
				<div class="form-group">
					<form:label for="title" path="title">제목</form:label>
					<form:errors path="title" cssClass="help-block"/>
					<form:input type="text" cssClass="form-control" id="title" path="title" />
				</div>
				<div class="form-group">
					<form:label for="content" path="content">내용</form:label>
					<form:errors path="content" cssClass="help-block"/>
					<form:textarea path="content" id="content" rows="5" cssClass="form-control"></form:textarea>
				</div>
				
				<a href="/board/getBoardList.do?page=${page.page }&perPageNum=${page.perPageNum}" class="btn btn-default">취소</a>
				<button type="submit" class="btn btn-success clearfix pull-right">수정</button>
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