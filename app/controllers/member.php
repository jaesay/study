<?php 
/******************************************************************/
/* 회원과 관련된 일을 처리하는 컨트롤러 							  */
/******************************************************************/
class Member extends Controller {

	//로그인 폼을 보여줌
	public function login() {
		$this->view('/templetes/header', ['title' => 'LOGIN']);
		if(@$_SESSION['memberid']) {
			header('location: /bookshop-mvc/public/');
		}
		$this->view('/templetes/heading', ['msg' => '로그인']);
		$this->view('/member/login');
		$this->view('/templetes/footer');
	}

	//jquery를 통해 ajax로 호출되고 로그인을 처리함
	public function process_login() {
		session_start();
		$form_data = $_POST['form_data'];
		$memberModel = $this->model('MemberModel');
		$memberModel->login($form_data);
		$_SESSION['memberid'] = $form_data[0]['value'];
	}

	//logout을 처리함
	public function logout() {
		session_start();
		session_destroy();
		header('location: /bookshop-mvc/public/');
	}

	//회원 가입 폼을 보여줌
	public function signup() {
		$this->view('/templetes/header', ['title' => 'REGISTER']);
		if(@$_SESSION['memberid']) {
			header('location: /bookshop-mvc/public/');
		}
		$this->view('/templetes/heading', ['msg' => '회원 가입']);
		$this->view('/member/signup');
		$this->view('/templetes/footer');
	}

	public function modify_member() {
		
	}

}

?>