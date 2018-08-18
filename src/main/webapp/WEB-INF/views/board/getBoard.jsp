<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../include/header.jsp" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
          <header class="qna-header">
              <h2 class="qna-title">${board.title }</h2>
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
                  	  <form role="form" method="post">
                  	  	  <input type="hidden" name="bid" value="${board.bid }" />
                  	  	  <input type="hidden" name="page" value="${page.page }"/>
                  	  	  <input type="hidden" name="perPageNum" value="${page.perPageNum }"/>
                  	  	  <input type="hidden" name="searchType" value="${page.searchType }"/>
              			  <input type="hidden" name="keyword" value="${page.keyword }"/>
                  	  </form>
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
                  </div>
              </article>

              <div class="qna-comment">
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong class="comment-count">${board.commentcnt }</strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles" id="comment-list">

                          <form class="submit-write">
                              <div class="form-group" style="padding:14px;">
	                          	  <input type="text" id="userid" name="userid" class="form-cotrol" placeholder="userid" />
                                  <textarea class="form-control" id="content" name="content" placeholder="Update your status"></textarea>
                              </div>
                              <button class="btn btn-success pull-right" id="comment-add-btn" type="button">답변하기</button>
                              <div class="clearfix" />
                          </form>
                          
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
			<div>
				<li>
					<button class="link-modify-article comment-edit-link">수정</button>
				</li>
				<li>
					<button class="link-modify-article comment-del-btn" data-cid={{cid}}>삭제</button>
				</li>
			</div>
		</ul>
		</div>
	</article>
	{{/commentList}}
</script>

<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<script src='//cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min.js'></script>

<script>
$("#comment-list").on("click", ".one-comment .comment-edit-link", function(event) {
	event.preventDefault();
	var parentElement = $(this).parents(".one-comment");
	
	parentElement.find(".one-comment-content").hide();
	parentElement.find(".one-comment-btns").css("visibility", "hidden");
	parentElement.find(".one-comment-form").show();
});

$("#comment-list").on("click", ".one-comment .comment-cancel-btn", function(event) {
	var parentElement = $(this).parents(".one-comment");

	parentElement.find(".one-comment-content").show();
	parentElement.find(".one-comment-btns").css("visibility", "visible");
	parentElement.find(".one-comment-form").hide();
})
var template = document.getElementById('comment-template').innerHTML;
//Mustache.parse(template);

function countComment(bid) {
	$.get("/comments/cnt/" + bid, function(data) {
		$(".comment-count").html(data);
	});
}

var bid = ${board.bid};

function getAllList() {
	$.getJSON("/comments/all/" + bid, function(data) {
		var rendered = Mustache.render(template, data);
		$(".one-comment").remove();
		$("#comment-list").prepend(rendered);
	});
}
getAllList();

$("#comment-add-btn").click(function() {
	var bid = ${board.bid};
	var userid = $("#userid").val();
	var content = $("#content").val();
	
	$.ajax({
		type: "post",
		url: "/comments",
		headers: {
			"Content-Type": "application/json"
		},
		dataType: "text",
		data: JSON.stringify({
			bid: bid,
			userid: userid,
			content: content
		})
	}).done(function(result) {
		if(result == "SUCCESS") {
			alert("등록되었습니다.");
			getAllList();
			countComment(bid);
		}
	});
});

$("#comment-list").on("click", ".one-comment .comment-del-btn", function(event) {
	if (!confirm("정말 삭제하시겠습니까?")) {
		return false;
	}
	
	var cid = $(this).data("cid");
	
	$.ajax({
		type: 'delete',
		url: "/comments/",
		headers: {
			"Content-Type": "application/json",
		},
		data: JSON.stringify({
			cid: cid,
			bid: bid
		}),
		dataType: "text",
	}).done(function(result) {
		if(result == "SUCCESS") {
			alert("삭제되었습니다.");
			getAllList();
			countComment(bid);
		}
	});
});

$("#comment-list").on("click", ".one-comment .comment-edit-btn", function(event) {
	var cid = $(this).data("cid");
	var content = $("#content" + cid).val();
	
	$.ajax({
		type: "put",
		url: "/comments/" + cid,
		headers: {
			"Content-Type": "application/json",
		},
		data: JSON.stringify({content: content}),
		dataType: "text",
		
	}).done(function(result) {
		if(result == "SUCCESS") {
			alert("수정되었습니다.");
			getAllList();
		}
	});
});

</script>



<%@ include file="../include/footer.jsp" %>