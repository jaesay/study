<?php 

class Book extends Controller {

	public function list_books($catname = '') {
		$this->view('/templetes/header', ['title'=>'BOOK']);
		$this->view('/templetes/heading', ['msg'=>'BOOK 목록']);
		$BookModel = $this->model('BookModel');
		$result = $BookModel->list_books($catname);
		$this->view('/book/list_books', $result);
		$this->view('/templetes/footer');
	}

	public function show_book($bookid = '') {
		if (!$bookid) {
			header('location: /bookshop-mvc/public/handler/error');
		}
		$this->view('/templetes/header', ['title'=>'BOOK']);
		$this->view('/templetes/heading', ['msg'=>'BOOK 세부사항']);
		$BookModel = $this->model('BookModel');
		$result = $BookModel->show_book($bookid);
		$this->view('/book/show_book', $result);
		$this->view('/templetes/footer');
	}

}


?>