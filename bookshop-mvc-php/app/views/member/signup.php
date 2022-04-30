<div class="signup">
	<form action="/bookshop-mvc/public/member/signup" method="post" id="signup">
		<input type="hidden" name="is_valid" id="is_valid" value="false">
		<label for="memberid">아이디: </label><span id="info"><span class="glyphicon glyphicon-info-sign"></span>아이디는 6자리 이상 16자리 이하여야 합니다.</span>
		<input type="text" name="memberid" id="memberid" maxlength="16" required>
		<label for="pwd">비밀번호: </label><span id="info"><span class="glyphicon glyphicon-info-sign"></span>비밀번호는 6자리 이상 16자리 이하여야 합니다.</span>
		<input type="password" name="pwd" id="pwd" maxlength="16" required>
		<label for="confirm_pwd">비밀번호 확인: </label>
		<input type="password" name="confirm_pwd" id="confirm_pwd" maxlength="16" required>
		<label for="email">이메일: </label>
		<input type="text" name="email" id="email" maxlength="30" required>
		<label for="name">이름: </label>
		<input type="text" name="name" id="name" maxlength="30" required>
		<label for="address">주소: </label>
		<input type="text" name="address" id="address" maxlength="50" required>
		<label for="phone">전화번호: </label>
		<input type="text" name="phone" id="phone" maxlength="11" class="numeric" required>
		<input type="button" value="취소" class="btn btn-default" id="cancel">
		<input type="submit" value="가입하기" class="btn btn-default">
	</form>
</div>