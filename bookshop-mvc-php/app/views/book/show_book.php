<?php 
if(@$_SESSION['memberid'] == 'admin') {
?>
<form action="/bookshop-mvc/public/admin_book/edit_book" method="post">
<?php 
} else {
?>
<form action="/bookshop-mvc/public/order/order_book_form" method="post">
<?php
}
?>
	<div class="detail">
	<div class="row text-center">
		<img src="<?= $data->image ?>" alt="book img" class="img-thumbnail">
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="author">Bookid: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<input type="text" name="bookid" id="bookid" value="<?= $data->bookid ?>" readonly >	
			<?php
			} else {
			?>
				<input type="hidden" name="token" id="token" value="token">
				<input type="hidden" name="bookid" id="bookid" value="<?= $data->bookid ?>">
			<?php	
			}
			?>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="author">Author: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<input type="text" name="author" id="author" value="<?= $data->author ?>" required >	
			<?php
			} else {
			?>
				<span><?= $data->author ?></span>
			<?php	
			}
			?>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="title">Title: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<input type="text" name="title" id="title" value="<?= $data->title ?>" required >
			<?php
			} else {
			?>
				<span><?= $data->title ?></span>
			<?php	
			}
			?>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="catname">Category: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<select name="catname" required >
					<option value="programming" <?php if ($data->author == 'programming') echo selected; ?>>PROGRAMMING</option>
					<option value="database" <?php if ($data->author == 'database') echo selected; ?>>DATABASE</option>
					<option value="network" <?php if ($data->author == 'network') echo selected; ?>>NETWORK</option>
					<option value="os" <?php if ($data->author == 'os') echo selected; ?>>OS</option>
					<option value="etc" <?php if ($data->author == 'etc') echo selected; ?>>ETC</option>
				</select>
			<?php
			} else {
			?>
				<span><?= $data->catname ?></span>
			<?php	
			}
			?>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="price">Price: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<input type="text" name="price" id="price" value="<?= $data->price ?>" required >
			<?php
			} else {
			?>
				<span><?= $data->price ?></span>
			<?php	
			}
			?>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="pub_date">Publication Date: </label>
		</div>
		<div class="col-sm-8">
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<input type="date" name="pub_date" id="pub_date" value="<?= $data->pub_date ?>" required >
			<?php
			} else {
			?>
				<span><?= $data->pub_date ?></span>
			<?php	
			}
			?>
		</div>
	</div>
	<?php 
	if(@$_SESSION['memberid'] != 'admin') {
	?>
	<div class="row">
		<div class="col-sm-2 text-right">
			<label for="quantity">수량: </label>
		</div>
		<div class="col-sm-8">
			<input type="number" name="quantity" id="quantity" min="1" max="100" required >
		</div>
	</div>
	<?php	
			}
			?>
	<div class="row">
		<div class="text-center">
			<label for="description">Description: </label>
		</div>
		<div>
			<?php 
			if(@$_SESSION['memberid'] == 'admin') {
			?>
				<textarea name="description" id="description" rows="10" required ><?= $data->description ?></textarea>
			<?php
			} else {
			?>
				<span><?= $data->description ?></span>
			<?php	
			}
			?>
		</div>
	</div>
</div>
<?php 
if(@$_SESSION['memberid'] == 'admin') {
?>
<div>
	<div class="btn-group btn-group-justified col-sm-8">
		<div class="btn-group">
			<input type="submit" value="변경" class="btn btn-default">
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="remove_book">삭제</button>
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="back">취소</button>
		</div>
	</div>
</div>
</form>
<?php 
} else {
?>
<div>
	<div class="btn-group btn-group-justified col-sm-8">
		<div class="btn-group">
			<input type="submit" value="바로 구매" class="btn btn-default">
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#cartModal" id="add_to_cart_btn">장바구니에 추가</button>
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="back">취소</button>
		</div>
	</div>
	<div id="cartModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">장바구니</h4>
			</div>
			<div class="modal-body">
				<p>장바구니로 이동하시겠습니까?</p>
			</div>
			<div class="modal-footer">
				<div class="btn-group btn-group-justified col-sm-8">
					<div class="btn-group">
						<button type="button" class="btn btn-default" data-dismiss="modal" id="go_to_cart">예</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-default" data-dismiss="modal">아니오</button>
					</div>
				</div>
				
			</div>
		</div>

		</div>
	</div>
</div>
</form>
<?php
}
?>