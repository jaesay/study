<?php 
if(@$_SESSION['memberid'] == 'admin') {
?>
<form action="/bookshop-mvc/public/admin_book/edit_book" method="post">
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
				<span><?= $data->bookid ?></span>
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
}
?>