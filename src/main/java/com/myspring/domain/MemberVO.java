package com.myspring.domain;

import java.util.Date;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.myspring.support.validator.EnglishKorean;


public class MemberVO {
	@Length(max = 20, min = 3)
	private String memberId;
	@Length(max = 20, min = 3)
	private String password;
	@Length(max = 30, min = 1)
	@EnglishKorean
	private String memberName;
	@Email
	private String email;
	@NumberFormat
	private boolean enabled;
	@NumberFormat
	private boolean admin;
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date regDate;
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date lastLogin;
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date updateDate;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", password=" + password + ", memberName=" + memberName + ", email="
				+ email + ", enabled=" + enabled + ", admin=" + admin + ", regDate=" + regDate + ", lastLogin="
				+ lastLogin + ", updateDate=" + updateDate + "]";
	}
}
