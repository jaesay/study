<?php 

class MemberModel extends Model {

	function __construct() {
		parent::__construct();
	}

	public function login($form_data) {
		try {
			$memberid = $form_data['memberid'];
			$pwd = sha1($form_data['pwd']);
			$sql = "select * from members where memberid = ? and password = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $pwd, PDO::PARAM_STR, 255);
			$result = $stmt->execute();

			if(!$result) {
				throw new Exception('쿼리 실패');
			}

			if($stmt->rowCount() == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}

	public function check_id($memberid) {
		try {
			$sql = 'select * from members where memberid = ?';
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$result = $stmt->execute();

			if(!$result) {
				throw new Exception('쿼리 실패');
			}
			if($stmt->rowCount() > 0) {
				return false;
			}
			return true;

		} catch (Exception $e) {
			header('HTTP/1.0 550 My Custom Error');
		}
	}

	public function signup($form_data) {
		try {
			$pwd = sha1($form_data['pwd']);
			$sql = 'insert into members values (?,?,?,?,?,?)';
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $form_data['memberid'], PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $pwd, PDO::PARAM_STR, 255);
			$stmt->bindParam(3, $form_data['email'], PDO::PARAM_STR, 100);
			$stmt->bindParam(4, $form_data['name'], PDO::PARAM_STR, 60);
			$stmt->bindParam(5, $form_data['address'], PDO::PARAM_STR, 80);
			$stmt->bindParam(6, $form_data['phone'], PDO::PARAM_STR, 30);
			$result = $stmt->execute();
			
			if(!$result) {
				throw new Exception();
			}
			return true;
		} catch (Exception $e) {
			//echo $e->getMessage();
			return false;
		}
	}

	public function fetch_info($memberid) {
		try {
			$sql = "select memberid, email, name, address, phone from members where memberid = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$result = $stmt->execute();

			if(!$result) {
				throw new Exception('쿼리 실패');
			}

			if($stmt->rowCount() == 1) {
				return $stmt->fetch(PDO::FETCH_ASSOC);
			} else {
				return false;
			}
		} catch (Exception $e) {
			return false;
		}
	}

	public function change_member($form_data) {
		try {
			$pwd = sha1($form_data['pwd']);
			$sql = 'update members set password = ?, email = ?, name = ?, address = ?, phone = ? where memberid = ?';
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $pwd, PDO::PARAM_STR, 255);
			$stmt->bindParam(2, $form_data['email'], PDO::PARAM_STR, 100);
			$stmt->bindParam(3, $form_data['name'], PDO::PARAM_STR, 60);
			$stmt->bindParam(4, $form_data['address'], PDO::PARAM_STR, 80);
			$stmt->bindParam(5, $form_data['phone'], PDO::PARAM_STR, 30);
			$stmt->bindParam(6, $form_data['memberid'], PDO::PARAM_STR, 16);
			$result = $stmt->execute();
			if(!$result) {
				throw new Exception();
			}
			if(($stmt->rowCount()) != 1) {
				echo $stmt->rowCount();
				throw new Exception();
			}

		} catch (Exception $e) {
			//에러 페이지로
			echo 'member model error';
		}
	}

	public function remove_id($memberid) {
		try {
			$sql = 'delete from members where memberid = ?';
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$result = $stmt->execute();
			if(!$result) {
				throw new Exception();
			}
			if(($stmt->rowCount()) != 1) {
				echo $stmt->rowCount();
				throw new Exception();
			}
		} catch (Exception $e) {
			//에러 페이지로
			echo 'member model error';
		}
	}

}

?>