package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import protocol.enums.Header;

public class MaliciousClient {
	private Socket socket;
	private boolean stop = false;
	
	public static void main(String[] args) {
		new MaliciousClient().connectToServer();
	}
	
	private void connectToServer() {
		connect();
		while(!stop) {
			try {
				Thread.sleep(2 * 1000);
				attackByVersion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void attackByVersion() {
		System.out.println("attackByVersion.............");
		ByteBuffer headerByteBuffer = ByteBuffer.allocate(12);
		headerByteBuffer.putInt(1)
				  .putInt(0)
				  .putInt(0);
		
		ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
		
		packetByteBuffer.put(headerByteBuffer.array());
		
		OutputStream outputStream;
		try {
			outputStream = socket.getOutputStream();
			outputStream.write(packetByteBuffer.array());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
			stop = true;
		}
	}

	private void connect() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket("localhost", 18080);
				} catch(Exception e) {
					System.out.println("[���� ��� �ȵ�]");
					if(!socket.isClosed()) { disconnect(); }
					return;
				}
				receive();
			}
		};
		thread.start();
	}
	
	private void disconnect() {
		try {
			System.out.println("[���� ����]");
			// server�� ���� ����
			if(socket!=null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {}
	}	
	
	private void receive() {
		while(true) {
			try {
				System.out.println("receive..................................");				
				
				byte[] byteArr = new byte[100];
				InputStream inputStream = socket.getInputStream();
				int readByteCount = inputStream.read(byteArr); // ������ ���� �����͸� �а�(�ٸ� client���� ���� msg����)
				
				if(readByteCount == -1) {
					throw new IOException(); 
				}
				
				String data = new String(byteArr, 0, readByteCount, "UTF-8");
				
				System.out.println("[�ޱ� �Ϸ�] "  + data);
			} catch (Exception e) {
				System.out.println("[���� ��� �ȵ�]");
				disconnect();
				break;
			}
		}
	}
}
