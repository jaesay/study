package study;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import protocol.enums.Header;
import protocol.enums.Mode;

public class SeatRequestClientTests {
	
	private static final int SCHEDULEID = 7;
	private static final int SEATID = 31;
	private static final int SEATNUM = 3;
	private static final int TICKET_PRICE = 8000;
	
	public static void main(String[] args) {
		
		SeatRequestClientTests seatRequestClientTests = new SeatRequestClientTests();
		for(int i=100; i<120; i++) {
			seatRequestClientTests.test(i);
		}
		
	}
	
	private void test(int i) {

		class SeatRequestTask implements Runnable {
	        int i;
	        SeatRequestTask(int i) { this.i = i; }
	        
	        @Override
	        public void run() {
	            work(i);
	        }
	    }
	    
	    Thread thread = new Thread(new SeatRequestTask(i));
	    thread.setName("Thead" + i);
	    thread.start();
	}
	
	private void work(int i) {
		Socket socket;
		InputStream inputStream; 
		OutputStream outputStream;
		try {
			socket = new Socket("127.0.0.1", 18080);
			String data = "user" + i + ",1234";
			int contentLength = data.length();
			
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			ByteBuffer headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
			headerByteBuffer.putInt(Mode.SIGNIN.getCode())
					  .putInt(contentLength)
					  .putShort((short) 1);
			
			ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength);
			
			packetByteBuffer.put(headerByteBuffer.array())
							.put(data.getBytes());

			outputStream.write(packetByteBuffer.array());
			outputStream.flush();
			
			
			byte[] bytes = new byte[200];
			inputStream.read(bytes);
				
			System.out.println(Thread.currentThread().getName() + "로그인 완료");
			
			// 로그인 완료
			Thread.sleep(10 * 1000);
			
			// 일정 선택
			headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
			String data1 = String.valueOf(SCHEDULEID);
			int contentLength1 = data1.length();
			headerByteBuffer.putInt(Mode.SCHEDULE.getCode())
					  .putInt(contentLength1)
					  .putShort((short) 1);
			
			packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength1);
			
			packetByteBuffer.put(headerByteBuffer.array())
							.put(data1.getBytes());

			outputStream.write(packetByteBuffer.array());
			outputStream.flush();
			
			bytes = new byte[200];
			inputStream.read(bytes);
			
			// 일정 선택 완료
			Thread.sleep(10 * 1000);
			
			// 10초 쉬고 90, 91, 92 좌석 예매 요청 시작
			String data2 = String.valueOf(SEATID) + "," + String.valueOf(SEATNUM);
			int contentLength2 = data2.length();
			
			headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
			headerByteBuffer.putInt(Mode.SEAT.getCode())
					  .putInt(contentLength2)
					  .putShort((short) 1);
			
			packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength2);
			
			packetByteBuffer.put(headerByteBuffer.array())
							.put(data2.getBytes());

			outputStream.write(packetByteBuffer.array());
			outputStream.flush();
						
			bytes = new byte[200];
			inputStream.read(bytes);
			
			// 좌석 선택 완료
			Thread.sleep(10 * 1000);
			
			// 10초 쉬고 결제 시작
			String data3 = String.valueOf(TICKET_PRICE * SEATNUM);
			int contentLength3 = data3.length();
			
			headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
			headerByteBuffer.putInt(Mode.PAY.getCode())
					  .putInt(contentLength3)
					  .putShort((short) 1);
			
			packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength3);
			
			packetByteBuffer.put(headerByteBuffer.array())
							.put(data3.getBytes());

			outputStream.write(packetByteBuffer.array());
			outputStream.flush();
			
			
			inputStream.close();
			outputStream.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
