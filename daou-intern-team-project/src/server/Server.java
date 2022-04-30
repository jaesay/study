package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import config.DBCPConfig;
import domain.Member;
import domain.Movie;
import domain.Schedule;
import domain.enums.Rank;
import domain.enums.Role;
import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Mode;
import protocol.support.BodyValidator;
import protocol.support.ProtocolParser;
import server.persistence.BookRepository;
import server.persistence.MemberRepository;
import server.persistence.MovieRepository;
import server.persistence.ScheduleRepository;

/**
 * Client와 TCP/IP 통신을 통해 기본적인 영화 예매 기능을 처리해주는 Server를 구현한다.
 * 
 * @author 	김재성
 * @since	2019-01-07
 */
public class Server {
	
	/*server 자원 선언*/
	private ExecutorService executorService;	// Thread Pool 
	private ServerSocket serverSocket;			// Server Socket
	private List<ClientHandler> connections = new ArrayList<>();	// ClientHandler를 관리하기 위한 List
	private final ReadWriteLock connectionReadWriteLock = new ReentrantReadWriteLock();	// Readers-Writer Lock
	
	private static final int MAX_CONCURRENT_USERS = 3_000;
	
	/**
	 * startService method를 호출하여 서버를 시작하는 main method입니다.
	 * @param args Unused
	 * @return nothing
	 */
	public static void main(String[] args) {
		new Server().startService();
	}

