package protocol.enums;

public enum Mode {
	SIGNIN(1, "signin", "로그인"),
	SIGNUP(2, "signup", "회원가입"),
	BOOK(3, "book", "예매하기"),
	MOVIE(4, "movie", "영화선택"),
	SCHEDULE(5, "schedule", "스케줄"),
	SEAT(6, "seat", "좌석 선택"),
	PAY(7, "pay", "결제"),
	CANCEL(8, "cancel", "주문 취소"),
	CHECK(9, "find", "예매 조회"),
	BROADCAST(10, "broadcast", "공지사항"),
	FAIL(100, "fail", "서버 에러");
	
	private int code;
	private String engName;
	private String korName;
	
	Mode(int code, String engName, String korName) {
		this.code = code;
		this.engName = engName;
		this.korName = korName;
	}
	
	public int getCode() {return code;}
	public String getEngName() {return engName;}
	public String getKorName() {return korName;}
}
