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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import config.DBCPConfig;
import domain.Member;
import domain.Movie;
import protocol.Packet;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;
import server.persistence.BookRepository;
import server.persistence.MemberRepository;

public class BroadcastServerTests {
	/*
	private ExecutorService executorService;
	private ServerSocket serverSocket;
//	private List<ClientHandler> connections = Collections.synchronizedList(new ArrayList<>());
	private List<ClientHandler> connections = new ArrayList<>();
	private final ReadWriteLock connectionReadWriteLock = new ReentrantReadWriteLock();
	
	
	public static void main(String[] args) {
		new BroadcastServerTests().startService();
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
						
						connectionReadWriteLock.writeLock().lock();
						ClientHandler clientHandler = new ClientHandler(socket);
						connections.add(clientHandler);
						System.out.println("[���� ����: " + connections.size() + "]");
					} catch (Exception e) {
						if(!serverSocket.isClosed()) { stopService(); }
						break;
					} finally {
						connectionReadWriteLock.writeLock().unlock();
					}
					
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
			connectionReadWriteLock.writeLock().lock();
			Iterator<ClientHandler> iterator = connections.iterator();
			
			while(iterator.hasNext()) {
				ClientHandler clientHandler = iterator.next();
				clientHandler.socket.close();
				iterator.remove();
			}
			
			if(serverSocket!=null && !serverSocket.isClosed()) { 
				serverSocket.close(); 
			}
			
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			}
			System.out.println("������ ����Ǿ����ϴ�.");
		} catch (Exception e) {
			
		} finally {
			connectionReadWriteLock.writeLock().unlock();
		}
		
	} // stopService ��
	// ���� �޼ҵ� ��
	
	private class ClientHandler {
		private Socket socket;
		private Member member;
		
		ClientHandler(Socket socket) {
			this.socket = socket;
			getPacket();
		}
		
		// Ŭ���̾�Ʈ ��鷯 �޼ҵ� ����
		// Ŭ���̾�Ʈ�ο� ����� ����, ����� Ŭ���̾�Ʈ�� ���� Request Packet�� ������
		private void getPacket() {
			Runnable runnable = new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					
					try {
						while(true) {
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
									
									sendPacket(Mode.MOVIE.getCode(), resultList);
									
								} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // ���� ����
									
									BookRepository bookRepository = new BookRepository();
									List<Map<String, String>> resultList = bookRepository.findSeats(	//
														Integer.parseInt(((Map<String, String>)packet.getData()).get("scheduleId")));
									
									sendPacket(Mode.SCHEDULE.getCode(), resultList);
									
								} else if(packet.getMode() == Mode.BROADCAST.getCode()) { // ��������
									
									broadcast(Mode.BROADCAST.getCode(), String.valueOf(packet.getData()));
									
								} else {
									
									
								}
						}
					} catch (SocketException se) {
						
						try {
							se.printStackTrace();
							connectionReadWriteLock.writeLock().lock();
							connections.remove(ClientHandler.this);
							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
							socket.close();
						} catch (IOException e2) {} finally {
							connectionReadWriteLock.writeLock().unlock();
						}
						
					} catch (NullPointerException npe) {
						
						npe.printStackTrace();
						
					} catch (SQLException sqle) {
						
						sqle.printStackTrace();
						
					} catch (IOException ioe) {
						
						ioe.printStackTrace();
						
					} catch (Exception e) {
						try {
							e.printStackTrace();
							connectionReadWriteLock.writeLock().lock();
							connections.remove(ClientHandler.this);
							System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
							socket.close();
						} catch (IOException e2) {} finally {
							connectionReadWriteLock.writeLock().unlock();
						}
					} finally {
						if(socket.isConnected()) {
							sendPacket(Mode.FAIL.getCode(), "");
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
							connectionReadWriteLock.writeLock().lock();
							connections.remove(ClientHandler.this);
							socket.close();
						} catch (IOException e2) {} finally {
							connectionReadWriteLock.writeLock().unlock();
						}
					}
				}
			};
			executorService.submit(runnable);
		} // sendResponse ��
		
		private void broadcast(int mode, String message) {
			for(ClientHandler clientHandler : connections) {
				clientHandler.sendPacket(Mode.BROADCAST.getCode(), message);
				
			}
			connectionReadWriteLock.readLock().lock();
			try {
				for(ClientHandler clientHandler : connections) {
					clientHandler.sendPacket(Mode.BROADCAST.getCode(), message); 

					try {
						Thread.sleep(2 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			} finally {
				connectionReadWriteLock.readLock().unlock();
			}
		}
		
	} // ClientHandler ��
*/	
}

