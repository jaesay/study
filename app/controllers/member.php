<?php 
/******************************************************************/
/* 회원과 관련된 일을 처리하는 컨트롤러 							  */
/******************************************************************/
class Member extends Controller {

	//로그인 폼을 보여줌
	public function login() {
		$this->view('/templetes/header', ['title' => 'LOGIN']);
		if(@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 이미 돼있을 때)
			header('location: /bookshop-mvc/public/handler/error');
		}
		$this->view('/templetes/heading', ['msg' => '로그인']);
		$this->view('/member/login');
		$this->view('/templetes/footer');
	}

	//로그인 폼에서 jquery를 통해 ajax로 호출되고 로그인을 처리함
	public function process_login() {
		session_start();
		$form_data = array();
		parse_str(json_decode($_POST['form_data']), $form_data);
		$memberModel = $this->model('MemberModel');
		if($memberModel->login($form_data)) {
			$_SESSION['memberid'] = $form_data['memberid'];
			echo 'true';
		} else {
			echo 'false';
		}
		$memberModel->login($form_data);

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
		if(@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 이미 돼있을 때)
			header('location: /bookshop-mvc/public/handler/error');
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
		if(@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 이미 돼있을 때)
			header('location: /bookshop-mvc/public/handler/error');
		}
		if ($this->filled_out($_POST)) {
			$memberModel = $this->model('MemberModel');

			// 새로고침같은 것으로 데이터 재삽입 방지
			if($memberModel->check_id($_POST['memberid'])) {
				$result = $memberModel->signup($_POST);
				// 쿼리 실패
				if(!$result) {
					$this->view('/templetes/heading', ['msg' => '회원 가입 실패']);
					$this->view('/templetes/error', ['msg' => '회원 등록 중에 문제가 발생하였습니다.']);
				} else { // 쿼리 성공
					$this->view('/templetes/heading', ['msg' => '회원 가입 완료']);
					$this->view('/templetes/message', ['msg' => '회원 가입이 완료되었습니다.']);
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

	// 회원 정보 변경, 아이디 삭제를 할 때 본인 인증
	public function auth_form($params = '') {
		$this->view('templetes/header', ['title' => '본인 인증']);
		if(!@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 안됐을 때)
			header('location: /bookshop-mvc/public/handler/error');
		}
		if($params == 'modify' || $params =='remove') {
			$this->view('templetes/heading', ['msg' => '본인 인증']);
			$this->view('member/auth', ['next' => $params] );
		} else {
			$this->view('/templetes/heading', ['msg' => 'ERROR']);
			$this->view('/templetes/error', ['msg' => '잘못된 접근입니다.']);
		}
		$this->view('templetes/footer');
	}

	// 본인 인증시 적절한 폼을 보여줌
	public function auth() {
		session_start();
		if(!@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 안됐을 때)
			header('location: /bookshop-mvc/public/handler/error');
		}
		// 본인 인증시 비밀번호 일치 여부 확인(meberModel의 login method 재사용)
		$memberModel = $this->model('MemberModel');
		$result = $memberModel->login(['memberid'=>$_SESSION['memberid'], 'pwd'=>$_POST['pwd']]);
		if($result) { //비밀번호가 일치했을 때
			if($_POST['next'] == 'modify') {
				$this->view('templetes/header', ['title' => '개인 정보 변경']);
				$this->view('/templetes/heading', ['msg' => '개인 정보 변경']);
				$arr_result = $memberModel->get_member($_SESSION['memberid']);
				$this->view('/member/change_member', $arr_result);
			} else if($_POST['next'] == 'remove') {
				$memberModel->remove_id($_SESSION['memberid']);
				session_destroy();
				$this->view('templetes/header', ['title' => '삭제 완료']);
				$this->view('/templetes/heading', ['msg' => '아이디 삭제']);
				$this->view('templetes/message', ['msg' => '지금까지 이용해주셔서 감사합니다.']);
			} else {
				$this->view('templetes/header', ['title' => '에러']);
				$this->view('/templetes/heading', ['msg' => 'ERROR']);
				$this->view('/templetes/error', ['msg' => '잘못된 접근입니다.']);
			}
			$this->view('templetes/footer');
		} else { //비밀번호가 일치하지 않았을 때
			echo("<script>alert('비밀번호가 일치하지 않습니다.');
				window.location = '/bookshop-mvc/public/member/auth_form/".$_POST['next']."';</script>");
			//header('location: /bookshop-mvc/public/member/auth_form/'.$_POST['next']);
		}
	}

	public function change_member() {
		$this->view('templetes/header', ['title' => '변경 완료']);
		if(!@$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 안됐을 때)
			//에러페이지로
			header('location: /bookshop-mvc/public/handler/error');
		}
		if ($this->filled_out($_POST)) {
			$memberModel = $this->model('MemberModel');
			$memberModel->change_member($_POST);
			$this->view('templetes/heading', ['msg' => '개인 정보 변경 완료']);
			$this->view('templetes/message', ['msg' => '변경이 완료되었습니다.']);
			$this->view('templetes/footer');
		} else {
			//error 페이지로
			header('location: /bookshop-mvc/public/handler/error');
		}
	}


}

?>