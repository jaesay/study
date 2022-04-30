package domain;

import java.sql.Timestamp;

public class Member {
	
	private int memberId;
	private String memberName;
	private String password;
	private String name;
	private String role;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	// Getter, Setter method 정의
	public int getMemberId() {return memberId;}
	public void setMemberId(int memberId) {this.memberId = memberId;}
	public String getMemberName() {return memberName;}
	public void setMemberName(String memberName) {this.memberName = memberName;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getRole() {return role;}
	public void setRole(String role) {this.role = role;}
	public Timestamp getCreatedDate() {return createdDate;}
	public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}
	public Timestamp getUpdatedDate() {return updatedDate;}
	public void setUpdatedDate(Timestamp updatedDate) {this.updatedDate = updatedDate;}

	// toString 오버라이딩
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberName=" + memberName + ", password=" + password + ", name="
				+ name + ", role=" + role + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}
}
