package study;
/*
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import protocol.enums.Mode;
*/
public class ClientTests {
/*	
	private Socket socket;
	private Scanner scanner = new Scanner(System.in);
	private String id = "";
	private String password = "";
	private static final String EXIT = "exit"; // Client�� �����Ѵ�.

	public static void main(String[] args) {
		new ClientTests().communicateWithServer();
	}
	
	private void communicateWithServer() {
		connect();
		while(true) {
			printGuide();
			
			String request = "";
			String message = scanner.nextLine();
			
			// exit��� �Է��ϸ� �ش� client ����
			if(message.equals(EXIT)) 
				break;
			else if(message.equalsIgnoreCase("test"))
				request = test();
			else if(message.equalsIgnoreCase(Mode.SIGNUP.getName())) 
				request = signup();
			else if(message.equalsIgnoreCase(Mode.SIGNIN.getName())) 
				request = signin();
			else if(message.equalsIgnoreCase(Mode.BROADCAST.getName())) 
				request = broadcast();
			
			send(request);
		}
		disconnect();
	}
	
	// Test�� ���� method
	private String test() {
		
		return null;
	}

	// ���� ������ �� �߰����� �����尡 �ϳ� �� �ʿ�
	private void send(String request) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					// ������ ������ ������
					byte[] byteArr = request.getBytes("UTF-8");
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(byteArr);
					outputStream.flush();
					System.out.println("[������ �Ϸ�]");
				} catch(Exception e) {
					System.out.println("[���� ��� �ȵ�]");
					disconnect();
				}				
			}
		};
		thread.start();
	}

	private void printGuide() {
		int i = 1;
		System.out.println("==============================================================");
		for(Mode m : Mode.values()) {
			System.out.println(i +". " + m.getUiName() + ": " + m.getName() + "�� �Է����ּ���.");
			i++;
		}
		System.out.println("==============================================================");
	}

	private String signup() {
		System.out.print("id: ");
		setId(scanner.nextLine());
		System.out.print("password: ");
		setPassword(scanner.nextLine());
		
		return setRequest(Mode.SIGNUP.toString(), getId());
	}
	
	private String signin() {
		System.out.print("id: ");
		setId(scanner.nextLine());
		System.out.print("password: ");
		setPassword(scanner.nextLine());
		
		return setRequest(Mode.SIGNIN.toString(), getId());
	}
	
	private String broadcast() {
		System.out.print("������ �޼���: ");
		String message = scanner.nextLine();
		return setRequest(Mode.BROADCAST.toString(), message);
	}
	
	private String setRequest(String header, String message) {
		System.out.println("header: " + header);
		System.out.println("message: " + message);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(header + "/")
			 		 .append(message);
		return stringBuilder.toString();
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
				receive(); // server���� ���� msg�� ����
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
				byte[] byteArr = new byte[100];
				InputStream inputStream = socket.getInputStream();
				int readByteCount = inputStream.read(byteArr); // ������ ���� �����͸� �а�(�ٸ� client���� ���� msg����)
				
				// ������ ���������� Socket�� close()�� ȣ������ ���
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

	//getter, setter method
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
*/
}