<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
          <header class="qna-header">
				<div class="pull-left">
					<h2 class="qna-title">${board.title }</h2>
				</div>
				<div class="pull-right">
					<h2 class="text-right">
						<c:choose>
							<c:when test="${!empty member and board.userid ne member.memberId}">
								<a id="good-vote">GOOD</a>: <span id="vote-cnt">${board.votecnt }</span>
							</c:when>
							<c:otherwise>
								GOOD: <span id="vote-cnt">${board.votecnt }</span>	
							</c:otherwise>
						</c:choose>
					</h2>
				</div>
				<div class="clearfix"></div>
			</header>
          <div class="content-main">
              <article class="article">
                  <div class="article-header">
                      <div class="article-header-thumb">
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                      </div>
                      <div class="article-header-text">
                          <a href="/users/92/kimmunsu" class="article-author-name">${board.userid }</a>
                          <a href="/questions/413" class="article-header-time" title="퍼머링크">
                              <fmt:formatDate value="${board.regdate }" pattern="yy-MM-dd HH:mm:ss"/>
                              <i class="icon-link"></i>
                          </a>
                      </div>
                  </div>
                  <div class="article-doc">
                      <p>${board.content }</p>
                  </div>
                  <div class="article-util">
                  	  <c:if test="${board.userid eq member.memberId }">
	                  	  <form:form role="form" method="post">
	                  	  	  <input type="hidden" name="page" value="${page.page }"/>
	                  	  	  <input type="hidden" name="perPageNum" value="${page.perPageNum }"/>
	                  	  	  <input type="hidden" name="searchType" value="${page.searchType }"/>
	              			  <input type="hidden" name="keyword" value="${page.keyword }"/>
	                  	  </form:form>
	                      <ul class="article-util-list">
	                          <li>
	                          	  <button class="link-modify-board" type="submit">수정</button>
	                          </li>
	                          <li>
	                              <button class="link-delete-board" type="submit">삭제</button>
	                          </li>
	                          <li>
	                              <button class="link-list-board" type="submit">목록</button>
	                          </li>
	                      </ul>
                  	  </c:if>
                  </div>
              </article>

              <div class="qna-comment">
              	  <div id="bid" data-bid="${board.bid }"></div>
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong class="comment-count">${board.commentcnt }</strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles" id="comment-list">
					  	<c:if test="${!empty member }">
                          <form class="submit-write">
                              <div class="form-group" style="padding:14px;">
	                          	  <!-- <input type="text" id="userid" name="userid" class="form-cotrol" placeholder="userid" /> -->
                                  <textarea class="form-control" id="content" name="content" placeholder="Update your status"></textarea>
                              </div>
                              <button class="btn btn-success pull-right" id="comment-add-btn" type="button">답변하기</button>
                              <div class="clearfix" />
                          </form>
                        </c:if>
                        
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
</div>

<script id="comment-template" type="x-tmpl-mustache">
{{#commentList}}
<article class="article one-comment">
	<div class="article-header">
		<div class="article-header-thumb">
			<img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
		</div>
		<div class="article-header-text">
			<a href="#" class="article-author-name">{{userid}}</a>
			<div class="article-header-time">{{regdate}}</div>
		</div>
	</div>
	<div class="article-doc comment-doc">
		<div class="one-comment-content">
			{{content}}
		</div>
		<div style="display:none" class="one-comment-form">
			<div class="form-group" style="padding:14px;">
            	<textarea class="form-control" id="content{{cid}}" name="content">{{content}}</textarea>
        	</div>
			<button class="btn btn-default pull-right comment-cancel-btn" type="button">취소</button>
			<button class="btn btn-success pull-right comment-edit-btn" type="button" data-cid={{cid}}>수정</button>
		</div>
	</div>
	<div class="article-util">
	<ul class="article-util-list one-comment-btns">
		{{#isWriter}}			
		<div>
			<li>
				<button class="link-modify-article comment-edit-link">수정</button>
			</li>
			<li>
				<button class="link-modify-article comment-del-btn" data-cid={{cid}}>삭제</button>
			</li>
		</div>
		{{/isWriter}}
	</ul>
	</div>
</article>
{{/commentList}}
</script>

<%@ include file="../include/resources.jsp" %>
<!-- <script src='//cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js'></script> -->
<script src="/webjars/mustache/2.2.1/mustache.min.js"></script>
<script src="/resources/js/board.js"></script>
<%@ include file="../include/footer.jsp" %>