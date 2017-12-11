<?php

class Controller {

	protected function model($model)
	{
		require_once '../app/models/Model.php';
		require_once '../app/models/' . $model . '.php';
		return new $model;
	}

	protected function view($view, $data = []) {
		require_once '../app/views/' . $view . '.php';
	}

	// 컨트롤러에서도 입력 값 체크할 때
	protected function filled_out($form_vars) {
		foreach ($form_vars as $key => $value) {
			if ((!isset($key)) || ($value == '')) {
		    	return false;
		    }
		}
		return true;
	}


}

?>