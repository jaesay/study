<div class="auth">
	<form action="" method="post" id="auth">
		<input type="hidden" name="next" id="next" value="<?php echo htmlspecialchars($data['next']); ?>">
		<h4><span class="glyphicon glyphicon-info-sign"></span>&nbsp;본인 인증을 위해 비밀번호를 입력해주세요.</h4>
		<label for="pwd">비밀번호: </label>
		<input type="password" name="pwd" id="pwd" maxlength="16" required>
		<input type="button" value="취소" class="btn btn-default" id="cancel">
		<input type="submit" value="인증하기" class="btn btn-default">
	</form>
</div>