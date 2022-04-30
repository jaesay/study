<div class="table-responsive">
	<table class="table">
		<thead>
			<tr>
				<th>제목</th>
				<th>저자</th>
				<th>가격</th>
				<th>수량</th>
				<th>합계</th>
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
				<input type="hidden" value="<?= $bookid ?>" id="bookid" name="bookid">
				<td><?= $title ?></td>
				<td><?= $author ?></td>
				<td><?= $price ?></td>
				<td><?= $quantity ?></td>
				<td><?= $total ?></td>
			</tr>
		<?php
			}
		?>
			<tr>
				<td colspan="3"></td>
				<td colspan="1">총상품금액:</td>
				<td colspan="1"><?= $_SESSION['total_price'] ?></td>
			</tr>
		</tbody>
	</table><hr>
</div>
<p><b>배송 정보</b></p>
<div>
	<form action="" method="post" id="delivery_form">
		<label for="ship_name">주문자: </label>
		<input type="text" id="ship_name" name="ship_name" value="<?= $data['member']['name'] ?>">
		<label for="ship_phone">전화번호: </label>
		<input type="text" id="ship_phone" name="ship_phone" value="<?= $data['member']['phone'] ?>">
		<label for="ship_address">배송: </label>
		<input type="text" id="ship_address" name="ship_address" value="<?= $data['member']['address'] ?>">
	</form>
</div>
<hr>
<div class="btn-group btn-group-justified col-sm-8">
	<div class="btn-group">
		<button type="button" class="btn btn-default" data-toggle="modal" data-target="#payModal">결제하기</button>
	</div>
	<div class="btn-group">
		<button type="button" class="btn btn-default" id="back">취소</button>
	</div>
	<div id="payModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">결제 정보</h4>
				</div>
					<form action="" method="post" id="pay_form">
						<div class="modal-body">
						<label for="bank">은행: </label>
						<input type="text" id="bank" name="bank">
						<label for="account">계좌번호: </label>
						<input type="text" id="account" name="account">
						<label for="name">이름: </label>
						<input type="text" id="name" name="name">
					</form>
				</div>
				<div class="modal-footer">
					<div class="btn-group btn-group-justified col-sm-8">
						<div class="btn-group">
							<button type="button" class="btn btn-default" data-dismiss="modal" id="pay_for_cart_btn">결제</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