	/**
	 *  네트워크 통신을 위한 기본적인 자원(Thread Pool, Database Connection Pool, Server Socket 등)을 생생하는 method입니다.
	 *  @return nothing
	 */
	private void startService() {
		System.out.println("쓰레드풀을 생성합니다.");
		executorService = new ThreadPoolExecutor(	// 과도한 thread 생성을 막기 위해 thread pool 생성
				3000,		// core thread 개수
				5000,		// 최대 thread 개수
				150L,		// 놀고 있는 시간
				TimeUnit.SECONDS,	// 놀고 있는 시간 단위
				new SynchronousQueue<Runnable>()	// 작업 큐
		);
		
		/*Singleton pattern으로 Database Connection Pool 생성*/
		DBCPConfig dbcpConfig = DBCPConfig.getDataSource();
		Connection connection = dbcpConfig.getConnection();
		DBCPConfig.getDataSource().freeConnection(connection);

		try {
			serverSocket = new ServerSocket();		// server socket 생성
			serverSocket.bind(new InetSocketAddress("localhost", 18080));	// server socket의 ip/port를 지정
		} catch(Exception e) {
			if(!serverSocket.isClosed()) { stopService(); }
			return;
		}
		
		/*Runnable interface를 구현하여 thread Pool의 작업 큐에서 할 작업을 생성*/
		Runnable runnable = new Runnable() {
			@Override
			public void run() {	
				System.out.println("서버가 시작되었습니다.");
				while(true) {
					try {
						Socket socket = serverSocket.accept();	// client의 요청이 올 때까지 무한정 기다리다가(Synchronous Blocking method) 요청이 들어오면 클라이언트와 통신하는 방법을 아는, 즉 클라이언트의 ip/port를 아는 소켓을 리턴함
						System.out.println("[연결 수락: " + socket.getRemoteSocketAddress()  + ": " + Thread.currentThread().getName() + "]");		
						
						connectionReadWriteLock.writeLock().lock();		// 공유 자원인 connections을 사용하기 위해 write lock을 걸음
						if(connections.size() < MAX_CONCURRENT_USERS) {	// 현재 사용하고 있는 사용자의 응답성 향상을 위해 동시사용자 제한
							ClientHandler clientHandler = new ClientHandler(socket);	// 서버 내부에 ClientHandler를 통해 client와 통신
							connections.add(clientHandler);		// 서버는 여러명의 클라이언트와 통신하기 때문에 ClientHandler 관리의 편의성을 위해 connections에 저장
							System.out.println("[연결 개수: " + connections.size() + "]");
						} else {
							socket.close();
							System.out.println("[최대 연결 개수 초과, " + MAX_CONCURRENT_USERS);	// 최대 동시사용자를 초과하면 더이상 소켓을 받지 않음
						}
						
					} catch (Exception e) {
						if(!serverSocket.isClosed()) { stopService(); }
						break;
					} finally {
						connectionReadWriteLock.writeLock().unlock();
					}
				}
			}
		};
		
		executorService.submit(runnable); // Thread Pool의 작업 큐에 작업을 보냄
	} //startServer 끝
	
	
	/**
	 * 서버와 통신하는 모든 클라이언트와의 연결을 끊고 서버를 종료하는 method입니다.
	 * @return nothing
	 * @exception 소켓 관련 exception
	 */
	private void stopService() {
		try {
			
			connectionReadWriteLock.writeLock().lock();	// 공유자원인 connections에 쓰기 작업을 하기 위해 write lock을 걸음
			Iterator<ClientHandler> iterator = connections.iterator();
			
			/*Client와 통신하는 socket들을 모두 제거*/
			while(iterator.hasNext()) {
				ClientHandler clientHandler = iterator.next();
				clientHandler.socket.close();
				iterator.remove();
			}
			
			/*server socket 제거*/
			if(serverSocket!=null && !serverSocket.isClosed()) { 
				serverSocket.close(); 
			}
			
			/*thread pool 제거*/
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			}
			System.out.println("서버가 종료되었습니다.");
		} catch (Exception e) {} finally {
			connectionReadWriteLock.writeLock().unlock();
		}
		
	} // stopService 끝
	// 서버 메소드 끝
	
	/**
	 * Server Socket으로 부터 생성된 socket을 받아 Client와 통신하는 Class를 구현한다.
	 * @author 김재성
	 * @since 2019-01-07
	 */
	private class ClientHandler {
		
		/*통신 시 사용하는 자원 선언*/
		private Socket socket;
		private Member member;
		private Schedule schedule;
		private Integer[] seats;
		private Packet packet;
		private boolean stop = false;
		BigInteger totalPrice;
		
		/**
		 * ClientHandler의 instance가 만들어질 때 클라이언트와 통신하는 방법(ip/port)을 아는 socket을 전달받고 클라이언트로부터 요청을 기다린다. 
		 * @param socket
		 */
		ClientHandler(Socket socket) {
			this.socket = socket;
			getPacket();
		}
		

		/**
		 * 클라이언트로 부터 요청이 오기를 무한정 기다리다가 요청이 오면 그에 맞는 businesss logic을 수행시켜는 method입니다.
		 * @return nothing
		 * @exception 서버로 부터 발생하는 모든 exception
		 * @see SocketException, EOFException, NumberFormatException, NullPointerException, SQLException, IOException, ArrayIndexOutOfBoundsException, Exception 
		 */
		private void getPacket() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					
					//try {
						while(!stop) {
							try {
								
								DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());	// 클라이언트의 패킷을 받는 stream
								ProtocolParser protocolParser = new ProtocolParser();	// Header의 유효성 체크 및 클라이언트로부터 받은 패킷을 instance로 변환하기 위한 객체
								BodyValidator bodyValidator = new BodyValidator();	// Body의 Data 유효성 체크를 위한 객체
								
								packet = protocolParser.getObjectPacket(dataInputStream);	// Header의 유효성 체크 및 클라이언트로부터 받은 패킷을 instance로 변환
								
								if(packet.getVersion() > 0 && bodyValidator.validate(packet)) { // header와 body의 유효성을 통과하면
									System.out.println("2. 유효성 통과 패킷 =======");
									System.out.println(packet.toString());
									System.out.println("===================================\n");
									
									/*Mode에 해당하는 business logic을 수행*/
									if(packet.getMode() == Mode.SIGNIN.getCode()) { // 로그인
										
										MemberRepository memberRepository = new MemberRepository(); 
										member = memberRepository.findByIdAndPassword(((Member)packet.getData())); // 클라이언트로부터 받은 데이터베이스에 id, password에 해당하는 data가 있는지 확인
										
										if(member != null) { // data가 있으면
											sendPacket(Mode.SIGNIN.getCode(), member);	// 로그인 성공										
										} else {
											sendPacket(Mode.SIGNIN.getCode(), Body.INVALID.getCode());	//로그인 실패
										}
										
									} else if(packet.getMode() == Mode.SIGNUP.getCode()) { // 회원가입
										
										MemberRepository memberRepository = new MemberRepository();
										int isSignedUp = memberRepository.save(((Member)packet.getData()));	// 클라이언트로부터 받은 Data를 데이터베이스에 삽입
										if(isSignedUp > 0) {	// data가 데이터베이스에 저장되었다면
											sendPacket(Mode.SIGNUP.getCode(), Body.SUCCESS.getCode());	// 회원가입 성공
										} else {
											sendPacket(Mode.SIGNUP.getCode(), Body.INVALID.getCode());	// 회원가입 실패
										}
										
									} else if(packet.getMode() == Mode.BOOK.getCode()) { // 예매하기
										
										BookRepository bookRepository = new BookRepository();
										List<Movie> movies = bookRepository.findScheduledMovieList();	// 오늘 날짜부터 상영일정이 잡혀 있는 영화 목록을 받아옴
										
										sendPacket(Mode.BOOK.getCode(), movies);	// 클라이언트에게 영화 목록 전달
										
									} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// 영화선택
										
										BookRepository bookRepository = new BookRepository();
										
										List<Map<String, String>> resultList = bookRepository.findMovieSchedule(Integer.parseInt(String.valueOf(packet.getData())));	// 선택한 영화에 대한 오늘날짜부터 상영일정들을 전달
										
										sendPacket(Mode.MOVIE.getCode(), resultList);	// 클라이언트에게 상영 일정 전달
										
									} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // 일정 선택
										
										BookRepository bookRepository = new BookRepository();
										
										int scheduleId = Integer.parseInt(String.valueOf(packet.getData()));	// 클라이언트 패킷으로부터 상영일정 PK를 받음
										
										schedule = bookRepository.findSchedule(scheduleId);	// 다음 진행에 대한 요청에서 사용하기 위해 상영일정 초기화
										List<Map<String, String>> resultList = bookRepository.findSeats(schedule.getTheaterId(), scheduleId);	// 상영일정에 대한 좌석 정보를 넘겨줌
										
										sendPacket(Mode.SCHEDULE.getCode(), resultList);	// 클라이언트에게 좌석 정보 전달
										
									} else if(packet.getMode() == Mode.SEAT.getCode()) { // 좌석 선택
										
										BookRepository bookRepository = new BookRepository();
										seats = (Integer[]) packet.getData();	// 클라이언트가 선택한 좌석들

										totalPrice = bookRepository.getTicketPrice(Rank.NORMAL.getCode()).multiply(BigInteger.valueOf(seats.length));	// 주문 가격 계산
										
										sendPacket(Mode.SEAT.getCode(), totalPrice);	// 클라이언트에게 티켓(주문) 가격 정보 전달
										
									} else if(packet.getMode() == Mode.PAY.getCode()) { // 결제
										
										BookRepository bookRepository = new BookRepository();
										
										if(totalPrice.compareTo(((BigInteger)packet.getData())) == 0) {	// 클라이언트가 주문 가격과 같은 돈을 지불했다면
											
											int orderId = bookRepository.save(member.getMemberId(), schedule, totalPrice, seats);	// 데이터베이스에 해당 주문 데이터 저장
											if(orderId > 0) { // transaction이 성공, 데이터가 성공적으로 저장되었다면
												List<Map<String, String>> resultList = bookRepository.findOrders(orderId);	// 현재 주문에 대한 정보를 가져오고
												/*티켓을 생성*/
												FileBuilder fileBuilder = new FileBuilder();
												String fullPath = fileBuilder.createFile(resultList);
												byte[] byteFile = fileBuilder.convertToBytes(fullPath);
												sendPacket(Mode.PAY.getCode(), new String(byteFile));	// 티켓 파일을 클라이언트에게 전달
												fileBuilder.deleteFile(fullPath);
											} else {	// transaction 실패, 데이터 저장이 실패됐다면
												sendPacket(Mode.PAY.getCode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구	
											}
											
										} else {	// 클라이언트가 주문 가격과 다른 돈을 지불했다면
											sendPacket(Mode.PAY.getCode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구
										}
										
									} else if(packet.getMode() == Mode.CANCEL.getCode()) { // 예약 취소
										
										BookRepository bookRepository = new BookRepository();
										int orderId = Integer.parseInt(String.valueOf(packet.getData()));
										int memberId =bookRepository.findMembersByOrderId(orderId);
										
										if( (memberId > 0) && (memberId == member.getMemberId()) ) {
											List<Map<String, String>> resultList = bookRepository.findOrders(orderId);
											if(resultList.size() > 0 && bookRepository.cancel(resultList)) {	// 영화가 상영을 안했고 취소할 주문이 요청한 사람의 주문이면
												sendPacket(Mode.CANCEL.getCode(), Body.SUCCESS.getCode());
											} else {
												sendPacket(Mode.CANCEL.getCode(), Body.ERROR.getCode());
											}
										} else {
											sendPacket(Mode.CANCEL.getCode(), Body.INVALID.getCode());
										}
										
									} else if(packet.getMode() == Mode.CHECK.getCode()) { // 현재 티켓
										
										BookRepository bookRepository = new BookRepository();
										List<Map<String, String>> resultList = bookRepository.findCurrentOrders(member.getMemberId());	// 로그인된 사용자가 가지고 있는 상영일정이 지나지 않은 주문(티켓) 정보
										sendPacket(Mode.CHECK.getCode(), resultList);	// 클라이언트에게 아직 유효한 티켓 정보 전달
										
									} else if(packet.getMode() == Mode.BROADCAST.getCode()) { // 공지사항
										
										if(member.getRole().equalsIgnoreCase(Role.ADMIN.name()) || member.getRole().equalsIgnoreCase(Role.MANAGER.name())) {	// 권한이 관리자와 매니저라면
											broadcast(Mode.BROADCAST.getCode(), String.valueOf(packet.getData()));	// 모든 클라이언트에게 공지사항 전달
										} else {	// 권한이 없는 사용자가 공지사항 요청을 사용한다면
											throw new Exception(); // 클라이언트 변조를 통해 Admin 권한을 사용하려는 악성 사용자이기 때문에 바로 연결을 끊음
										}
										
									} else if(packet.getMode() == Mode.SIGNOUT.getCode()) {	// 로그아웃
										
										/*로그인 시 사용했던 자원들 제거*/
										member = new Member();
										schedule = new Schedule();
										seats = null;
										packet = new Packet();
										totalPrice = BigInteger.ZERO;
										sendPacket(Mode.SIGNOUT.getCode(), Body.SUCCESS.getCode());	// 클라이언트에게 로그아웃 성공했다고 전달
										
									} else if(packet.getMode() == Mode.DELETE_ACCOUNT.getCode()) {	// 회원 탈퇴
										
										if(member.getPassword().equals(packet.getData())) {	// 현재 로그인한 사람의 비밀번호와 보내진 비밀번호가 같은지 확인
											
											MemberRepository memberRepository = new MemberRepository();	
											
											if(memberRepository.delete(member.getMemberId()) > 0) {	// 삭제(enabled를 0로 변환)가 성공적으로 됐다면
												sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.SUCCESS.getCode());	// 클라이언트에게 삭제 성공했다고 패킷 전달
											} else {	// 삭제가 실패했다면
												sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구
											}
											
										} else {	// 비밀번호를 잘못 입력했다면
											sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구
										}
										
									} else if (packet.getMode() == Mode.POST_MOVIE.getCode()) {		// 영화 포스팅
										
										if(member.getRole().equalsIgnoreCase(Role.ADMIN.name()) || member.getRole().equalsIgnoreCase(Role.MANAGER.name())) { // 관리자나 매니저 권한을 가졌다면
											
											MovieRepository managerRepository = new MovieRepository();
											int isPosted = managerRepository.save((Movie)packet.getData());	// 클라이언트로 부터 전달받은 데이터를 데이터베이스에 저장
											
											if(isPosted > 0) {	// 영화 데이터가 성공적으로 저장되었다면
												sendPacket(Mode.POST_MOVIE.getCode(), Body.SUCCESS.getCode());	// 클라이언트에게 영화가 성공적으로 저장됐다고 패킷 전달
											} else {	// 영화 데이터 저장이 실패됐다면
												sendPacket(Mode.POST_MOVIE.getCode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구
											}
											
										} else {
											
											throw new Exception(); // 클라이언트 변조를 통해 Admin 권한을 사용하려는 악성 사용자이기 때문에 바로 연결을 끊음
											
										}
										
									} else if (packet.getMode() == Mode.THEATER_SCHEDULE.getCode()) {		// 상영관 일정 보기
										
										ScheduleRepository scheduleRepository = new ScheduleRepository();
										
										List<Schedule> resultList = scheduleRepository.findScheduleByTheaterId(Integer.parseInt(String.valueOf(packet.getData())));	// 오늘 날짜부터 상영관 일정 정보
										
										sendPacket(Mode.THEATER_SCHEDULE.getCode(), resultList);	// 해당 상영관의 상영일정들을 클라이언트에게 전달
										
										
									} else if(packet.getMode() == Mode.POST_SCHEDULE.getCode()) {		// 영화 일정 포스팅
										
										ScheduleRepository scheduleRepository = new ScheduleRepository();

										schedule = (Schedule) packet.getData();	// 클라이언트로부터 저장하길 원하는 상영일정 정보를 받음
										
										/*저장하고 싶은 상영일정 정보가 기존의 상영 일정과 겹치지는지 체크*/
										boolean isOverlaped = scheduleRepository.getTheaterDateSchedules(schedule.getTheaterId(), schedule.getReservedDate())
												.stream().anyMatch(x -> x.overlap(schedule));
										
										if(!isOverlaped) {	// 겹치지 않고
											
											if(scheduleRepository.save(schedule) > 0) { // 상영일정이 성공적으로 저장됐다면
												sendPacket(Mode.POST_SCHEDULE.getCode(), Body.SUCCESS.getCode());	// 클라이언트에게 상영일정이 성공적으로 저장됐다는 패킷을 보냄
											} else {	// 저장이 실패했다면
												sendPacket(Mode.POST_SCHEDULE.getCode(), Body.INVALID.getCode());	// 재요청 요구
											}
											
										} else {	// 기존의 상영일정과 겹친다면
											sendPacket(Mode.POST_SCHEDULE.getCode(), Body.INVALID.getCode());	// 재요청 요구
										}
										
									} else if(packet.getMode() == Mode.CLIENT_LIST.getCode()) {	// 서버에 접속한 클라이언트 목록
										
										connectionReadWriteLock.readLock().lock();	// Read lock을 걸음, Write Lock이 안걸려있다면
										try {
											
											List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
											
											/*현재 요청한 사람(관리자)을 제외하고 현재 접속된 클라이언트의 정보를 담은 map을 초기화*/ 
											for(ClientHandler clientHandler : connections) {	
												if(!clientHandler.equals(ClientHandler.this)) {	
													
													Map<String, String> clientInfo = new HashMap<String, String>();
													clientInfo.put("ip:port", clientHandler.socket.getRemoteSocketAddress().toString());	// ip:port 정보
													clientInfo.put("memberId", (clientHandler.member != null) ? String.valueOf(clientHandler.member.getMemberId()) : "");	// 로그인이 되어있다면 member PK 정보
													clientInfo.put("memberName", (clientHandler.member != null) ? clientHandler.member.getMemberName() : "");	// 로그인이 되어있다면 id 정보
													
													resultList.add(clientInfo);
												}
											}
											
											sendPacket(Mode.CLIENT_LIST.getCode(), resultList);	// 클라이언트(관리자)에게 접속된 사용자 정보를 전달 
											
										} finally {
											connectionReadWriteLock.readLock().unlock();
										}
										
									} else if(packet.getMode() == Mode.CLOSE_SOCEKT.getCode()) {	// 클라이언트 끊기
										
										connectionReadWriteLock.writeLock().lock();	// write lock을 걸음
										try {
											
											/*사용자로 부터 받은 데이터와 같은 ip:port, member PK,또는 아이디를 가진 ClientHandler가 있는지 찾음*/ 
											Optional<ClientHandler> target = connections.stream().parallel().filter(clientHandler -> 
											String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.socket.getRemoteSocketAddress())) || //
											(clientHandler.member != null && (String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.member.getMemberId())) ||	//
													String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.member.getMemberName())))
													)).findFirst();
											
											if(target.isPresent()) {	// 그러한 사용자가 있다면
												/*통신을 끊음*/
												connections.remove(target.get());	
												try {
													target.get().socket.close();
													sendPacket(Mode.CLOSE_SOCEKT.getCode(), Body.SUCCESS.getCode());	// 성공적으로 클라이언트와 통신을 끊었다고 전달
												} catch (IOException e) {
													e.printStackTrace();
												}
											} else {	// 없다면
												sendPacket(Mode.CLOSE_SOCEKT.getCode(), Body.INVALID.getCode());	// 클라이언트에게 그러한 사용자가 없다고 전달
											}
											
										} finally {
											connectionReadWriteLock.writeLock().unlock();
										}
										
									} else {
										
										sendPacket(packet.getMode(), Body.INVALID.getCode());	// Mode 값이 잘못되었을때
										
									}
									
								} else {	// Header, Body 유효성 검사 실패
									
									System.out.println("유효성검사 실패!!!!! " + packet.getData());
									sendPacket(packet.getMode(), Body.INVALID.getCode());	// 클라이언트에게 재요청 요구
									
								}
								
							/*서버에서 발생하는 모든 예외 처리*/
							} catch (SocketException se) {
								try {
									stop = true;
									//sendPacket(packet.getMode(), Body.ERROR.getCode());
									se.printStackTrace();
									connectionReadWriteLock.writeLock().lock();
									connections.remove(ClientHandler.this);
									System.out.println("[처리 쓰레드 : " + Thread.currentThread().getName() + "] 클라이언트 '" + socket.getRemoteSocketAddress() + "'와 연결이 끊겼습니다.");
									socket.close();
								} catch (IOException e2) {} finally {
									connectionReadWriteLock.writeLock().unlock();
								}
								
							} catch (EOFException sube) {

								sendPacket(packet.getMode(), Body.INVALID.getCode());
								sube.printStackTrace();
								
							} catch (NumberFormatException | NullPointerException | SQLException | IOException | ArrayIndexOutOfBoundsException ex) {
								sendPacket(packet.getMode(), Body.INVALID.getCode());
								ex.printStackTrace();
								
							} catch (Exception e) {
								try {
									stop = true;
									sendPacket(packet.getMode(), Body.ERROR.getCode());
									e.printStackTrace();
									connectionReadWriteLock.writeLock().lock();
									connections.remove(ClientHandler.this);
									System.out.println("[처리 쓰레드 : " + Thread.currentThread().getName() + "] 클라이언트 '" + socket.getRemoteSocketAddress() + "'와 연결이 끊겼습니다.");
									socket.close();
								} catch (IOException e2) {} finally {
									connectionReadWriteLock.writeLock().unlock();
								}
							}
							
						}
					
				}

			};
			executorService.submit(runnable);
		} // getRequest 끝

		/**
		 * 클라이언트에게 요청에 대한 응답으로 Packet을 전달하는 method입니다.
		 * @param mode
		 * @param data
		 * @exception IO와 관련된 Exception, protocol parser를 통해 전달받은 exception
		 * @see UnsupportedEncodingException, IOException, Exception
		 */
		private void sendPacket(int mode, Object data) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());	// 클라이언트에게 패킷을 전달하기 위한 stream
						ProtocolParser protocolParser = new ProtocolParser();	// 바이트 패킷을 만들기 위한 객체
						
						byte[] packet = protocolParser.getBytePacket(mode, data);	// 바이트 패킷 생성
						
						dataOutputStream.write(packet);	// 클라이언트에게 전달
						
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
		} // sendResponse 끝
		
		/**
		 * 모든 클라이언트에게 message를 전송하는 method입니다.
		 * @param mode
		 * @param message
		 */
		private void broadcast(int mode, String message) {

			connectionReadWriteLock.readLock().lock();
			try {				
				connections.stream().parallel().forEach(clientHandler -> clientHandler.sendPacket(Mode.BROADCAST.getCode(), message));
			} finally {
				connectionReadWriteLock.readLock().unlock();
			}
			
		}
		
	} // ClientHandler 끝
	
}
