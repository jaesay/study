<?php 

class MemberModel extends Model {

	function __construct() {
		parent::__construct();
	}

	public function login($form_data) {
		try {
			$memberid = $form_data[0]['value'];
			$pwd = sha1($form_data[1]['value']);
			$sql = "select * from members where memberid = ? and password = ?";
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $memberid, PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $pwd, PDO::PARAM_STR, 255);
			$result = $stmt->execute();

			if(!$result) {
				throw new Exception('쿼리 실패');
			}

			if($stmt->rowCount() == 1) {
				echo 'true';
			} else {
				throw new Exception('아이디와 비밀번호를 확인해주세요.');
			}

		} catch (Exception $e) {
			echo $e->getMessage();
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
			$sql = 'insert into members values (?,?,?,?,?,?)';
			$stmt = $this->dbh->prepare($sql);
			$stmt->bindParam(1, $form_data['memberid'], PDO::PARAM_STR, 16);
			$stmt->bindParam(2, $form_data['pwd'], PDO::PARAM_STR, 255);
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

}

?>