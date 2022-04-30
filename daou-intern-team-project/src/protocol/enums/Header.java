package protocol.enums;

public enum Header {
	MODE(4),
	CONTENT_LENGTH(4),
	VERSION(2),
	HEADER(10);
	
	private int length;
	
	Header(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
}
