<?php  

class AdminBookModel extends Model {

	function __construct() {
		parent::__construct();
	}

	public function add_book($form_data, $image) {
		try {
			$sql = "insert into books values (NULL, ?, ?, ?, ?, ?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $image, PDO::PARAM_STR, 100);
			$stmt->bindParam(2, $form_data['author'], PDO::PARAM_STR, 80);
			$stmt->bindParam(3, $form_data['title'], PDO::PARAM_STR, 100);
			$stmt->bindParam(4, $form_data['catname'], PDO::PARAM_STR, 60);
			$stmt->bindParam(5, $form_data['price'], PDO::PARAM_INT);
			$stmt->bindParam(6, $form_data['pub_date'], PDO::PARAM_STR);
			$stmt->bindParam(7, $form_data['description'], PDO::PARAM_STR, 255);

			$result = $stmt->execute();
			
			if(!$result) {
				throw new Exception();
			}
			if($stmt->rowCount() != 1) {
				throw new Exception();
			}

		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}

	public function edit_book($form_data) {
		try {
			$sql = "update books set author = ?, title = ?, catname = ?, 
					price = ?, pub_date = ?, description = ?, updated_at = now() where bookid = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $form_data['author'], PDO::PARAM_STR, 80);
			$stmt->bindParam(2, $form_data['title'], PDO::PARAM_STR, 100);
			$stmt->bindParam(3, $form_data['catname'], PDO::PARAM_STR, 60);
			$stmt->bindParam(4, $form_data['price'], PDO::PARAM_INT);
			$stmt->bindParam(5, $form_data['pub_date'], PDO::PARAM_STR);
			$stmt->bindParam(6, $form_data['description'], PDO::PARAM_STR, 255);
			$stmt->bindParam(7, $form_data['bookid'], PDO::PARAM_INT);

			$result = $stmt->execute();
			
			if(!$result) {
				throw new Exception();
			}
			if($stmt->rowCount() != 1) {
				throw new Exception();
			}

		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}


	public function remove_book($bookid) {
		try {
			$sql = "delete from books where bookid = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $bookid, PDO::PARAM_INT);
			$result = $stmt->execute();
			
			if(!$result) {
				throw new Exception();
			}
			if($stmt->rowCount() != 1) {
				throw new Exception();
			}

		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}

}


?>