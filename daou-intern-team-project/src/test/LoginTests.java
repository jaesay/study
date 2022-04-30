package test;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Header;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;


/**
 * 1�� ���� 3000���� ����ڰ� ���ÿ� �α��� ���� �� �����ϴ� Ƚ���� �׽�Ʈ�ϴ� �ڵ带 �����Ѵ�.
 * @author ���缺
 * @since 2019-01-26
 */
public class LoginTests {
	
	private static final int USER_COUNT = 3_000;
	private static AtomicInteger atomicInteger = new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		LoginTests loginTests = new LoginTests();

		for(int i=1; i <= USER_COUNT; i++) {
			loginTests.test(i);
		}
		
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("����: " + atomicInteger.get() + ", ����: " + (USER_COUNT - atomicInteger.get()));
		
	}
	
	private void test(int i) {

		class LoginTask implements Runnable {
	        int i;
	        LoginTask(int i) { this.i = i; }
	        
	        @Override
	        public void run() {
	        	Random rand = new Random();

	        	int randomNum = rand.nextInt(1000) + 1;
				try {
					Thread.sleep(randomNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	
	            task(i);
	        }
	    }
	    
	    Thread thread = new Thread(new LoginTask(i));
	    thread.setName("Thead" + i);
	    thread.start();
	}
	
	private void task(int i) {
		Socket socket;
		DataInputStream dataInputStream; 
		OutputStream outputStream;
		try {
			socket = new Socket("127.0.0.1", 18080);
			
			String data = "id" + i + ",1234";
			
			dataInputStream = new DataInputStream(socket.getInputStream());
			outputStream = socket.getOutputStream();
			
			
			outputStream.write(createTestPacket(Mode.SIGNIN.getCode(), data));
			outputStream.flush();
			
			ProtocolParser protocolParser = new ProtocolParser();
			Packet packet = protocolParser.getObjectPacket(dataInputStream);
			
			if(!Body.INVALID.getCode().equals(packet.getData())) {
				System.out.println(Thread.currentThread().getName() + " �α��� ����");
				atomicInteger.incrementAndGet();
			} else {
				System.out.println(Thread.currentThread().getName() + " �α��� ����");
			}
			
			Thread.sleep(100 * 1000);
			socket.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
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
