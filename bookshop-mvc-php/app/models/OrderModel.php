<?php 

class OrderModel extends Model {

	function __construct() {
		parent::__construct();
	}

	function order_book($memberid, $delivery_form_data, $pay_form_data) {
		try {
			extract($delivery_form_data);
			extract($pay_form_data);
			$date = date("Y-m-d");

			$this->dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	  		$this->dbh->beginTransaction();

	  		$sql = "insert into orders values (NULL, ?, ?, ?, '배송 준비 중', ?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $amount, PDO::PARAM_INT);
			$stmt->bindParam(3, $date, PDO::PARAM_STR);
			$stmt->bindParam(4, $ship_name, PDO::PARAM_STR, 60);
			$stmt->bindParam(5, $ship_address, PDO::PARAM_STR, 80);
			$stmt->bindParam(6, $ship_phone, PDO::PARAM_STR, 30);
			$result = $stmt->execute();

			if(!$result) {
				//print_r($this->dbh->errorInfo());
				throw new Exception('fail');
			}
			if($stmt->rowCount() != 1) {
				//print_r($this->dbh->errorInfo());
				throw new Exception();
			}

			$orderid = $this->dbh->lastInsertId();
			$sql = "insert into order_items values (?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $orderid, PDO::PARAM_INT);
			$stmt->bindParam(2, $bookid, PDO::PARAM_INT);
			$stmt->bindParam(3, $quantity, PDO::PARAM_INT);
			$result = $stmt->execute();

			if(!$result) {
				//print_r($this->dbh->errorInfo());
				throw new Exception('쿼리 실패');
			}
			if($stmt->rowCount() != 1) {
				//print_r($this->dbh->errorInfo());
				throw new Exception();
			}

			$sql = "insert into accounts values (?, ?, ?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $orderid, PDO::PARAM_INT);
			$stmt->bindParam(2, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(3, $bank, PDO::PARAM_STR, 50);
			$stmt->bindParam(4, $account, PDO::PARAM_STR, 100);
			$stmt->bindParam(5, $name, PDO::PARAM_STR, 60);
			$result = $stmt->execute();

			if(!$result) {
				//print_r($this->dbh->errorInfo());
				throw new Exception('쿼리 실패');
			}
			if($stmt->rowCount() != 1) {
				//print_r($this->dbh->errorInfo());
				throw new Exception();
			}

			$this->dbh->commit();

		} catch (Exception $e) {
			$this->dbh->rollBack();
			header('HTTP/1.0 550 My Custom Error');
		}
		
	}

	function order_cart($memberid, $delivery_form_data, $pay_form_data) {
		try {
			extract($delivery_form_data);
			extract($pay_form_data);
			$date = date("Y-m-d");

			$this->dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	  		$this->dbh->beginTransaction();

	  		$sql = "insert into orders values (NULL, ?, ?, ?, '배송 준비 중', ?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $amount, PDO::PARAM_INT);
			$stmt->bindParam(3, $date, PDO::PARAM_STR);
			$stmt->bindParam(4, $ship_name, PDO::PARAM_STR, 60);
			$stmt->bindParam(5, $ship_address, PDO::PARAM_STR, 80);
			$stmt->bindParam(6, $ship_phone, PDO::PARAM_STR, 30);
			$result = $stmt->execute();

			if(!$result) {
				//print_r($this->dbh->errorInfo());
				throw new Exception('fail');
			}
			if($stmt->rowCount() != 1) {
				//print_r($this->dbh->errorInfo());
				throw new Exception();
			}

			$orderid = $this->dbh->lastInsertId();
			foreach ($_SESSION['cart'] as $bookid => $quantity) {
				$sql = "insert into order_items values (?, ?, 111, ?, now(), now())";
				$stmt = $this->dbh->prepare($sql);
				$stmt->bindParam(1, $orderid, PDO::PARAM_INT);
				$stmt->bindParam(2, $bookid, PDO::PARAM_INT);
				$stmt->bindParam(3, $quantity, PDO::PARAM_INT);
				$result = $stmt->execute();
				
				if(!$result) {
					//print_r($this->dbh->errorInfo());
					throw new Exception('쿼리 실패');
				}
				if($stmt->rowCount() != 1) {
					//print_r($this->dbh->errorInfo());
					throw new Exception();
				}
			}


			$sql = "insert into accounts values (?, ?, ?, ?, ?, now(), now())";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $orderid, PDO::PARAM_INT);
			$stmt->bindParam(2, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(3, $bank, PDO::PARAM_STR, 50);
			$stmt->bindParam(4, $account, PDO::PARAM_STR, 100);
			$stmt->bindParam(5, $name, PDO::PARAM_STR, 60);
			$result = $stmt->execute();

			if(!$result) {
				//print_r($this->dbh->errorInfo());
				throw new Exception('쿼리 실패');
			}
			if($stmt->rowCount() != 1) {
				//print_r($this->dbh->errorInfo());
				throw new Exception();
			}

			$this->dbh->commit();

		} catch (Exception $e) {
			$this->dbh->rollBack();
			header('HTTP/1.0 550 My Custom Error');
		}
		
	}
	
	
}
?>