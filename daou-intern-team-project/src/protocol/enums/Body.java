package protocol.enums;

public enum Body {

	INVALID("0"),
	EMPTY("3"),
	ERROR("2"),
	SUCCESS("1");
	
	private String code;
	
	Body(String code) {
		this.code = code;
	}
	
	public String getCode() {return code;}
	
}
