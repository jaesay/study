<?php 

function display_book_form($book='') {
	$edit = is_array($book);
?>
	<form action="<?php echo $edit ? 'edit_book.php' : 'insert_book.php' ?>" method="post">
		<table border="0">
		<tr>
			<td>ISBN:</td>
			<td><input type="text" name="isbn"
			value="<?php echo htmlspecialchars($edit ? $book['isbn'] : ''); ?>"></td>
		</tr>
		<tr>
			<td>Book Title:</td>
			<td><input type="text" name="title"
			value="<?php echo htmlspecialchars($edit ? $book['title'] : ''); ?>"></td>
		</tr>
		<tr>
			<td>Book Author:</td>
			<td><input type="text" name="author"
			value="<?php echo htmlspecialchars($edit ? $book['author'] : ''); ?>"></td>
		</tr>
		<tr>
			<td>Category:</td>
			<td><select name="catid">
				<?php 
					$cat_array=get_categories();
					foreach($cat_array as $thiscat) {
						echo "<option value=\"".htmlspecialchars($thiscat['catid'])."\"";
						if (($edit) && ($thiscat['catid'] == $book['catid'])) {
							echo " selected";
						}
						echo ">".htmlspecialchars($thiscat['catname'])."</option>";
					}
				?>
			</select></td>
		</tr>
		<tr>
			<td>Price:</td>
			<td><input type="text" name="price"
			value="<?php echo htmlspecialchars($edit ? $book['price'] : ''); ?>"></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td>
			<textarea rows="3" cols="50" name="description">
				<?php echo htmlspecialchars($edit ? $book['description'] : ''); ?>								
			</textarea>	
			</td>
		</tr>
		<tr>
			<td <?php if (!$edit) { echo "colspan=2"; } ?> align="center">
			<?php
				if($edit) 
					echo "<input type=\"hidden\" name=\"oldisbn\" value=\"".htmlspecialchars($book['isbn'])."\" />";
			?>
			<input type="submit" value="<?php echo $edit ? 'Update' : 'Add'; ?> Book">
				
			</form></td>
			<?php
				if ($edit) {
					echo "<td>
						  <form method=\"post\" action=\"delete_book.php\">
						  <input type=\"hidden\" name=\"isbn\"
						  value=\"".htmlspecialchars($book['isbn'])."\" />
						  <input type=\"submit\" value=\"Delete Book\" /></form></td>";
				}
			?>
		</tr>
	</table>
<?php
}

function insert_book($isbn, $title, $author, $catid, $price, $description) {
	$conn = db_connect();
	$query = "select *
			  from books
			  where isbn='".$conn->real_escape_string($isbn)."'";

	$result = $conn->query($query);
	if((!$result) || ($result->num_rows!=0)) {
		return false;
	}

	$query = "insert into books values ('".
			 $conn->real_escape_string($isbn)."', '".
			 $conn->real_escape_string($author)."', '".
			 $conn->real_escape_string($title)."', '".
			 $conn->real_escape_string($catid)."', '".
			 $conn->real_escape_string($price)."', '".
			 $conn->real_escape_string($description)."')";

	$result = $conn->query($query);
	if(!$result) {
		return false;
	} else {
		return true;
	}
}

function update_book($oldisbn, $isbn, $title, $author, $catid, $price, $description) {
	$conn = db_connect();

	$query = "update books
			  set isbn='".$conn->real_escape_string($isbn)."',
			  title = '".$conn->real_escape_string($title)."',
			  author = '".$conn->real_escape_string($author)."',
			  catid = '".$conn->real_escape_string($catid)."',
			  price = '".$conn->real_escape_string($price)."',
			  description = '".$conn->real_escape_string($description)."'
			  where isbn = '".$conn->real_escape_string($oldisbn)."'";

	$result = @$conn->query($query);
	if(!$result) {
		echo $conn->error;
		return false;
	} else {
		return true;
	}
}

function delete_book($isbn) {
	$conn = db_connect();

	$query = "delete from books
			  where isbn='".$conn->real_escape_string($isbn)."'";

	$result = @$conn->query($query);
	if(!$result) {
		return false;
	} else {
		return true;
	}
}

?>