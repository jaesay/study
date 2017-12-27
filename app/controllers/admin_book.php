<?php  

class Admin_book extends Controller {

	public function add_book_form() {
		session_start();
		if(@$_SESSION['memberid'] != 'admin') {
			$this->view('/templetes/header', ['title'=>'ERROR']);
			$this->view('/templetes/heading', ['msg'=>'권한 에러']);
			$this->view('/templetes/error', ['msg' => '관리자만 볼 수 있는 페이지입니다.']);
			$this->view('/templetes/footer');
			exit;
		}
		$this->view('/templetes/header', ['title'=>'BOOK']);
		$this->view('/templetes/heading', ['msg'=>'관리자 BOOK 추가']);
		$this->view('/admin_book/admin_add_book');
		$this->view('/templetes/footer');
	}

	public function add_book() {
		session_start();
		if(@$_SESSION['memberid'] != 'admin') {
			$this->view('/templetes/header', ['title'=>'ERROR']);
			$this->view('/templetes/heading', ['msg'=>'권한 에러']);
			$this->view('/templetes/error', ['msg' => '관리자만 볼 수 있는 페이지입니다.']);
			$this->view('/templetes/footer');
			exit;
		}
		if(@$_SESSION['memberid'] != 'admin') {

		}
		if($_FILES['image']['error'] > 0 && $_FILES['image']['error'] !=4) {
			echo 'error';
		}
		if(!($_FILES['image']['type'] == 'image/png' || $_FILES['image']['type'] == 'image/gif' ||
		   $_FILES['image']['type'] == 'image/jpeg' || $_FILES['image']['type'] == 'image/jpg' ||
		   $_FILES['image']['type'] == 'image/bmp' || $_FILES['image']['type'] == 'image/webp')) {
			echo 'not image';
		}
		if(!file_exists(__DIR__.'/../../public/inc/uploads/')) {
			$oldumask = umask(0);
			mkdir(__DIR__.'/../../public/inc/uploads/', 0770);
			umask($oldumask);
		}


		$uploaded_file = __DIR__.'/../../public/inc/uploads/'.time().'-'.$_FILES['image']['name'];
		$db_path = str_replace(__DIR__ . '/', '', $uploaded_file);

		if($_FILES['image']['error'] == 0) {
			if(is_uploaded_file($_FILES['image']['tmp_name'])) {
				if(!move_uploaded_file($_FILES['image']['tmp_name'], $uploaded_file)) {
					echo 'move error';
				}
			} else {
				echo 'move error2';
			}
		}

		$AdminBookModel = $this->model('AdminBookModel');
		$AdminBookModel->add_book($_POST, $db_path);

		$this->view('/templetes/header', ['title'=>'BOOK 추가']);
		$this->view('/templetes/heading', ['msg'=>'관리자-BOOK 추가 완료']);
		$this->view('/templetes/message', ['msg'=>$_POST['title'].' BOOK 추가가 완료되었습니다.']);
		$this->view('/templetes/footer');
	}

	public function edit_book() {
		session_start();
		if(@$_SESSION['memberid'] != 'admin') {
			$this->view('/templetes/header', ['title'=>'ERROR']);
			$this->view('/templetes/heading', ['msg'=>'권한 에러']);
			$this->view('/templetes/error', ['msg' => '관리자만 볼 수 있는 페이지입니다.']);
			$this->view('/templetes/footer');
			exit;
		}
		$this->view('/templetes/header', ['title'=>'BOOK']);
		$this->view('/templetes/heading', ['msg'=>'BOOK 변경']);
		$AdminBookModel = $this->model('AdminBookModel');
		$AdminBookModel->edit_book($_POST);
		$this->view('/templetes/heading', ['msg'=>'관리자-BOOK 변경 완료']);
		$this->view('/templetes/message', ['msg'=>'변경가 완료되었습니다.']);
		$this->view('/templetes/footer');
	}

	public function remove_book($bookid) {
		session_start();
		if(@$_SESSION['memberid'] != 'admin') {
			$this->view('/templetes/header', ['title'=>'ERROR']);
			$this->view('/templetes/heading', ['msg'=>'권한 에러']);
			$this->view('/templetes/error', ['msg' => '관리자만 볼 수 있는 페이지입니다.']);
			$this->view('/templetes/footer');
			exit;
		}
		if (!$bookid) {
			header('location: /bookshop-mvc/public/handler/error');
		}
		$this->view('/templetes/header', ['title'=>'BOOK']);
		$this->view('/templetes/heading', ['msg'=>'BOOK 삭제']);
		$AdminBookModel = $this->model('AdminBookModel');
		$AdminBookModel->remove_book($bookid);
		$this->view('/templetes/heading', ['msg'=>'관리자-BOOK 삭제 완료']);
		$this->view('/templetes/message', ['msg'=>'삭제가 완료되었습니다.']);
		$this->view('/templetes/footer');
	}
}

?>