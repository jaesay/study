<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/tagLibrary.jsp" %>
<div class="container" id="main">
	<div class="col-md-10 col-md-offset-1">
		<div class="panel panel-default">
			<c:choose>
				<c:when test="${!empty boardList }">
					<table class="table text-center">
						<thead>
							<tr>
								<th class="col-md-1 text-center">#</th>
								<th class="col-md-3 text-center">제목</th>
								<th class="col-md-2 text-center">작성자</th>
								<th class="col-md-2 text-center">등록일</th>
								<th class="col-md-1 text-center">댓글수</th>
								<th class="col-md-1 text-center">조회수</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${boardList }" var="board" varStatus="status">
							<tr>
								<td>${board.bid }</td>
								<td><a href="/board/getBoard.do${pagination.makeQueryString(pagination.pageVO.page) }&bid=${board.bid }">${board.title }</a></td>
								<td>${board.userid }</td>
								<td><fmt:formatDate value="${board.regdate }" pattern="yy-MM-dd HH:mm:ss"/></td>
								<td>${board.commentcnt }</td>
								<td>${board.viewcnt }</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<p>등록된 글이 없습니다.</p>
				</c:otherwise>
			</c:choose>
			<div class="text-center">
				<ul class="pagination">
					<c:if test="${pagination.prev }">
						<li><a href="/board/getBoardList.do${pagination.makeQueryString(pagination.startPage - 1) }">&laquo;</a></li>
					</c:if>
					<c:forEach begin="${pagination.startPage }" end="${pagination.endPage }" var="idx">
						<li <c:out value="${pagination.pageVO.page == idx?'class=active':'' }"/>>
							<a href="/board/getBoardList.do${pagination.makeQueryString(idx) }">${idx }</a>
						</li>
					</c:forEach>
					<c:if test="${pagination.next && pagination.endPage > 0 }">
						<li><a href="/board/getBoardList.do${pagination.makeQueryString(pagination.endPage + 1) }">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
			<div class="panel-right">
				<select name="searchType">
					<option value="n" <c:out value="${page.searchType == null?'selected':'' }" />>---</option>
					<option value="t" <c:out value="${page.searchType eq 't'?'selected':'' }" />>제목</option>
					<option value="w" <c:out value="${page.searchType eq 'w'?'selected':'' }" />>작성자</option>
					<option value="tc" <c:out value="${page.searchType eq 'tc'?'selected':'' }" />>제목 + 내용</option>
				</select>
				<input type="text"  name="keyword" id="keyword" value="${page.keyword }"/>
				<a href="/board/getBoardList.do?page=1&perPageNum=${page.perPageNum}" id="searchBtn" class="btn btn-default btn-primary">검색</a>
				<a href="/board/insertBoard.do${pagination.makeQueryString(pagination.pageVO.page)}" class="btn btn-primary">등록</a>
			</div>
		</div>
	</div>
</div>