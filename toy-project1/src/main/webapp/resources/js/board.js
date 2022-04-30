$(document).ready(function() {
//GetBoardList
	$("#searchBtn").click(function(event){
		event.preventDefault();
		location.href=$(this).attr("href")
			+ "&searchType="
			+ $("select option:selected").val()
			+ "&keyword=" + $("#keyword").val();
	});
// GetBoard
	var formObj = $("form[role='form']");
	
	$(".link-modify-board").on("click", function(event) {
		formObj.attr("action", "/board/updateBoard.do");
		formObj.attr("method", "get");
		formObj.submit();
	});
	
	$(".link-delete-board").on("click", function(event) {
		if (!confirm("정말 삭제하시겠습니까?")) {
			return false;
		}
		formObj.attr("action", "/board/deleteBoard.do");
		formObj.submit();
	});
	
	$(".link-list-board").on("click", function(event) {
		formObj.attr("action", "/board/getBoardList.do");
		formObj.attr("method", "get");
		formObj.submit();
	});
	
	//댓글
	$(function () {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
	
	
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
	
	function countComment(bid) {
		$.get("/comments/cnt/" + bid, function(data) {
			$(".comment-count").html(data);
		});
	}
	
	function getFormattedDate(dateTime) {
		var date = new Date(dateTime);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var hour = date.getHours();
		var min = date.getMinutes();
		var sec = date.getSeconds();
		var formatted = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
		
		return formatted
	}
	
	
	function getAllList() {
		var bid = $("#bid").data("bid");
		$.getJSON("/comments/all/" + bid, function(data) {
			var list = data.commentList;
			for ( var key in list) {
				if(list[key].userid == data.memberId) {
					data.commentList[key].isWriter = true;		
				}
				data.commentList[key].regdate = getFormattedDate(list[key].regdate);
				data.commentList[key].upddate = getFormattedDate(list[key].upddate);
			}
			
			var rendered = Mustache.render(template, data);
			$(".one-comment").remove();
			$("#comment-list").prepend(rendered);
		});
	}
	getAllList();
	
	$("#comment-add-btn").click(function() {
		var bid = $("#bid").data("bid");
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
				content: content
			})
		}).done(function(result) {
			if(result == "SUCCESS") {
				getAllList();
				countComment(bid);
				$("#content").val("");
			}
		});
	});
	
	$("#comment-list").on("click", ".one-comment .comment-del-btn", function(event) {
		if (!confirm("정말 삭제하시겠습니까?")) {
			return false;
		}
		var bid = $("#bid").data("bid");
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
				getAllList();
			}
		});
	});
	//좋아요
	
	function countBoardVote(bid) {
		$.get("/board_votes/cnt/" + bid, function(data) {
			$("#vote-cnt").html(data);
		});
	}
		
	$("#good-vote").click(function(e) {
		e.preventDefault();
		var bid = $("#bid").data("bid");
		
		$.get("/board_votes/" + bid, function(result) {
			if(parseInt(result) < 1) {
				$.ajax({
					type: "post",
					url: "/board_votes",
					headers: {
						"Content-Type": "application/json",
					},
					data: JSON.stringify({boardId: bid}),
					dataType: "text",
					
				}).done(function(result) {
					if(result == "SUCCESS") {
						alert("good!!");
					}
				});
			} else {
				$.ajax({
					type: "delete",
					url: "/board_votes",
					headers: {
						"Content-Type": "application/json",
					},
					data: JSON.stringify({boardId: bid}),
					dataType: "text",
					
				}).done(function(result) {
					if(result == "SUCCESS") {
						alert("delete!!");
					}
				});
			}
			countBoardVote(bid);
		});
	});

});
