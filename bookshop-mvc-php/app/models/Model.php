<?php  

class Model {

	protected $dbh;

	protected function __construct() {
		try {
			$this->dbh = new PDO('mysql:host=localhost;dbname=bookshop', 'bookshop', 'password');
		} catch (Exception $e) {
			echo $e->getMessage();
			exit;
		}
	}

}

?>