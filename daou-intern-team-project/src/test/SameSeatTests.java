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
 * 100명의 고객이 같은 좌석들에 결제를 요청하면 1명만 성공하는지 테스트하는 코드를 작성한다
 * @author 김재성
 * @since 2019-01-26
 */
public class SameSeatTests {
	
	private static final int SCHEDULEID = 64;
	private static final int SEATID = 31;
	private static final int SEATNUM = 3;
	private static final int TICKET_PRICE = 8000;
	private static final int USER_COUNT = 10;
	private static final AtomicInteger atomicInteger = new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		SameSeatTests seatRequestClientTests = new SameSeatTests();
		for(int i=1; i<=USER_COUNT; i++) {
			seatRequestClientTests.test(i);
		}
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("성공: " + atomicInteger.get() + ", 실패: " + (USER_COUNT - atomicInteger.get()));
	}
	
	private void test(int i) {

		class SeatRequestTask implements Runnable {
	        int i;
	        SeatRequestTask(int i) { this.i = i; }
	        
	        @Override
	        public void run() {
	            task(i);
	        }
	    }
	    
	    Thread thread = new Thread(new SeatRequestTask(i));
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

			outputStream.write(createTestPacket(Mode.SCHEDULE.getCode(), data));
			outputStream.flush();
			
			
			ProtocolParser protocolParser = new ProtocolParser();
			Packet packet = protocolParser.getObjectPacket(dataInputStream);
			
			if(!Body.INVALID.getCode().equals(packet.getData())) {
				System.out.println(Thread.currentThread().getName() + "로그인 완료");
			}
				
			
			// 로그인 완료
			Thread.sleep(10 * 1000);
			
			// 일정 선택
			String data1 = String.valueOf(SCHEDULEID);

			outputStream.write(createTestPacket(Mode.SCHEDULE.getCode(), data1));
			outputStream.flush();
			
			protocolParser = new ProtocolParser();
			packet = protocolParser.getObjectPacket(dataInputStream);
			
			if(!Body.INVALID.getCode().equals(packet.getData())) {
				System.out.println(Thread.currentThread().getName() + "일정 선택 완료");
			}
			
			// 일정 선택 완료
			Thread.sleep(10 * 1000);
			
			// 10초 쉬고 90, 91, 92 좌석 예매 요청 시작
			String data2 = String.valueOf(SEATID) + "," + String.valueOf(SEATNUM);

			outputStream.write(createTestPacket(Mode.SEAT.getCode(), data2));
			outputStream.flush();
						
			protocolParser = new ProtocolParser();
			packet = protocolParser.getObjectPacket(dataInputStream);
			
			if(!Body.INVALID.getCode().equals(packet.getData())) {
				System.out.println(Thread.currentThread().getName() + "좌석 선택 완료");
			}
			
			// 좌석 선택 완료
			Thread.sleep(10 * 1000);
			
			// 10초 쉬고 결제 시작
			String data3 = String.valueOf(TICKET_PRICE * SEATNUM);

			outputStream.write(createTestPacket(Mode.PAY.getCode(), data3));
			outputStream.flush();
			
			protocolParser = new ProtocolParser();
			packet = protocolParser.getObjectPacket(dataInputStream);
			
			if(!Body.INVALID.getCode().equals(packet.getData())) {
				System.out.println("Lucky User: " + Thread.currentThread().getName());
				atomicInteger.incrementAndGet();
			}

			dataInputStream.close();
			outputStream.close();
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
