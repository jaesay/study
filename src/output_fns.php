<?php

function do_html_header($title = '') {
	if(empty($_SESSION['items'])) {
		$_SESSION['items'] = 0;
	}
	if(empty($_SESSION['total_price'])) {
		$_SESSION['total_price'] = '0.00';
	}
?>
	<!DOCTYPE html>
	<head>
		<meta charset="UTF-8">
		<title><?php echo htmlspecialchars($title)?></title>
		<style>
			h2 { font-family: Arial, Helvetica, sans-serif; font-size: 22px; color: red; margin: 6px; }
			body { font-family: Arial, Helvetica, sans-serif; font-size: 13px; }
			li, td { font-family: Arial, Helvetica, sans-serif; font-size: 13px; }
			hr { color: #FF0000; width: 70%; text-align: center; }
			a {color: #000000;}
		</style>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" bgcolor="#cccccc">
		<tr>
		<td rowspan="2">
		<a href="index.php"><img src="inc/img/Book-O-Rama.gif" alt="Bookorama" border="0" align="left" valign="bottom" height="55" width="325"></a>
		</td>
		<td align="right" valign="bottom">
			<?php
				if(isset($_SESSION['admin_user'])) {
					echo "&nbsp;";
				} else {
					echo "Total Items = " . htmlspecialchars($_SESSION['items']);
				}
			?>
		</td>
		<td align="right" rowspan="2" width="135">
		<?php
			if(isset($_SESSION['admin_user'])) {
				display_button('logout.php', 'log-out', 'Log Out');
			} else {
				display_button('show_cart.php', 'view-cart', 'View Your Shopping Cart');
			}
		?>
		</tr>
		<tr>
		<td align="right" valign="top">
		<?php
			if(isset($_SESSION['admin_user'])) {
				echo "&nbsp;";
			} else {
				echo "Total Price = $".number_format($_SESSION['total_price'], 2);
			}
		?>
		</td>
		</tr>
		</table>
	</body>
	</html>
<?php
}

function display_button($target, $image, $alt) {
	echo "<div align=\"center\"><a href=\"".htmlspecialchars($target)."\">
	<img src=\"inc/img/".htmlspecialchars($image).".gif\" alt=\"".htmlspecialchars($alt)."\" border=\"0\" height=\"50\" width=\"135\"/>
	</a></div>";
}

function do_html_footer() {
?>
	</body>
	</html>
<?php
}

function display_categories($cat_array) {
	if(!is_array($cat_array)) {
		echo "<p>No categories currently available</p>";
		return;
	}
	echo "<ul>";
	foreach($cat_array as $row) {
		$url = "show_cat.php?catid=".urlencode($row['catid']);
		$title = $row['catname'];
		echo "<li>";
		do_html_url($url, $title);
		echo "</li>";
	}
	echo "</ul>";
	echo "<hr /> ";
}

function do_html_url($url, $name) {
?>
	<a href="<?php echo htmlspecialchars($url); ?>"><?php echo $name; ?></a>

<?php	
}

function display_books($book_array) {
	if(!is_array($book_array)) {
		echo "<p>No books currently available in this category</p>";
	} else {
		echo "<table width=\"100%\" border=\"0\">";

		foreach($book_array as $row) {
			$url = "show_book.php?isbn=".urlencode($row['isbn']);
			echo "<tr><td>";
			if(@file_exists("inc/img/{$row['isbn']}.jpg")) {
				$title = "<img src=\"inc/img/".htmlspecialchars($row['isbn']).".jpg\" style=\"border: 1px solid black \"/>";
				do_html_url($url, $title);
			} else {
				echo "&nbsp;";
			}
			echo "</td><td>";
			$title = htmlspecialchars($row['title'])." by ".htmlspecialchars($row['author']);
			do_html_url($url, $title);
			echo "</td></tr>";
		} 
		echo "</table>";
	}
	echo "<hr />";
}

function display_book_details($book) {
	if(is_array($book)) {
		echo "<table><tr>";
		if(@file_exists("inc/img/{$book['isbn']}.jpg")) {
			$size = GetImageSize("inc/img/{$book['isbn']}.jpg");
			echo '<br /><!-- size[0], size[1]'.$size[0].", ".$size[1].' --><br />';
			if(($size[0]>0) && ($size[1]>0)) {
				echo "<td><img src=\"inc/img/".htmlspecialchars($book['isbn']).".jpg\" style=\"border: 1px solid black\"/></td>";
			}
		}
		echo "<td><ul>";
		echo "<li><strong>Author:</strong> ";
		echo htmlspecialchars($book['author']);
		echo "</li><li><strong>ISBN:</strong> ";
		echo htmlspecialchars($book['isbn']);
		echo "</li><li><strong>Our Price:</strong> ";
		echo number_format($book['price'], 2);
		echo "</li><li><strong>Description:</strong> ";
		echo htmlspecialchars($book['description']);
		echo "</li></ul></td></tr></table>";
	} else {
		echo "<p>The details of this book cannot be displayed at this time.</p>";
	}
	echo "<br />";
}
?>