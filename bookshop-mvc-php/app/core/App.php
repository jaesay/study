<?php

class App {

	// domain/home/index/params[1]/params[2]/...
	// application을 bootstrap 했을 때 run되는 
	protected $controller = 'home'; // default controller

	protected $method = 'index'; // default method

	protected $params = []; // url의 parameters 

	public function __construct() {
		// (parseUrl을 사용하여)url을 parse한다.
		// parse된 url인 array를 사용하여 controller(array의 첫번 째 element)가 
		// parameters(array의 세번 째를 포함한 이후  element)와 함께 method(array의 두번 째 element)를 적절히 호출한다.
		$url = $this->parseUrl();

		// controller가 있다면 
		if (file_exists(__DIR__.'/../controllers/' . $url[0] . '.php')) {
			$this->controller = $url[0]; // instance variable인 controller의 값을 바꿈
			unset($url[0]); // 나중에 남은 배열을 parameters로 사용하려고 unset시킴
		}

		// controller file을 require시킴(없다면 위 명령문이 실행되지 않았기 때문에 default controller가 된다.)
		require_once(__DIR__.'/../controllers/' . $this->controller . '.php');

		$this->controller = new $this->controller;

		// 위와 같은 원리로 method 체크
		if(isset($url[1])) {
			if(method_exists($this->controller, $url[1])) {
				$this->method = $url[1];
				unset($url[1]);
			}
		}

		$this->params = $url ? array_values($url) : [];

		call_user_func_array([$this->controller, $this->method], $this->params);


	}

	public function parseUrl() {
		// public directory의 .htaccess로부터 rewrite된 url을 (array로) parse 한다.
		if (isset($_GET['url'])) {
			return $url = explode('/',filter_var(rtrim($_GET['url'], '/'), FILTER_SANITIZE_URL));
		}

	}
}

?>