<div class="signup">
	<form action="/bookshop-mvc/public/member/change_member" method="post" id="signup">
		<input type="hidden" name="is_valid" id="is_valid" value="false">
		<label for="memberid">아이디: </label>
		<input type="text" name="memberid" id="ch_memberid" maxlength="16" value="<?php echo htmlspecialchars($data['memberid']);?>" style="background-color: #ebebeb;" required readonly>
		<label for="pwd">비밀번호: </label><span id="info"><span class="glyphicon glyphicon-info-sign"></span>비밀번호는 6자리 이상 16자리 이하여야 합니다.</span>
		<input type="password" name="pwd" id="pwd" maxlength="16" required>
		<label for="confirm_pwd">비밀번호 확인: </label>
		<input type="password" name="confirm_pwd" id="confirm_pwd" maxlength="16" required>
		<label for="email">이메일: </label>
		<input type="text" name="email" id="email" maxlength="30" value="<?php echo htmlspecialchars($data['email']);?>" required>
		<label for="name">이름: </label>
		<input type="text" name="name" id="name" maxlength="30" value="<?php echo htmlspecialchars($data['name']);?>" required>
		<label for="address">주소: </label>
		<input type="text" name="address" id="address" maxlength="50" value="<?php echo htmlspecialchars($data['address']);?>" required>
		<label for="phone">전화번호: </label>
		<input type="text" name="phone" id="phone" maxlength="11" class="numeric" value="<?php echo htmlspecialchars($data['phone']);?>" required>
		<input type="button" value="취소" class="btn btn-default" id="cancel">
		<input type="button" value="변경하기" class="btn btn-default" id="ch_mem_btn">
	</form>
</div>