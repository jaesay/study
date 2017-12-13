// 숫자만 입력
$('.numeric').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

// 로그인 폼에서 로그인 버튼을 눌렀을 때
$("#login").submit(function(event) {	
	event.preventDefault();
	var form_data = JSON.stringify($("#login").serialize());
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
				alert('아이디 또는 비밀번호가 확인하세요.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			//이후에 에러 페이지로 보내도록 수정해야 함 
			console.log('jqXHR:' + jqXHR);
			console.log('textStatus:' + textStatus);
			console.log('errorThrown:' + errorThrown);
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
		if(!is_valid_id) {
			alert("아이디가 적합하지 않습니다.");
		} else if(!is_valid_pwd) {
			alert("비밀번호가 적합하지 않습니다.");
		} else if(!is_valid_confirm) {
			alert("비밀번호가 일치하지 않습니다.");
		} else {
			alert("이메일이 적합하지 않습니다.");
		}
	}
});

// 본인 인증 버튼
$('#auth').submit(function(event) {
	if(($('#next').val() == 'modify') || ($('#next').val() == 'remove')) {
		this.action="/bookshop-mvc/public/member/auth";
	} else {
		//에러 페이지로
	}
});

/*$('#auth_btn').click(function() {
	$.ajax({
		url: "/bookshop-mvc/public/member/check_pwd",
		method: "post",
		data: {pwd: $('.auth #pwd').val()},
		success: function(data) {
			if((data == 'true') && ($('#next').val() == 'modify')) {
				window.location.replace("/bookshop-mvc/public/member/change_info"); //주소창에 url을 쳐서 오는 걸 막지 못함
			} else if((data == 'true') && ($('#next').val() == 'remove')){
				console.log('remove');
			} else {
				alert('비밀번호가 일치하지 않습니다.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			//이후에 에러 페이지로 보내도록 수정해야 함 
			console.log('jqXHR:' + jqXHR);
			console.log('textStatus:' + textStatus);
			console.log('errorThrown:' + errorThrown);
		}

	})
});*/

$('#ch_mem_btn').click(function(event) {
	//기존 입력 값에 대한 처리 때문에
	is_valid_id = true;
	var regex = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
	var is_valid = regex.exec($('.signup #email').val());
	if(is_valid) {
		is_valid_email = true;
	}

	/*if(is_valid_pwd && is_valid_confirm && is_valid_email) {
		$('#is_valid').val('true');
		console.log('bbbbbbbbb');
	} else {
		$('#is_valid').val('false');
	}
	if($('#is_valid').val() == 'false') {
		console.log('cccccccccc');
		event.preventDefault();
		if(!is_valid_pwd) {
			alert("비밀번호가 적합하지 않습니다.");
		} else if(!is_valid_confirm) {
			alert("비밀번호가 일치하지 않습니다.");
		} else {
			alert("이메일이 적합하지 않습니다.");
		}
	}*/
	console.log('aaaa');
	$('#signup').submit();
});