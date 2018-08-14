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
	$(".form-delete").submit(function(event) {
		if (!confirm("정말 삭제하시겠습니까?")) {
			event.preventDefault();
		}
	});	
});