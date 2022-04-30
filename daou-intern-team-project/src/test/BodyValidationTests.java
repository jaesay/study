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
 * �ٵ��� Data�� ��ȿ�� üũ�� �ߵǴ��� �׽�Ʈ�ϴ� �ڵ带 �����Ѵ�.
 * @author ���缺
 * @since 2019-01-26
 */
public class BodyValidationTests {

	public static void main(String[] args) {
		new BodyValidationTests().test();
	}

	private void test() {
		
		/*������ ����ϴ� �Ѱ��� thread ����*/
		new Thread(()-> {
			
			try {
				Socket socket = new Socket("127.0.0.1", 18080);
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				OutputStream outputStream = socket.getOutputStream();
				
								
				// ������ ���� �׽�Ʈ
				String invalidDataNumData = "jjaesay,,KimJaeSung";
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), invalidDataNumData));
				outputStream.flush();
				
				ProtocolParser protocolParser = new ProtocolParser();
				Packet packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'������ ����' ��ȿ�� ���");
				} else {
					System.out.println("'������ ����' ��ȿ�� ����");
				}
								
				Thread.sleep(5 * 1000);
				
				// ���̵� ���� �׽�Ʈ
				String invalidLenghData = "jjaesayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy,1234,KimJaeSung";
				
				outputStream.write(createTestPacket(Mode.SIGNUP.getCode(), invalidLenghData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'���̵� ����' ��ȿ�� ���");
				} else {
					System.out.println("'���̵� ����' ��ȿ�� ����");
				}
				
				Thread.sleep(5* 1000);

				// Integer ��ȿ�� �׽�Ʈ
				String invalidPKData = "not number";
				
				outputStream.write(createTestPacket(Mode.MOVIE.getCode(), invalidPKData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'PK�� ����' ��ȿ�� ���");
				} else {
					System.out.println("'PK�� ����' ��ȿ�� ����");
				}
				
				// ���Ŀ� �׽�Ʈ�� ��ɵ��� Admin�� ����� ���ִ� ����̱� ������ Admin���� �α��� 
				String validAdminLoginData = "admin,1234";
				
				outputStream.write(createTestPacket(Mode.SIGNIN.getCode(), validAdminLoginData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("������ �α��� ����");
				} else {
					System.out.println("������ �α��� ����");
				}
				
				// ��¥ ��ȿ�� �׽�Ʈ
				String invalidReservedDateData = "1,1,11111111,10:30,2:30";
				
				outputStream.write(createTestPacket(Mode.POST_SCHEDULE.getCode(), invalidReservedDateData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'��¥' ��ȿ�� ���");
				} else {
					System.out.println("'��¥' ��ȿ�� ����");
				}
				
				Thread.sleep(5* 1000);
				
				// �ð� ��ȿ�� �׽�Ʈ
				String invalidStartTimeData = "1,1,2019-02-04,1111,2:30";
				
				outputStream.write(createTestPacket(Mode.POST_SCHEDULE.getCode(), invalidStartTimeData));
				outputStream.flush();
				
				packet = protocolParser.getObjectPacket(dataInputStream);
				
				if(!Body.INVALID.getCode().equals(packet.getData())) {
					System.out.println("'�ð�' ��ȿ�� ���");
				} else {
					System.out.println("'�ð�' ��ȿ�� ����");
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
	 * ������ ������ ��Ŷ�� ������ִ� method�Դϴ�.
	 * @param mode
	 * @param data
	 * @return ����Ʈ ��Ŷ
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
