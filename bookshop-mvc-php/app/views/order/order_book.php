<?php 
	$price = $data['book']->price;
	$quantity = $data['quantity'];
	$amount = $price * $quantity;
?>
<div>
	<hr>
	<p><b>주문 정보</b></p>
	<div class="table-responsive">
		<table class="table">
			<thead>
				<tr>
					<th>bookid</th>
					<th>title</th>
					<th>author</th>
					<th>category</th>
					<th>price</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><?= $data['book']->bookid ?></td>
					<td><?= $data['book']->title ?></td>
					<td><?= $data['book']->author ?></td>
					<td><?= $data['book']->catname ?></td>
					<td><?= $amount ?></td>
				</tr>
			</tbody>
		</table>
	</div>
	<hr>
	<p><b>배송 정보</b></p>
	<div>
		<form action="" method="post" id="delivery_form">
			<input type="hidden" name="bookid" id="bookid" value="<?= $data['book']->bookid ?>">
			<input type="hidden" name="price" id="price" value="<?= $price ?>">
			<input type="hidden" name="quantity" id="quantity" value="<?= $quantity ?>">
			<input type="hidden" name="amount" id="amount" value="<?= $amount ?>">
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
								<button type="button" class="btn btn-default" data-dismiss="modal" id="pay_btn">결제</button>
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
</div>