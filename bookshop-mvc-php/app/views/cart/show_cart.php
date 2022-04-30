<div class="table-responsive">
	<table class="table">
		<thead>
			<tr>
				<th>제목</th>
				<th>저자</th>
				<th>가격</th>
				<th>수량</th>
				<th>합계</th>
				<th>선택</th>
			</tr>
		</thead>
		<tbody>
		<?php  
			for($i=0; $i<count($data['cart_list']); $i++) {
				$bookid = $data['cart_list'][$i]->bookid;
				$title = $data['cart_list'][$i]->title;
				$author = $data['cart_list'][$i]->author;
				$price = $data['cart_list'][$i]->price;
				$quantity = $data['cart'][$bookid];
				$total = $price * $quantity;
		?>
			<tr>
				<form action="/bookshop-mvc/public/cart/change_qty" method="post">
					<input type="hidden" value="<?= $bookid ?>" id="bookid" name="bookid">
					<td><?= $title ?></td>
					<td><?= $author ?></td>
					<td><?= $price ?></td>
					<td>
						<input type="number" id="quantity" name="quantity" value="<?= $quantity ?>">
					</td>
					<td><?= $total ?></td>
					<td><input type="submit" class="btn btn-default" value="변경" id="change_qty" name="change_qty"></td>
				</form>
			</tr>
		<?php
			}
		?>
			<tr>
				<td colspan="4"></td>
				<td colspan="1">총상품금액:</td>
				<td colspan="1"><?= $_SESSION['total_price'] ?></td>
			</tr>
		</tbody>
	</table><hr>
	<div class="btn-group btn-group-justified col-sm-8">
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="order_cart_btn">장바구니 주문하기</button>
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="con_shop_btn">쇼핑 계속하기</button>
		</div>
	</div>
</div>