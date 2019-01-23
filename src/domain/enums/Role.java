package domain.enums;

public enum Role {

	ADMIN(1, "admin", "관리자"),
	MANAGER(2, "manager", "매니저"),
	USER(3, "user", "사용자");
	
	private int code;
	private String engName;
	private String korName;
	
	Role(int code, String engName, String korName) {
		this.code = code;
		this.engName = engName;
		this.korName = korName;
	}
	
	public int getCode() {return code;}
	public String getEngName() {return engName;}
	public String getKorName() {return korName;}
}
