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

	//로그인 폼에서 jquery를 통해 ajax로 호출되고 로그인을 처리함
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

	//회원 가입 폼에서 jquery를 통해 ajax로 호출되고 아이디 중복 확인
	public function check_id() {
		$memberid = $_POST['memberid'];
		$memberModel = $this->model('MemberModel');
		$result = $memberModel->check_id($memberid);
		if(!$result) {
			echo json_encode(['false', $memberid.'는 이미 사용 중인 아이디 입니다.']);
		} else {
			echo json_encode(['true', $memberid.'는 사용하실 수 있습니다.']);
		}

	}

	//회원 가입
	public function process_signup() {
		$this->view('/templetes/header', ['title' => '회원 가입']);
		if ($this->filled_out($_POST)) {
			$memberid = $_POST['memberid'];
			$pwd = sha1($_POST['pwd']);
			$confirm_pwd = $_POST['confirm_pwd'];
			$email = $_POST['email'];
			$name = $_POST['name'];
			$address = $_POST['address'];
			$phone = $_POST['phone'];
			$form_data = ['memberid'=>$memberid, 'pwd'=>$pwd, 'email'=>$email,
						  'name'=>$name, 'address'=>$address, 'phone'=>$phone];
			$memberModel = $this->model('MemberModel');

			// 새로고침같은 것으로 데이터 재삽입 방지
			if($memberModel->checkid($memberid)) {
				$result = $memberModel->signup($form_data);
				// 쿼리 실패
				if(!$result) {
					$this->view('/templetes/heading', ['msg' => '회원 가입 실패']);
					$this->view('/templetes/error', ['msg' => '회원 등록 중에 문제가 발생하였습니다.']);
				} else { // 쿼리 성공
					$this->view('/templetes/heading', ['msg' => '회원 가입 완료']);
					$this->view('/templetes/message', ['msg' => '회원 가입이 완료되였습니다.']);
				}
			} else {
				$this->view('/templetes/heading', ['msg' => 'ERROR']);
				$this->view('/templetes/error', ['msg' => '이미 처리되었습니다.']);
			}

		} else { // 서버 문제로 POST 전달 실패
			$this->view('/templetes/heading', ['msg' => '회원 가입 실패']);
			$this->view('/templetes/error', ['msg' => '회원 등록 중에 문제가 발생하였습니다.']);
		}
		$this->view('/templetes/footer');

	}


}

?>