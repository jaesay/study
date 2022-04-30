<?php 

class BookModel extends Model {

	function __construct() {
		parent::__construct();
	}
	
	public function list_books($catname = "") {
		try {
			if($catname) {
				$sql = "select * from books where catname = ?";
				$upper_catname = strtoupper($catname);
				$stmt = $this->dbh->prepare($sql);
				$stmt->bindParam(1, $upper_catname, PDO::PARAM_STR, 60);
			} else {
				$sql = "select * from books";
				$stmt = $this->dbh->prepare($sql);
			}
			
			if(!$stmt->execute()) {
				throw new Exception();
			}
			$result = $stmt->fetchAll();
			return $result;
			
		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}

	}

	public function show_book($bookid) {
		try {

			$sql = "select * from books where bookid = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $bookid, PDO::PARAM_INT);
			
			if(!$stmt->execute()) {
				throw new Exception();
			}
			$result = $stmt->fetchObject();
			return $result;
			
		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}
}
?>