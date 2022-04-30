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
	SIGNOUT(11, "signout", "로그아웃"),
	DELETE_ACCOUNT(12, "delete account", "회원 탈퇴"),
	POST_MOVIE(13, "post movie", "영화 포스팅"),
	THEATER_SCHEDULE(14, "theater schedule", "상영관 일정"),
	POST_SCHEDULE(15, "post schedule", "영화 일정 포스팅"),
	CLIENT_LIST(16, "client list", "사용자 목록"),
	CLOSE_SOCEKT(17, "close socket", "강제 종료");
	
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
