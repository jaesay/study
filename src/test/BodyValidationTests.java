package test;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Header;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;

/**
 * 바디의 Data의 유효성 체크가 잘되는지 테스트하는 코드를 구현한다.
 * @author 김재성
 * @since 2019-01-26
 */
public class BodyValidationTests {

	public static void main(String[] args) {
		new BodyValidationTests().test();
	}

	private void test() {
		
		/*서버와 통신하는 한개의 thread 생성*/
		new Thread(()-> {
			
			try {
				Socket socket = new Socket("127.0.0.1", 18080);
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				OutputStream outputStream = socket.getOutputStream();
				
								
				// 데이터 개수 테스트
				String invalidDataNumData = "jjaesay,,KimJaeSung";
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), invalidDataNumData));
				outputStream.flush();
				
				ProtocolParser protocolParser = new ProtocolParser();
				Packet packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'데이터 개수' 유효성 통과");
				} else {
					System.out.println("'데이터 개수' 유효성 실패");
				}
								
				Thread.sleep(5 * 1000);
				
				// 아이디 길이 테스트
				String invalidLenghData = "jjaesayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy,1234,KimJaeSung";
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), invalidLenghData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'아이디 길이' 유효성 통과");
				} else {
					System.out.println("'아이디 길이' 유효성 실패");
				}
				
				Thread.sleep(5* 1000);

				// Integer 유효성 테스트
				String invalidPKData = "not number";
				
				outputStream.write(createTestPacket(Mode.MOVIE.getCode(), invalidPKData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'PK가 숫자' 유효성 통과");
				} else {
					System.out.println("'PK가 숫자' 유효성 실패");
				}
				
				// 이후에 테스트할 기능들은 Admin만 사용할 수있는 기능이기 때문에 Admin으로 로그인 
				String validAdminLoginData = "admin,1234";
				
				outputStream.write(createTestPacket(Mode.SIGNIN.getCode(), validAdminLoginData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("관리자 로그인 성공");
				} else {
					System.out.println("관리자 로그인 실패");
				}
				
				// 날짜 유효성 테스트
				String invalidReservedDateData = "1,1,11111111,10:30,2:30";
				
				outputStream.write(createTestPacket(Mode.POST_SCHEDULE.getCode(), invalidReservedDateData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'날짜' 유효성 통과");
				} else {
					System.out.println("'날짜' 유효성 실패");
				}
				
				Thread.sleep(5* 1000);
				
				// 시간 유효성 테스트
				String invalidStartTimeData = "1,1,2019-02-04,1111,2:30";
				
				outputStream.write(createTestPacket(Mode.POST_SCHEDULE.getCode(), invalidStartTimeData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'시간' 유효성 통과");
				} else {
					System.out.println("'시간' 유효성 실패");
				}
				
				dataInputStream.close();
				outputStream.close();
				socket.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}).start();
		
	}
	
	/**
	 * 서버에 전달할 패킷을 만들어주는 method입니다.
	 * @param mode
	 * @param data
	 * @return 바이트 패킷
	 */
	private byte[] createTestPacket(int mode, String data) {
		int contentLength = data.length();
		ByteBuffer headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
		headerByteBuffer.putInt(mode)
				  .putInt(contentLength)
				  .putShort((short) 1);
		
		ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength);
		
		packetByteBuffer.put(headerByteBuffer.array())
						.put(data.getBytes());
		
		return packetByteBuffer.array();
	}
}
