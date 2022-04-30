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
 * Header�� ��ȿ���� �� üũ�Ǵ��� �׽�Ʈ�ϴ� �ڵ带 �����Ѵ�.
 * @author ���缺
 * @since 2019-01-26
 */
public class HeaderValidationTests {

	public static void main(String[] args) {
		new HeaderValidationTests().test();
	}

	private void test() {
		
		/*������ ����ϴ� �Ѱ��� thread ����*/
		new Thread(()-> {
			
			try {
				Socket socket = new Socket("127.0.0.1", 18080);
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				OutputStream outputStream = socket.getOutputStream();
				
				StringBuilder stringBuilder = new StringBuilder();
				String tenWords = "aaaaaaaaaa";
		
				// 1KB�� �Ѵ� �����͸� ����
				for(int i = 0; i<3000; i++) {
					stringBuilder.append(tenWords);
				}
								
				// Content-Length ���� �׽�Ʈ
				String longWordData = stringBuilder.toString();
				
				outputStream.write(createTestPacket(Mode.SIGNIN.getCode(), longWordData));
				outputStream.flush();
				
				ProtocolParser protocolParser = new ProtocolParser();
				Packet packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'Content-Length ����' ��ȿ�� ���");
				} else {
					System.out.println("'Content-Length ����' ��ȿ�� ����");
				}
								
				Thread.sleep(5 * 1000);
				
				// Version �׽�Ʈ
				String validLoginData = "user100,1234";
				
				int contentLength = validLoginData.length();
				ByteBuffer headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
				headerByteBuffer.putInt(Mode.SIGNIN.getCode())
						  .putInt(contentLength)
						  .putShort((short) 2);	// ��ȿ���� ���� Version(2)�� ���� Header ����
				
				ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength);
				
				packetByteBuffer.put(headerByteBuffer.array())
								.put(validLoginData.getBytes());
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), validLoginData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'Version' ��ȿ�� ���");
				} else {
					System.out.println("'Version' ��ȿ�� ����");
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
