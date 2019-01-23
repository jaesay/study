package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
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
import domain.Schedule;
import domain.enums.Rank;
import protocol.Packet;
import protocol.enums.Mode;
import protocol.support.ProtocolParser;
import server.persistence.BookRepository;
import server.persistence.MemberRepository;

public class Server {
	
	private ExecutorService executorService;
	private ServerSocket serverSocket;
//	private List<ClientHandler> connections = Collections.synchronizedList(new ArrayList<>());
	private List<ClientHandler> connections = new ArrayList<>();
	private final ReadWriteLock connectionReadWriteLock = new ReentrantReadWriteLock();
	
	
	public static void main(String[] args) {
		new Server().startService();
	}

	// 서버 메소드
	private void startService() {
		System.out.println("쓰레드풀을 생성합니다.");
		executorService = new ThreadPoolExecutor(
				100,
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
				System.out.println("서버가 시작되었습니다.");
				while(true) {
					try {
						Socket socket = serverSocket.accept();
						System.out.println("[연결 수락: " + socket.getRemoteSocketAddress()  + ": " + Thread.currentThread().getName() + "]");		
						
						connectionReadWriteLock.writeLock().lock();
						ClientHandler clientHandler = new ClientHandler(socket);
						connections.add(clientHandler);
						System.out.println("[연결 개수: " + connections.size() + "]");
					} catch (Exception e) {
						if(!serverSocket.isClosed()) { stopService(); }
						break;
					} finally {
						connectionReadWriteLock.writeLock().unlock();
					}
				}
			}
		};
		
