		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-book"></span>&nbsp;&nbsp;All
					<?php 
					if (@$_SESSION['memberid'] == 'admin') {
					?>
					<button type="button" class="btn btn-default" id="admin_add_book">추가</button>
					<?php } 
					?>
				</h3>
			</div>
		<?php
			for ($i=0; $i < count($data) ; $i++) { 
		?>

			<div class="panel-body">
				<div class="media">
					<div class="media-left">
						<a href="/bookshop-mvc/public/book/show_book/<?= $data[$i]['bookid'] ?>">
						<img src="<?= $data[$i]['image'] ?>" alt="New Book1" class="media-object"></a>
					</div>
					<div class="media-body">
						<h4 class="media-heading"><?= $data[$i]['title'] ?>&nbsp;<span class="badge">New</span>&nbsp;<span class="badge">Best</span></h4>
						<h5><?= $data[$i]['author'] ?>|<?= $data[$i]['pub_date'] ?></h5>
						<h5><?= $data[$i]['price'] ?>원</h5>
					</div>
				</div>
			</div>
			<?php  
			if ($i != (count($data)-1)) {
			?>
			<hr>
			<?php
			}
			?>
		<?php 
			}
		?>
		</div>