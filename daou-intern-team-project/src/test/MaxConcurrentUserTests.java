package test;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Header;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;

/**
 * 최대 동시접속자 수가 몇명까지 가능한지 테스트하는 코드를 구현한다.
 * @author 김재성
 * @Since 2019-01-26
 */
public class MaxConcurrentUserTests {
	
	// 현재 동시접속자를 3000명으로 제한했기 때문에 50명만 실패해야 함
	private static final int USER_COUNT = 3_050;
	private static AtomicInteger atomicInteger = new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		MaxConcurrentUserTests maxCurrentUserTests = new MaxConcurrentUserTests();

		for(int i=1; i <= USER_COUNT; i++) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			maxCurrentUserTests.test(i);
		}
		
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("성공: " + atomicInteger.get() + ", 실패: " + (USER_COUNT - atomicInteger.get()));
		
	}
	
	private void test(int i) {

		class MaxConcurrentUserTask implements Runnable {
	        int i;
	        MaxConcurrentUserTask(int i) { this.i = i; }
	        
	        @Override
	        public void run() {
	        	
	            task(i);
	        }
	    }
	    
	    Thread thread = new Thread(new MaxConcurrentUserTask(i));
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
				System.out.println(Thread.currentThread().getName() + " 로그인 성공");
				atomicInteger.incrementAndGet();
			} else {
				System.out.println(Thread.currentThread().getName() + " 로그인 실패");
			}
			
			Thread.sleep(1000 * 1000 + i);
			
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

