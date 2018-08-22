package com.myspring.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberVO implements Serializable{
	private static final long serialVersionUID = -3200720444281952185L;
	String memberId;
	String password;
	String memberName;
	String email;
	boolean enabled;
	String role;
	Date regDate;
	Date lastLogin;
	Date updateDate;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
				+ email + ", enabled=" + enabled + ", role=" + role + ", regDate=" + regDate + ", lastLogin="
				+ lastLogin + ", updateDate=" + updateDate + "]";
	}
	
}
