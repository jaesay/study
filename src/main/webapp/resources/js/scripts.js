String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

// GetBoard
$(document).ready(function() {
	/*$(".form-delete").submit(function(event) {
		if (!confirm("정말 삭제하시겠습니까?")) {
			event.preventDefault();
		}
	});*/
	
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
});