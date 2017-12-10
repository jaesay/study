// 숫자만 입력
$('.numeric').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

// 로그인 폼에서 로그인 버튼을 눌렀을 때
$("#login").submit(function(event) {	
	event.preventDefault();
	var form_data = $("#login").serializeArray();
	$.ajax({
		url:"/bookshop-mvc/public/member/process_login",
		method: "post",
		data: { form_data: form_data},
		success: function(data) {
			if (data == 'true') {
				//if(window.location.href != document.referrer) {
				window.location.replace(document.referrer);
				//} else {
				//	window.location.replace("/bookshop-mvc/public/");	
				//}
			} else {
				alert(data);
			}
		}
	});
});

// 회원가입 폼에서 취소 버튼을 눌렀을 때
$("#cancel").click(function(event) {
	window.location.replace("/bookshop-mvc/public/");
})