//*********************************************************************************************************
//
// ����� �����ϰ� ���� �ʽ��ϴ�. C Client �� Java Server �ϼ� ��Ų ���Ŀ� �̾ �����ϰڽ��ϴ�.
//
//*********************************************************************************************************
package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import protocol.enums.Header;
import protocol.enums.Mode;

public class Client {

	public static void main(String[] args) {
		new Client().communicateWithServer();
	}

	private void communicateWithServer() {
		
		Runnable runnable = new Runnable() {			
			@Override
			public void run() {
				try {
					Socket socket = new Socket("127.0.0.1", 18080);
					
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					
					Thread.sleep(10 * 1000);
					
					String msg = String.valueOf(Math.random() * 49 + 1);
					
					ByteBuffer headerByteBuffer = ByteBuffer.allocate(12);
					headerByteBuffer.putInt(Mode.BROADCAST.getCode())
							  .putInt(msg.getBytes().length)
							  .putInt(1);
					
					ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + msg.getBytes().length);
					
					packetByteBuffer.put(headerByteBuffer.array())
									.put(msg.getBytes());
					
					outputStream.write(packetByteBuffer.array());
					outputStream.flush();
					
					while(true) {
						byte[] bytes = new byte[100];
						int l = inputStream.read(bytes);
						
						/*if(l < 0) {
							socket.close();
						}*/
						System.out.println(Thread.currentThread().getName() + ": broadcast ����");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread thread1 = new Thread(runnable);
		thread1.setName("1�� Ŭ���̾�Ʈ");
		thread1.start();
		
		Thread thread2 = new Thread(runnable);
		thread2.setName("2�� Ŭ���̾�Ʈ");
		thread2.start();
		
		Thread thread3 = new Thread(runnable);
		thread3.setName("3�� Ŭ���̾�Ʈ");
		thread3.start();
		
		Thread thread4 = new Thread(runnable);
		thread4.setName("4�� Ŭ���̾�Ʈ");
		thread4.start();
		
	}

}