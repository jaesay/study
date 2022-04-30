package protocol.enums;

public enum Mode {
	
	SIGNIN(1, "signin", "�α���"),
	SIGNUP(2, "signup", "ȸ������"),
	BOOK(3, "book", "�����ϱ�"),
	MOVIE(4, "movie", "��ȭ����"),
	SCHEDULE(5, "schedule", "������"),
	SEAT(6, "seat", "�¼� ����"),
	PAY(7, "pay", "����"),
	CANCEL(8, "cancel", "�ֹ� ���"),
	CHECK(9, "find", "���� ��ȸ"),
	BROADCAST(10, "broadcast", "��������"),
	SIGNOUT(11, "signout", "�α׾ƿ�"),
	DELETE_ACCOUNT(12, "delete account", "ȸ�� Ż��"),
	POST_MOVIE(13, "post movie", "��ȭ ������"),
	THEATER_SCHEDULE(14, "theater schedule", "�󿵰� ����"),
	POST_SCHEDULE(15, "post schedule", "��ȭ ���� ������"),
	CLIENT_LIST(16, "client list", "����� ���"),
	CLOSE_SOCEKT(17, "close socket", "���� ����");
	
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
