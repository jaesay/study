<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="include/header.jsp" >
	<c:param name="pageTitle" value="MyOkky" />
</c:import> --%>
<div class="container" id="main">
	<div class="col-md-10 col-md-offset-1">
		<div class="panel panel-default">
			<c:choose>
				<c:when test="${!empty boardList }">
					<table class="table text-center">
						<thead>
							<tr>
								<th class="col-md-1 text-center">#</th>
								<th class="col-md-4 text-center">제목</th>
								<th class="col-md-2 text-center">작성자</th>
								<th class="col-md-2 text-center">등록일</th>
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
			<div class="panel-right"><a href="/board/insertBoard.do" class="btn btn-primary">등록</a></div>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp"%>