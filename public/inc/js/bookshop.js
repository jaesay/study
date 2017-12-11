// 숫자만 입력
$('.numeric').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

// 로그인 폼에서 로그인 버튼을 눌렀을 때
$("#login").submit(function(event) {	
	event.preventDefault();
	var form_data = $("#login").serializeArray();
	console.log(form_data);
	console.log(JSON.stringify(form_data));
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
});

var is_valid_id = false;
var is_valid_pwd = false;
var is_valid_confirm = false;
var is_valid_email =false;

//회원가입 폼에서 아이디 중복 확인
$(".signup #memberid").focusout(function () {
	/*$.ajax({
		url:"/bookshop-mvc/public/member/check_id",
		method: "post",
		data: {memberid : $(".signup #memberid").val()},
		success: function(data) {
			if (data[0] == 'true') {
				$('#correct_id').remove();
				$('.signup label[for="memberid"]')
				.after('&nbsp;&nbsp;<span style="color:blue;" id="correct_id">' + data[1] + '</span>');
				is_valid_id = true;
			} else {
				$('#correct_id').remove();
				$('.signup label[for="memberid"]')
				.after('&nbsp;&nbsp;<span style="color:red;" id="correct_id">' + data[1] + '</span>');
				is_valid_id = false;
			}
		},
		dataType:"json"
	})*/
	if((this.value.length < 6) || (this.value.length > 16)) {
		$('#correct_id').remove();
		$('.signup label[for="memberid"]')
		.after('&nbsp;&nbsp;<span style="color:blue;" id="correct_id">아이디는 6자리 이상 16자리 이하여야 합니다.</span>');
	} else {
		$.ajax({
			url:"/bookshop-mvc/public/member/check_id",
			method: "post",
			data: {memberid : $(".signup #memberid").val()},
			success: function(data) {
				if (data[0] == 'true') {
					$('#correct_id').remove();
					$('.signup label[for="memberid"]')
					.after('&nbsp;&nbsp;<span style="color:blue;" id="correct_id">' + data[1] + '</span>');
					is_valid_id = true;
				} else {
					$('#correct_id').remove();
					$('.signup label[for="memberid"]')
					.after('&nbsp;&nbsp;<span style="color:red;" id="correct_id">' + data[1] + '</span>');
					is_valid_id = false;
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//이후에 에러 페이지로 보내도록 수정해야 함 
				console.log('jqXHR:' + jqXHR);
				console.log('textStatus:' + textStatus);
				console.log('errorThrown:' + errorThrown);
			},
			dataType:"json"
		});
	}
});

// 비밀번호 유효성 체크(6자리 이상 16자리 이하)
$('.signup #pwd').focusout(function() {
	if((this.value.length<6) || (this.value.length>16)) {
		$('#valid_pwd').remove();
		$('.signup label[for="pwd"]')
		.after('&nbsp;&nbsp;<span style="color:red;" id="valid_pwd">비밀번호는 6자리 이상 16자리 이하여야 합니다.</span>');
		is_valid_pwd = false;
	} else {
		$('#valid_pwd').remove();
		is_valid_pwd = true;
	}
});

// 회원가입 폼에서 비밀번호 중복 확인
$(".signup #confirm_pwd").focusout(function() {
	if($('.signup #pwd').val() == this.value) {
		$('#match_pwd').remove();
		$('.signup label[for="confirm_pwd"]')
		.after('&nbsp;&nbsp;<span style="color:blue;" id="match_pwd">비밀번호가 일치합니다.</span>');
		is_valid_confirm = true;
	} else {
		$('#match_pwd').remove();
		$('.signup label[for="confirm_pwd"]')
		.after('&nbsp;&nbsp;<span style="color:red;" id="match_pwd">비밀번호가 일치하지 않습니다.</span>');
		is_valid_confirm = false;
	}
});

// 이메일 유효성 확인
$('.signup #email').focusout(function() {
	var regex = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
	var is_valid = regex.exec(this.value);
	if(!is_valid) {
		$('#valid_email').remove();
		$('.signup label[for="email"]')
		.after('&nbsp;&nbsp;<span style="color:red;" id="valid_email">이메일 주소가 유효하지 않습니다.</span>');
		is_valid_email = false;
	} else {
		$('#valid_email').remove();
		is_valid_email = true;
	}
});

// 회원가입 입력
$('#signup').submit(function(event) {
	//event.preventDefault();
	if(is_valid_id && is_valid_pwd && is_valid_confirm && is_valid_email) {
		$('#is_valid').val('true');
	} else {
		$('#is_valid').val('false');
	}
	if($('#is_valid').val() == 'false') {
		event.preventDefault();
	}
});
