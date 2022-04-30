package study;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import config.DBCPConfig;
import domain.Member;
import domain.Movie;
import protocol.Packet;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;
import server.persistence.BookRepository;
import server.persistence.MemberRepository;

public class ServerTests {
	
/*	private ExecutorService executorService;
	private ServerSocket serverSocket;
	private List<ClientHandler> connections = Collections.synchronizedList(new ArrayList<>());
	
	public static void main(String[] args) {
		new ServerTests().startService();
	}

	// ���� �޼ҵ�
	private void startService() {
		System.out.println("������Ǯ�� �����մϴ�.");
		executorService = new ThreadPoolExecutor(
				5,
				200,
				150L,
				TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>()
		);
		
		DBCPConfig dbcpConfig = DBCPConfig.getDataSource();
		Connection connection = dbcpConfig.getConnection();
		DBCPConfig.getDataSource().freeConnection(connection);

		try {
			serverSocket = new ServerSocket();		
			serverSocket.bind(new InetSocketAddress("localhost", 18080));
		} catch(Exception e) {
			if(!serverSocket.isClosed()) { stopService(); }
			return;
		}
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {	
				System.out.println("������ ���۵Ǿ����ϴ�.");
				while(true) {
					try {
						Socket socket = serverSocket.accept();
						System.out.println("[���� ����: " + socket.getRemoteSocketAddress()  + ": " + Thread.currentThread().getName() + "]");		
						
						ClientHandler clientHandler = new ClientHandler(socket);
						connections.add(clientHandler);
						System.out.println("[���� ����: " + connections.size() + "]");
					} catch (Exception e) {
						if(!serverSocket.isClosed()) { stopService(); }
						break;
					}
				}
			}
		};
		
		executorService.submit(runnable);
	} //startServer ��
	
	private void stopService() {
		try {
			
			Iterator<ClientHandler> iterator = connections.iterator();
			synchronized (connections) {
				while(iterator.hasNext()) {
					ClientHandler clientHandler = iterator.next();
					clientHandler.socket.close();
					iterator.remove();
				}
			}
			
			if(serverSocket!=null && !serverSocket.isClosed()) { 
				serverSocket.close(); 
			}
			
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			}
			System.out.println("������ ����Ǿ����ϴ�.");
		} catch (Exception e) {}
	} // stopService ��
	// ���� �޼ҵ� ��
	
	private class ClientHandler {
		private Socket socket;
		private Member member;
		private AtomicInteger errorPacketCount = new AtomicInteger(0); // = private volatile int errorPacketCount;
		private boolean stop = false;
		
		ClientHandler(Socket socket) {
			this.socket = socket;
			getPacket();
			checkMaliciousClient();
		}
		
		// Ŭ���̾�Ʈ ��鷯 �޼ҵ� ����
		// Ŭ���̾�Ʈ�ο� ����� ����, ����� Ŭ���̾�Ʈ�� ���� Request Packet�� ������
		private void getPacket() {
			Runnable runnable = new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					
					while(!stop) {
						try {
							DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
							ProtocolParser protocolParser = new ProtocolParser();
							
							Packet packet = protocolParser.getObjectPacket(dataInputStream);
							
							if(packet.getMode() == Mode.SIGNIN.getCode()) { // �α���
								
								MemberRepository memberRepository = new MemberRepository();
								member = memberRepository.findByIdAndPassword(((Member)packet.getData()));
								
								sendPacket(Mode.SIGNIN.getCode(), member);
								
							} else if(packet.getMode() == Mode.SIGNUP.getCode()) { // ȸ������
								
								MemberRepository memberRepository = new MemberRepository();
								int isSignedUp = memberRepository.save(((Member)packet.getData()));
								if(isSignedUp > 0) {
									sendPacket(Mode.SIGNUP.getCode(), isSignedUp);
								} else {
									sendPacket(Mode.SIGNUP.getCode(), "");
								}
								
							} else if(packet.getMode() == Mode.BOOK.getCode()) { // �����ϱ�
								
								BookRepository bookRepository = new BookRepository();
								List<Movie> movies = bookRepository.findScheduledMovieList();
								
								sendPacket(Mode.BOOK.getCode(), movies);
								
							} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// ��ȭ����
								
								BookRepository bookRepository = new BookRepository();
								List<Map<String, String>> resultList = bookRepository.findMovieSchedule(Integer.parseInt(String.valueOf(packet.getData())));
								
								sendPacket(Mode.SCHEDULE.getCode(), resultList);
								
							} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // ���� ����
								
								BookRepository bookRepository = new BookRepository();
								bookRepository.findSeats(
										Integer.parseInt(((Map<String, String>)packet.getData()).get("scheduleId")));
								
							} else if(packet.getMode() == Mode.BROADCAST.getCode()) { // ��������
								
							} else {
								
							}
						} catch (SocketException se) {
							
							try {
								se.printStackTrace();
								connections.remove(ClientHandler.this);
								System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
								socket.close();
							} catch (IOException e2) {}
							
						} catch (NullPointerException npe) {
						
							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] ���� Ƚ��: " + errorPacketCount.get());
							errorPacketCount.incrementAndGet();
							npe.printStackTrace();
							
						} catch (SQLException sqle) {

							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] ���� Ƚ��: " + errorPacketCount.get());
							errorPacketCount.incrementAndGet();
							sqle.printStackTrace();
						
						} catch (IOException ioe) {
							
							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] ���� Ƚ��: " + errorPacketCount.get());
							errorPacketCount.incrementAndGet();
							ioe.printStackTrace();
							
						} catch (Exception e) {
							try {
								e.printStackTrace();
								connections.remove(ClientHandler.this);
								System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
								socket.close();
							} catch (IOException e2) {}
						} finally {
							if(socket.isConnected()) {
								sendPacket(Mode.FAIL.getCode(), "");
							}
							stop = true;
						}
					}
						
				} 
			};
			executorService.submit(runnable);
		} // getRequest ��

		private void sendPacket(int mode, Object data) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
						ProtocolParser protocolParser = new ProtocolParser();
						
						byte[] packet = protocolParser.getBytePacket(mode, data);
						
						dataOutputStream.write(packet);
						
					} catch(Exception e) {
						try {
							e.printStackTrace();
							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
							connections.remove(ClientHandler.this);
							socket.close();
						} catch (IOException e2) {}
					}
				}
			};
			executorService.submit(runnable);
		} // sendResponse ��
		
		private void broadcast(String request) {
			for(ClientHandler clientHandler : connections) {
				clientHandler.sendResponsePacket(request, Status.SUCCESS.getCode()); 
			}
		}
		
		private void checkMaliciousClient() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					while(!stop) {
						try {
							Thread.sleep(15 * 1000);
							
							if(errorPacketCount.get() < 3) {
								errorPacketCount.set(0);
							} else {
								stop = true;
								throw new Exception();
							}
								
						} catch (Exception e) {
							try {
								e.printStackTrace();
								connections.remove(ClientHandler.this);
								System.out.println("[�Ǽ� ����� ����: " + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName() + "]");
								socket.close();
							} catch (IOException e2) {}
						}
						
					}
				}
			};
			executorService.submit(runnable);
		}
		
	} // ClientHandler ��
*/	
}
