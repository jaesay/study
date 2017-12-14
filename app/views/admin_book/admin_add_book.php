<div class="add_book">
	<form action="/bookshop-mvc/public/admin_book/add_book" method="post" enctype="multipart/form-data">
		<input type="hidden" nmae="MAX_FILE_SIZE" value="1000000">
		<label for="image">이미지: </label>
		<input type="file" name="image" id="image">
		<label for="author">저자: </label>
		<input type="text" name="author" id="author" maxlength="20" required>
		<label for="title">제목: </label>
		<input type="text" name="title" id="title" maxlength="30">
		<label for="catname">카테고리: </label>
		<select name="catname" required>
			<option value="programming">PROGRAMMING</option>
			<option value="database">DATABASE</option>
			<option value="network">NETWORK</option>
			<option value="os">OS</option>
			<option value="etc">ETC</option>
		</select>
		<label for="price">판매가: </label>
		<input type="text" name="price" id="price" class="numeric" required>원
		<label for="pub_date">출간일: </label>
		<input type="date" name="pub_date" id="pub_date" required>
		<label for="description">책 소개: </label>
		<textarea name="description" id="description" cols="30" rows="10"></textarea>
		<div class="btn-group btn-group-justified">
			<div class="btn-group">
				<input type="submit" value="추가" class="btn btn-default">
			</div>
			<div class="btn-group">
				<button type="button" class="btn btn-default" id="cancel">취소</button>
			</div>
		</div>
	</form>
</div>