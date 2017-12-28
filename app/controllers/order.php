<?php 

class Order extends Controller {

	public function order_book_form() {
		session_start();
		if (!$_POST['bookid']) {
			header('location: /bookshop-mvc/public/handler/error');
		}
		if (! @$_SESSION['memberid']) {
			header('location: /bookshop-mvc/public/member/login');
		}
		$this->view('/templetes/header', ['title'=>'ORDER']);
		$this->view('/templetes/heading', ['msg'=>'주문하기']);
		$bookModel = $this->model('BookModel');
		$memberModel = $this->model('MemberModel');
		$book = $bookModel->show_book($_POST['bookid']);
		$member = $memberModel->get_member($_SESSION['memberid']);
		$this->view('/order/order_book', ['book' => $book, 'member' => $member, 'quantity' =>$_POST['quantity']]);
		$this->view('/templetes/footer');
	}

	public function order_book() {
		session_start();
		$memberid = $_SESSION['memberid'];
		$delivery_form_data = array();
		$pay_form_data = array();
		parse_str(json_decode($_POST['delivery_form_data']), $delivery_form_data);
		parse_str(json_decode($_POST['pay_form_data']), $pay_form_data);
		
		$orderModel = $this->model('OrderModel');
		$orderModel->order_book($memberid, $delivery_form_data, $pay_form_data);
		echo "true";
	}

	public function order_compl() {
		$this->view('/templetes/header', ['title' => 'ORDER']);
		if(@ !$_SESSION['memberid']) { // 잘못된 접근일 때(로그인이 이미 돼있을 때)
			header('location: /bookshop-mvc/public/handler/error');
		}
		$this->view('/templetes/heading', ['msg' => '주문 완료']);
		$this->view('/templetes/message', ['msg' => '요청하신 주문을 완료하였습니다.']);
		$this->view('/templetes/footer');
	}
}


?>