		executorService.submit(runnable);
	} //startServer 끝
	
	private void stopService() {
		try {
			
			/*Iterator<ClientHandler> iterator = connections.iterator();
			synchronized (connections) {
				while(iterator.hasNext()) {
					ClientHandler clientHandler = iterator.next();
					clientHandler.socket.close();
					iterator.remove();
				}
			}*/
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
			System.out.println("서버가 종료되었습니다.");
		} catch (Exception e) {} finally {
			connectionReadWriteLock.writeLock().unlock();
		}
		
	} // stopService 끝
	// 서버 메소드 끝
	
	private class ClientHandler {
		private Socket socket;
		private Member member;
		private Schedule schedule;
		private Integer[] seats;
		private Packet packet;
		BigInteger totalPrice;
		
		ClientHandler(Socket socket) {
			this.socket = socket;
			getPacket();
		}
		
		// 클라이언트 헨들러 메소드 섹션
		// 클라이언트로와 연결된 소켓, 연결된 클라이언트로 부터 Request Packet을 수신함
		private void getPacket() {
			Runnable runnable = new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					
					try {
						while(true) {
							DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
							ProtocolParser protocolParser = new ProtocolParser();
							
							packet = protocolParser.getObjectPacket(dataInputStream);
							
							if(packet.getMode() == Mode.SIGNIN.getCode()) { // 로그인
								
								MemberRepository memberRepository = new MemberRepository();
								member = memberRepository.findByIdAndPassword(((Member)packet.getData()));
								
								sendPacket(Mode.SIGNIN.getCode(), member);
								
							} else if(packet.getMode() == Mode.SIGNUP.getCode()) { // 회원가입
								
								MemberRepository memberRepository = new MemberRepository();
								int isSignedUp = memberRepository.save(((Member)packet.getData()));
								if(isSignedUp > 0) {
									sendPacket(Mode.SIGNUP.getCode(), isSignedUp);
								} else {
									sendPacket(Mode.SIGNUP.getCode(), "");
								}
								
							} else if(packet.getMode() == Mode.BOOK.getCode()) { // 예매하기
								
								BookRepository bookRepository = new BookRepository();
								List<Movie> movies = bookRepository.findScheduledMovieList();
								
								sendPacket(Mode.BOOK.getCode(), movies);
								
							} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// 영화선택

								BookRepository bookRepository = new BookRepository();
								
								List<Map<String, String>> resultList = bookRepository.findMovieSchedule(Integer.parseInt(String.valueOf(packet.getData())));
								
								sendPacket(Mode.MOVIE.getCode(), resultList);
								
							} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // 일정 보기
								
								BookRepository bookRepository = new BookRepository();
								
								int scheduleId = Integer.parseInt(((Map<String, String>)packet.getData()).get("scheduleId"));
								
								schedule = bookRepository.findSchedule(scheduleId);
								List<Map<String, String>> resultList = bookRepository.findSeats(schedule.getTheaterId(), scheduleId);
								
								sendPacket(Mode.SCHEDULE.getCode(), resultList);
								
							} else if(packet.getMode() == Mode.SEAT.getCode()) { // 좌석 선택
								
								BookRepository bookRepository = new BookRepository();
								seats = (Integer[]) packet.getData();
								for(int seat : seats) System.out.print(seat + " ");
								totalPrice = bookRepository.getTicketPrice(Rank.NORMAL.getCode()).multiply(BigInteger.valueOf(seats.length));
								
								sendPacket(Mode.SEAT.getCode(), totalPrice);
								
							} else if(packet.getMode() == Mode.PAY.getCode()) { // 결제
								BookRepository bookRepository = new BookRepository();
								
								if(totalPrice.compareTo(((BigInteger)packet.getData())) == 0) {
	
									int orderId = bookRepository.save(member.getMemberId(), schedule, totalPrice, seats);
									if(orderId > 0) { // transaction이 성공
										System.out.println(member.getMemberName() + "'s test: " + orderId);
										List<Map<String, String>> resultList = bookRepository.findOrders(orderId);
										FileBuilder fileBuilder = new FileBuilder();
										String fullPath = fileBuilder.createFile(resultList);
										byte[] byteFile = fileBuilder.convertToBytes(fullPath);
										sendPacket(Mode.PAY.getCode(), new String(byteFile));
									}
									
								} else {
									sendPacket(Mode.PAY.getCode(), "");
								}
								
							} else if(packet.getMode() == Mode.CANCEL.getCode()) { // 예약 취소
								
								BookRepository bookRepository = new BookRepository();
								int orderId = Integer.parseInt(String.valueOf(packet.getData()));
								int memberId =bookRepository.findMembersByOrderId(orderId);
								
								if( (memberId > 0) && (memberId == member.getMemberId()) ) {
									List<Map<String, String>> resultList = bookRepository.findOrders(orderId);
									if(bookRepository.cancel(resultList)) {
										sendPacket(Mode.CANCEL.getCode(), "1");
									} else {
										sendPacket(Mode.CANCEL.getCode(), "");
									}
								} else {
									sendPacket(Mode.CANCEL.getCode(), "");
								}
								
							} else if(packet.getMode() == Mode.CHECK.getCode()) { // 공지사항
								
								BookRepository bookRepository = new BookRepository();
								List<Map<String, String>> resultList = bookRepository.findCurrentOrders(member.getMemberId());
								if(resultList.size() > 0) {
									sendPacket(Mode.CHECK.getCode(), resultList);
								} else {
									sendPacket(Mode.CHECK.getCode(), "");
								}
								
							} else if(packet.getMode() == Mode.BROADCAST.getCode()) { // 공지사항
								
								broadcast(Mode.BROADCAST.getCode(), String.valueOf(packet.getData()));
								
							} else {
								
							}
						}
						
					} catch (SocketException se) {
						
						try {
							//se.printStackTrace();
							connectionReadWriteLock.writeLock().lock();
							connections.remove(ClientHandler.this);
							System.out.println("[처리 쓰레드 : " + Thread.currentThread().getName() + "] 클라이언트 '" + socket.getRemoteSocketAddress() + "'와 연결이 끊겼습니다.");
							socket.close();
						} catch (IOException e2) {} finally {
							connectionReadWriteLock.writeLock().unlock();
						}
						
					} catch (NumberFormatException | NullPointerException | SQLException | IOException | ArrayIndexOutOfBoundsException ex) {
						
						ex.printStackTrace();
						
					} catch (Exception e) {
						try {
							e.printStackTrace();
							connectionReadWriteLock.writeLock().lock();
							connections.remove(ClientHandler.this);
							System.out.println("[처리 쓰레드 : " + Thread.currentThread().getName() + "] 클라이언트 '" + socket.getRemoteSocketAddress() + "'와 연결이 끊겼습니다.");
							socket.close();
						} catch (IOException e2) {} finally {
							connectionReadWriteLock.writeLock().unlock();
						}
					} finally {
						if(socket.isConnected()) {
							System.out.println("Hello.....................");
							sendPacket(packet.getMode(), "");
						}
					}
				}
			};
			executorService.submit(runnable);
		} // getRequest 끝

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
							//e.printStackTrace();
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
		} // sendResponse 끝
		
		private void broadcast(int mode, String message) {
			/*for(ClientHandler clientHandler : connections) {
				clientHandler.sendPacket(Mode.BROADCAST.getCode(), message);
				
			}*/
			connectionReadWriteLock.readLock().lock();
			try {
				for(ClientHandler clientHandler : connections) {
					clientHandler.sendPacket(Mode.BROADCAST.getCode(), message); 
				}
			} finally {
				connectionReadWriteLock.readLock().unlock();
			}
		}
		
	} // ClientHandler 끝
	
}
