package protocol;

public class Packet {
	
	// header
	private int mode;			// 4 bytes
	private int contentLength;	// 4 bytes
	private short version;  		// 2 bytes
	
	// body
	private Object data;	// 가변 길이

	// Getter, Setter 정의
	public int getMode() {return mode;}
	public void setMode(int mode) {this.mode = mode;}
	public int getContentLength() {return contentLength;}
	public void setContentLength(int contentLength) {this.contentLength = contentLength;}
	public short getVersion() {return version;}
	public void setVersion(short version) {this.version = version;}
	public Object getData() {return data;}
	public void setData(Object data) {this.data = data;}

	// ToString 오버라이딩
	@Override
	public String toString() {
		return "Packet [mode=" + mode + ", contentLength=" + contentLength + ", version=" + version + ", data=" + data
				+ "]";
	}
}
