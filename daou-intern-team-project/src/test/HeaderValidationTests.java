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
 * Header의 유효성이 잘 체크되는지 테스트하는 코드를 구현한다.
 * @author 김재성
 * @since 2019-01-26
 */
public class HeaderValidationTests {

	public static void main(String[] args) {
		new HeaderValidationTests().test();
	}

	private void test() {
		
		/*서버와 통신하는 한개의 thread 생성*/
		new Thread(()-> {
			
			try {
				Socket socket = new Socket("127.0.0.1", 18080);
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				OutputStream outputStream = socket.getOutputStream();
				
				StringBuilder stringBuilder = new StringBuilder();
				String tenWords = "aaaaaaaaaa";
		
				// 1KB가 넘는 데이터를 생성
				for(int i = 0; i<3000; i++) {
					stringBuilder.append(tenWords);
				}
								
				// Content-Length 길이 테스트
				String longWordData = stringBuilder.toString();
				
				outputStream.write(createTestPacket(Mode.SIGNIN.getCode(), longWordData));
				outputStream.flush();
				
				ProtocolParser protocolParser = new ProtocolParser();
				Packet packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'Content-Length 길이' 유효성 통과");
				} else {
					System.out.println("'Content-Length 길이' 유효성 실패");
				}
								
				Thread.sleep(5 * 1000);
				
				// Version 테스트
				String validLoginData = "user100,1234";
				
				int contentLength = validLoginData.length();
				ByteBuffer headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
				headerByteBuffer.putInt(Mode.SIGNIN.getCode())
						  .putInt(contentLength)
						  .putShort((short) 2);	// 유효하지 않은 Version(2)을 담은 Header 생성
				
				ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength);
				
				packetByteBuffer.put(headerByteBuffer.array())
								.put(validLoginData.getBytes());
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), validLoginData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'Version' 유효성 통과");
				} else {
					System.out.println("'Version' 유효성 실패");
				}
				
				dataInputStream.close();
				outputStream.close();
				socket.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}).start();
		
	}
	
	
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
