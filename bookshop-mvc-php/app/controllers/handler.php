<?php  

class Handler extends Controller {

	public function error() {
		$this->view('/templetes/header', ['title' => 'ERROR']);
		$this->view('/templetes/heading', ['msg' => 'ERROR']);
		$this->view('/templetes/error', ['msg' => '']);
		$this->view('/templetes/footer');
	}
}

?>