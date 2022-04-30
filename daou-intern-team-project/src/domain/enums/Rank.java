package domain.enums;

public enum Rank {

	NORMAL(1, "normal", "¿œπ›");
	
	private int code;
	private String engName;
	private String korName;
	
	Rank(int code, String engName, String korName) {
		this.code = code;
		this.engName = engName;
		this.korName = korName;
	}
	
	public int getCode() {return code;}
	public String getEngName() {return engName;}
	public String getKorName() {return korName;}
}
