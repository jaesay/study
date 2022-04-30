<?php 

class Home extends Controller {

	public function index() {
		//$user = $this->model('User');
		//$user->name = $name;
		$this->view('/templetes/header', ['title' => 'BOOKSHOP']);
		$this->view('/templetes/heading', ['msg' => 'HOME']);
		//$this->view('/home/index', ['name' => $user->name]); // view의 directory path
		$this->view('/home/index');
		//print_r($_SESSION);

		$this->view('/templetes/footer');
	}

}


?>