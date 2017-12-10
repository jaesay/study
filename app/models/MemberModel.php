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
}

?>