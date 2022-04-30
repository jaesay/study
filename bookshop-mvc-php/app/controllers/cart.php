<?php 

class cart extends Controller {

	public function add_to_cart() {
		session_start();

		if(!isset($_SESSION['cart'])) {
			$_SESSION['cart'] = array();
			$_SESSION['items'] = 0;
			$_SESSION['total_price'] = 0;
		}

		$bookid = $_POST['bookid'];
		$quantity = $_POST['quantity'];

		if (isset($_SESSION['cart'][$bookid])) {
			$_SESSION['cart'][$bookid]+=$quantity;
		} else {
			$_SESSION['cart'][$bookid] = $quantity;
		}

		$_SESSION['total_price'] = $this->calculate_price($_SESSION['cart']);
		$_SESSION['items'] = $this->calculate_items($_SESSION['cart']);
		echo $_SESSION['items'];

	}

	public function show_cart() {
		$this->view('/templetes/header', ['title'=>'Cart']);
		$this->view('/templetes/heading', ['msg'=>'내 장바구니']);

		$cart_list = array();
		$bookModel = $this->model('BookModel');
		
		if (isset($_SESSION['cart'])) {
			foreach ($_SESSION['cart'] as $bookid => $quantity) {
				$book = $bookModel->show_book($bookid);
				array_push($cart_list, $book);
			}
		}

		if ($_SESSION['items'] != 0) {
			$this->view('/cart/show_cart', [ 'cart' => $_SESSION['cart'], 'cart_list' => $cart_list ]);
		} else {
			$this->view('templetes/message', ['msg' => '장바구니에 담긴 물품이 없습니다.']);
		}

		$this->view('/templetes/footer');
	}

	public function change_qty() {
		session_start();
		if(isset($_POST['bookid'])) {
			$bookid = $_POST['bookid'];

			if($_POST['quantity'] == 0) {
				unset($_SESSION['cart'][$bookid]);
			} else {
				$_SESSION['cart'][$bookid] = $_POST['quantity'];
			}
		}
		$_SESSION['total_price'] = $this->calculate_price($_SESSION['cart']);
		$_SESSION['items'] = $this->calculate_items($_SESSION['cart']);

		header('location: /bookshop-mvc/public/cart/show_cart');
	}

	private function calculate_price($cart) {
		$price = 0;
		$bookModel = $this->model('BookModel');
		if(is_array($cart)) {
			foreach ($cart as $bookid => $quantity) {
				$book = $bookModel->show_book($bookid);
				$book_price = $book->price;
				$price += $book_price * $quantity;
				
			}
		}

		return $price;
	}

	private function calculate_items($cart) {
		$items = 0;
		if(is_array($cart)) {
			foreach ($cart as $bookid => $quantity) {
				$items++;
			}
		}
		return $items;
	}

}


?>