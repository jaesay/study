package test;
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
	private static final String EXIT = "exit"; // Client를 종료한다.

	public static void main(String[] args) {
		new ClientTests().communicateWithServer();
	}
	
	private void communicateWithServer() {
		connect();
		while(true) {
			printGuide();
			
			String request = "";
			String message = scanner.nextLine();
			
			// exit라고 입력하면 해당 client 종료
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
	
	// Test를 위한 method
	private String test() {
		
		return null;
	}

	// 메인 쓰레드 외 추가적인 쓰레드가 하나 더 필요
	private void send(String request) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					// 서버로 데이터 보내기
					byte[] byteArr = request.getBytes("UTF-8");
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(byteArr);
					outputStream.flush();
					System.out.println("[보내기 완료]");
				} catch(Exception e) {
					System.out.println("[서버 통신 안됨]");
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
			System.out.println(i +". " + m.getUiName() + ": " + m.getName() + "을 입력해주세요.");
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
		System.out.print("전송할 메세지: ");
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
					System.out.println("[서버 통신 안됨]");
					if(!socket.isClosed()) { disconnect(); }
					return;
				}
				receive(); // server에서 보낸 msg를 받음
			}
		};
		thread.start();
	}
	
	private void disconnect() {
		try {
			System.out.println("[연결 끊음]");
			// server와 연결 끊음
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
				int readByteCount = inputStream.read(byteArr); // 서버가 보낸 데이터를 읽고(다른 client들이 보낸 msg들임)
				
				// 서버가 정상적으로 Socket의 close()를 호출했을 경우
				if(readByteCount == -1) {
					throw new IOException(); 
				}
				
				String data = new String(byteArr, 0, readByteCount, "UTF-8");
				
				System.out.println("[받기 완료] "  + data);
			} catch (Exception e) {
				System.out.println("[서버 통신 안됨]");
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