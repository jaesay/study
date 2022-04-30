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
 * Client�� TCP/IP ����� ���� �⺻���� ��ȭ ���� ����� ó�����ִ� Server�� �����Ѵ�.
 * 
 * @author 	���缺
 * @since	2019-01-07
 */
public class Server {
	
	/*server �ڿ� ����*/
	private ExecutorService executorService;	// Thread Pool 
	private ServerSocket serverSocket;			// Server Socket
	private List<ClientHandler> connections = new ArrayList<>();	// ClientHandler�� �����ϱ� ���� List
	private final ReadWriteLock connectionReadWriteLock = new ReentrantReadWriteLock();	// Readers-Writer Lock
	
	private static final int MAX_CONCURRENT_USERS = 3_000;
	
	/**
	 * startService method�� ȣ���Ͽ� ������ �����ϴ� main method�Դϴ�.
	 * @param args Unused
	 * @return nothing
	 */
	public static void main(String[] args) {
		new Server().startService();
	}

	/**
	 *  ��Ʈ��ũ ����� ���� �⺻���� �ڿ�(Thread Pool, Database Connection Pool, Server Socket ��)�� �����ϴ� method�Դϴ�.
	 *  @return nothing
	 */
	private void startService() {
		System.out.println("������Ǯ�� �����մϴ�.");
		executorService = new ThreadPoolExecutor(	// ������ thread ������ ���� ���� thread pool ����
				3000,		// core thread ����
				5000,		// �ִ� thread ����
				150L,		// ��� �ִ� �ð�
				TimeUnit.SECONDS,	// ��� �ִ� �ð� ����
				new SynchronousQueue<Runnable>()	// �۾� ť
		);
		
		/*Singleton pattern���� Database Connection Pool ����*/
		DBCPConfig dbcpConfig = DBCPConfig.getDataSource();
		Connection connection = dbcpConfig.getConnection();
		DBCPConfig.getDataSource().freeConnection(connection);

		try {
			serverSocket = new ServerSocket();		// server socket ����
			serverSocket.bind(new InetSocketAddress("localhost", 18080));	// server socket�� ip/port�� ����
		} catch(Exception e) {
			if(!serverSocket.isClosed()) { stopService(); }
			return;
		}
		
		/*Runnable interface�� �����Ͽ� thread Pool�� �۾� ť���� �� �۾��� ����*/
		Runnable runnable = new Runnable() {
			@Override
			public void run() {	
				System.out.println("������ ���۵Ǿ����ϴ�.");
				while(true) {
					try {
						Socket socket = serverSocket.accept();	// client�� ��û�� �� ������ ������ ��ٸ��ٰ�(Synchronous Blocking method) ��û�� ������ Ŭ���̾�Ʈ�� ����ϴ� ����� �ƴ�, �� Ŭ���̾�Ʈ�� ip/port�� �ƴ� ������ ������
						System.out.println("[���� ����: " + socket.getRemoteSocketAddress()  + ": " + Thread.currentThread().getName() + "]");		
						
						connectionReadWriteLock.writeLock().lock();		// ���� �ڿ��� connections�� ����ϱ� ���� write lock�� ����
						if(connections.size() < MAX_CONCURRENT_USERS) {	// ���� ����ϰ� �ִ� ������� ���伺 ����� ���� ���û���� ����
							ClientHandler clientHandler = new ClientHandler(socket);	// ���� ���ο� ClientHandler�� ���� client�� ���
							connections.add(clientHandler);		// ������ �������� Ŭ���̾�Ʈ�� ����ϱ� ������ ClientHandler ������ ���Ǽ��� ���� connections�� ����
							System.out.println("[���� ����: " + connections.size() + "]");
						} else {
							socket.close();
							System.out.println("[�ִ� ���� ���� �ʰ�, " + MAX_CONCURRENT_USERS);	// �ִ� ���û���ڸ� �ʰ��ϸ� ���̻� ������ ���� ����
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
		
		executorService.submit(runnable); // Thread Pool�� �۾� ť�� �۾��� ����
	} //startServer ��
	
	
	/**
	 * ������ ����ϴ� ��� Ŭ���̾�Ʈ���� ������ ���� ������ �����ϴ� method�Դϴ�.
	 * @return nothing
	 * @exception ���� ���� exception
	 */
	private void stopService() {
		try {
			
			connectionReadWriteLock.writeLock().lock();	// �����ڿ��� connections�� ���� �۾��� �ϱ� ���� write lock�� ����
			Iterator<ClientHandler> iterator = connections.iterator();
			
			/*Client�� ����ϴ� socket���� ��� ����*/
			while(iterator.hasNext()) {
				ClientHandler clientHandler = iterator.next();
				clientHandler.socket.close();
				iterator.remove();
			}
			
			/*server socket ����*/
			if(serverSocket!=null && !serverSocket.isClosed()) { 
				serverSocket.close(); 
			}
			
			/*thread pool ����*/
			if(executorService!=null && !executorService.isShutdown()) { 
				executorService.shutdown(); 
			}
			System.out.println("������ ����Ǿ����ϴ�.");
		} catch (Exception e) {} finally {
			connectionReadWriteLock.writeLock().unlock();
		}
		
	} // stopService ��
	// ���� �޼ҵ� ��
	
	/**
	 * Server Socket���� ���� ������ socket�� �޾� Client�� ����ϴ� Class�� �����Ѵ�.
	 * @author ���缺
	 * @since 2019-01-07
	 */
	private class ClientHandler {
		
		/*��� �� ����ϴ� �ڿ� ����*/
		private Socket socket;
		private Member member;
		private Schedule schedule;
		private Integer[] seats;
		private Packet packet;
		private boolean stop = false;
		BigInteger totalPrice;
		
		/**
		 * ClientHandler�� instance�� ������� �� Ŭ���̾�Ʈ�� ����ϴ� ���(ip/port)�� �ƴ� socket�� ���޹ް� Ŭ���̾�Ʈ�κ��� ��û�� ��ٸ���. 
		 * @param socket
		 */
		ClientHandler(Socket socket) {
			this.socket = socket;
			getPacket();
		}
		

		/**
		 * Ŭ���̾�Ʈ�� ���� ��û�� ���⸦ ������ ��ٸ��ٰ� ��û�� ���� �׿� �´� businesss logic�� ������Ѵ� method�Դϴ�.
		 * @return nothing
		 * @exception ������ ���� �߻��ϴ� ��� exception
		 * @see SocketException, EOFException, NumberFormatException, NullPointerException, SQLException, IOException, ArrayIndexOutOfBoundsException, Exception 
		 */
		private void getPacket() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					
					//try {
						while(!stop) {
							try {
								
								DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());	// Ŭ���̾�Ʈ�� ��Ŷ�� �޴� stream
								ProtocolParser protocolParser = new ProtocolParser();	// Header�� ��ȿ�� üũ �� Ŭ���̾�Ʈ�κ��� ���� ��Ŷ�� instance�� ��ȯ�ϱ� ���� ��ü
								BodyValidator bodyValidator = new BodyValidator();	// Body�� Data ��ȿ�� üũ�� ���� ��ü
								
								packet = protocolParser.getObjectPacket(dataInputStream);	// Header�� ��ȿ�� üũ �� Ŭ���̾�Ʈ�κ��� ���� ��Ŷ�� instance�� ��ȯ
								
								if(packet.getVersion() > 0 && bodyValidator.validate(packet)) { // header�� body�� ��ȿ���� ����ϸ�
									System.out.println("2. ��ȿ�� ��� ��Ŷ =======");
									System.out.println(packet.toString());
									System.out.println("===================================\n");
									
									/*Mode�� �ش��ϴ� business logic�� ����*/
									if(packet.getMode() == Mode.SIGNIN.getCode()) { // �α���
										
										MemberRepository memberRepository = new MemberRepository(); 
										member = memberRepository.findByIdAndPassword(((Member)packet.getData())); // Ŭ���̾�Ʈ�κ��� ���� �����ͺ��̽��� id, password�� �ش��ϴ� data�� �ִ��� Ȯ��
										
										if(member != null) { // data�� ������
											sendPacket(Mode.SIGNIN.getCode(), member);	// �α��� ����										
										} else {
											sendPacket(Mode.SIGNIN.getCode(), Body.INVALID.getCode());	//�α��� ����
										}
										
									} else if(packet.getMode() == Mode.SIGNUP.getCode()) { // ȸ������
										
										MemberRepository memberRepository = new MemberRepository();
										int isSignedUp = memberRepository.save(((Member)packet.getData()));	// Ŭ���̾�Ʈ�κ��� ���� Data�� �����ͺ��̽��� ����
										if(isSignedUp > 0) {	// data�� �����ͺ��̽��� ����Ǿ��ٸ�
											sendPacket(Mode.SIGNUP.getCode(), Body.SUCCESS.getCode());	// ȸ������ ����
										} else {
											sendPacket(Mode.SIGNUP.getCode(), Body.INVALID.getCode());	// ȸ������ ����
										}
										
									} else if(packet.getMode() == Mode.BOOK.getCode()) { // �����ϱ�
										
										BookRepository bookRepository = new BookRepository();
										List<Movie> movies = bookRepository.findScheduledMovieList();	// ���� ��¥���� �������� ���� �ִ� ��ȭ ����� �޾ƿ�
										
										sendPacket(Mode.BOOK.getCode(), movies);	// Ŭ���̾�Ʈ���� ��ȭ ��� ����
										
									} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// ��ȭ����
										
										BookRepository bookRepository = new BookRepository();
										
										List<Map<String, String>> resultList = bookRepository.findMovieSchedule(Integer.parseInt(String.valueOf(packet.getData())));	// ������ ��ȭ�� ���� ���ó�¥���� ���������� ����
										
										sendPacket(Mode.MOVIE.getCode(), resultList);	// Ŭ���̾�Ʈ���� �� ���� ����
										
									} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // ���� ����
										
										BookRepository bookRepository = new BookRepository();
										
										int scheduleId = Integer.parseInt(String.valueOf(packet.getData()));	// Ŭ���̾�Ʈ ��Ŷ���κ��� ������ PK�� ����
										
										schedule = bookRepository.findSchedule(scheduleId);	// ���� ���࿡ ���� ��û���� ����ϱ� ���� ������ �ʱ�ȭ
										List<Map<String, String>> resultList = bookRepository.findSeats(schedule.getTheaterId(), scheduleId);	// �������� ���� �¼� ������ �Ѱ���
										
										sendPacket(Mode.SCHEDULE.getCode(), resultList);	// Ŭ���̾�Ʈ���� �¼� ���� ����
										
									} else if(packet.getMode() == Mode.SEAT.getCode()) { // �¼� ����
										
										BookRepository bookRepository = new BookRepository();
										seats = (Integer[]) packet.getData();	// Ŭ���̾�Ʈ�� ������ �¼���

										totalPrice = bookRepository.getTicketPrice(Rank.NORMAL.getCode()).multiply(BigInteger.valueOf(seats.length));	// �ֹ� ���� ���
										
										sendPacket(Mode.SEAT.getCode(), totalPrice);	// Ŭ���̾�Ʈ���� Ƽ��(�ֹ�) ���� ���� ����
										
									} else if(packet.getMode() == Mode.PAY.getCode()) { // ����
										
										BookRepository bookRepository = new BookRepository();
										
										if(totalPrice.compareTo(((BigInteger)packet.getData())) == 0) {	// Ŭ���̾�Ʈ�� �ֹ� ���ݰ� ���� ���� �����ߴٸ�
											
											int orderId = bookRepository.save(member.getMemberId(), schedule, totalPrice, seats);	// �����ͺ��̽��� �ش� �ֹ� ������ ����
											if(orderId > 0) { // transaction�� ����, �����Ͱ� ���������� ����Ǿ��ٸ�
												List<Map<String, String>> resultList = bookRepository.findOrders(orderId);	// ���� �ֹ��� ���� ������ ��������
												/*Ƽ���� ����*/
												FileBuilder fileBuilder = new FileBuilder();
												String fullPath = fileBuilder.createFile(resultList);
												byte[] byteFile = fileBuilder.convertToBytes(fullPath);
												sendPacket(Mode.PAY.getCode(), new String(byteFile));	// Ƽ�� ������ Ŭ���̾�Ʈ���� ����
												fileBuilder.deleteFile(fullPath);
											} else {	// transaction ����, ������ ������ ���еƴٸ�
												sendPacket(Mode.PAY.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸	
											}
											
										} else {	// Ŭ���̾�Ʈ�� �ֹ� ���ݰ� �ٸ� ���� �����ߴٸ�
											sendPacket(Mode.PAY.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸
										}
										
									} else if(packet.getMode() == Mode.CANCEL.getCode()) { // ���� ���
										
										BookRepository bookRepository = new BookRepository();
										int orderId = Integer.parseInt(String.valueOf(packet.getData()));
										int memberId =bookRepository.findMembersByOrderId(orderId);
										
										if( (memberId > 0) && (memberId == member.getMemberId()) ) {
											List<Map<String, String>> resultList = bookRepository.findOrders(orderId);
											if(resultList.size() > 0 && bookRepository.cancel(resultList)) {	// ��ȭ�� ���� ���߰� ����� �ֹ��� ��û�� ����� �ֹ��̸�
												sendPacket(Mode.CANCEL.getCode(), Body.SUCCESS.getCode());
											} else {
												sendPacket(Mode.CANCEL.getCode(), Body.ERROR.getCode());
											}
										} else {
											sendPacket(Mode.CANCEL.getCode(), Body.INVALID.getCode());
										}
										
									} else if(packet.getMode() == Mode.CHECK.getCode()) { // ���� Ƽ��
										
										BookRepository bookRepository = new BookRepository();
										List<Map<String, String>> resultList = bookRepository.findCurrentOrders(member.getMemberId());	// �α��ε� ����ڰ� ������ �ִ� �������� ������ ���� �ֹ�(Ƽ��) ����
										sendPacket(Mode.CHECK.getCode(), resultList);	// Ŭ���̾�Ʈ���� ���� ��ȿ�� Ƽ�� ���� ����
										
									} else if(packet.getMode() == Mode.BROADCAST.getCode()) { // ��������
										
										if(member.getRole().equalsIgnoreCase(Role.ADMIN.name()) || member.getRole().equalsIgnoreCase(Role.MANAGER.name())) {	// ������ �����ڿ� �Ŵ������
											broadcast(Mode.BROADCAST.getCode(), String.valueOf(packet.getData()));	// ��� Ŭ���̾�Ʈ���� �������� ����
										} else {	// ������ ���� ����ڰ� �������� ��û�� ����Ѵٸ�
											throw new Exception(); // Ŭ���̾�Ʈ ������ ���� Admin ������ ����Ϸ��� �Ǽ� ������̱� ������ �ٷ� ������ ����
										}
										
									} else if(packet.getMode() == Mode.SIGNOUT.getCode()) {	// �α׾ƿ�
										
										/*�α��� �� ����ߴ� �ڿ��� ����*/
										member = new Member();
										schedule = new Schedule();
										seats = null;
										packet = new Packet();
										totalPrice = BigInteger.ZERO;
										sendPacket(Mode.SIGNOUT.getCode(), Body.SUCCESS.getCode());	// Ŭ���̾�Ʈ���� �α׾ƿ� �����ߴٰ� ����
										
									} else if(packet.getMode() == Mode.DELETE_ACCOUNT.getCode()) {	// ȸ�� Ż��
										
										if(member.getPassword().equals(packet.getData())) {	// ���� �α����� ����� ��й�ȣ�� ������ ��й�ȣ�� ������ Ȯ��
											
											MemberRepository memberRepository = new MemberRepository();	
											
											if(memberRepository.delete(member.getMemberId()) > 0) {	// ����(enabled�� 0�� ��ȯ)�� ���������� �ƴٸ�
												sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.SUCCESS.getCode());	// Ŭ���̾�Ʈ���� ���� �����ߴٰ� ��Ŷ ����
											} else {	// ������ �����ߴٸ�
												sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸
											}
											
										} else {	// ��й�ȣ�� �߸� �Է��ߴٸ�
											sendPacket(Mode.DELETE_ACCOUNT.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸
										}
										
									} else if (packet.getMode() == Mode.POST_MOVIE.getCode()) {		// ��ȭ ������
										
										if(member.getRole().equalsIgnoreCase(Role.ADMIN.name()) || member.getRole().equalsIgnoreCase(Role.MANAGER.name())) { // �����ڳ� �Ŵ��� ������ �����ٸ�
											
											MovieRepository managerRepository = new MovieRepository();
											int isPosted = managerRepository.save((Movie)packet.getData());	// Ŭ���̾�Ʈ�� ���� ���޹��� �����͸� �����ͺ��̽��� ����
											
											if(isPosted > 0) {	// ��ȭ �����Ͱ� ���������� ����Ǿ��ٸ�
												sendPacket(Mode.POST_MOVIE.getCode(), Body.SUCCESS.getCode());	// Ŭ���̾�Ʈ���� ��ȭ�� ���������� ����ƴٰ� ��Ŷ ����
											} else {	// ��ȭ ������ ������ ���еƴٸ�
												sendPacket(Mode.POST_MOVIE.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸
											}
											
										} else {
											
											throw new Exception(); // Ŭ���̾�Ʈ ������ ���� Admin ������ ����Ϸ��� �Ǽ� ������̱� ������ �ٷ� ������ ����
											
										}
										
									} else if (packet.getMode() == Mode.THEATER_SCHEDULE.getCode()) {		// �󿵰� ���� ����
										
										ScheduleRepository scheduleRepository = new ScheduleRepository();
										
										List<Schedule> resultList = scheduleRepository.findScheduleByTheaterId(Integer.parseInt(String.valueOf(packet.getData())));	// ���� ��¥���� �󿵰� ���� ����
										
										sendPacket(Mode.THEATER_SCHEDULE.getCode(), resultList);	// �ش� �󿵰��� ���������� Ŭ���̾�Ʈ���� ����
										
										
									} else if(packet.getMode() == Mode.POST_SCHEDULE.getCode()) {		// ��ȭ ���� ������
										
										ScheduleRepository scheduleRepository = new ScheduleRepository();

										schedule = (Schedule) packet.getData();	// Ŭ���̾�Ʈ�κ��� �����ϱ� ���ϴ� ������ ������ ����
										
										/*�����ϰ� ���� ������ ������ ������ �� ������ ��ġ������ üũ*/
										boolean isOverlaped = scheduleRepository.getTheaterDateSchedules(schedule.getTheaterId(), schedule.getReservedDate())
												.stream().anyMatch(x -> x.overlap(schedule));
										
										if(!isOverlaped) {	// ��ġ�� �ʰ�
											
											if(scheduleRepository.save(schedule) > 0) { // �������� ���������� ����ƴٸ�
												sendPacket(Mode.POST_SCHEDULE.getCode(), Body.SUCCESS.getCode());	// Ŭ���̾�Ʈ���� �������� ���������� ����ƴٴ� ��Ŷ�� ����
											} else {	// ������ �����ߴٸ�
												sendPacket(Mode.POST_SCHEDULE.getCode(), Body.INVALID.getCode());	// ���û �䱸
											}
											
										} else {	// ������ �������� ��ģ�ٸ�
											sendPacket(Mode.POST_SCHEDULE.getCode(), Body.INVALID.getCode());	// ���û �䱸
										}
										
									} else if(packet.getMode() == Mode.CLIENT_LIST.getCode()) {	// ������ ������ Ŭ���̾�Ʈ ���
										
										connectionReadWriteLock.readLock().lock();	// Read lock�� ����, Write Lock�� �Ȱɷ��ִٸ�
										try {
											
											List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
											
											/*���� ��û�� ���(������)�� �����ϰ� ���� ���ӵ� Ŭ���̾�Ʈ�� ������ ���� map�� �ʱ�ȭ*/ 
											for(ClientHandler clientHandler : connections) {	
												if(!clientHandler.equals(ClientHandler.this)) {	
													
													Map<String, String> clientInfo = new HashMap<String, String>();
													clientInfo.put("ip:port", clientHandler.socket.getRemoteSocketAddress().toString());	// ip:port ����
													clientInfo.put("memberId", (clientHandler.member != null) ? String.valueOf(clientHandler.member.getMemberId()) : "");	// �α����� �Ǿ��ִٸ� member PK ����
													clientInfo.put("memberName", (clientHandler.member != null) ? clientHandler.member.getMemberName() : "");	// �α����� �Ǿ��ִٸ� id ����
													
													resultList.add(clientInfo);
												}
											}
											
											sendPacket(Mode.CLIENT_LIST.getCode(), resultList);	// Ŭ���̾�Ʈ(������)���� ���ӵ� ����� ������ ���� 
											
										} finally {
											connectionReadWriteLock.readLock().unlock();
										}
										
									} else if(packet.getMode() == Mode.CLOSE_SOCEKT.getCode()) {	// Ŭ���̾�Ʈ ����
										
										connectionReadWriteLock.writeLock().lock();	// write lock�� ����
										try {
											
											/*����ڷ� ���� ���� �����Ϳ� ���� ip:port, member PK,�Ǵ� ���̵� ���� ClientHandler�� �ִ��� ã��*/ 
											Optional<ClientHandler> target = connections.stream().parallel().filter(clientHandler -> 
											String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.socket.getRemoteSocketAddress())) || //
											(clientHandler.member != null && (String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.member.getMemberId())) ||	//
													String.valueOf(packet.getData()).equals(String.valueOf(clientHandler.member.getMemberName())))
													)).findFirst();
											
											if(target.isPresent()) {	// �׷��� ����ڰ� �ִٸ�
												/*����� ����*/
												connections.remove(target.get());	
												try {
													target.get().socket.close();
													sendPacket(Mode.CLOSE_SOCEKT.getCode(), Body.SUCCESS.getCode());	// ���������� Ŭ���̾�Ʈ�� ����� �����ٰ� ����
												} catch (IOException e) {
													e.printStackTrace();
												}
											} else {	// ���ٸ�
												sendPacket(Mode.CLOSE_SOCEKT.getCode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� �׷��� ����ڰ� ���ٰ� ����
											}
											
										} finally {
											connectionReadWriteLock.writeLock().unlock();
										}
										
									} else {
										
										sendPacket(packet.getMode(), Body.INVALID.getCode());	// Mode ���� �߸��Ǿ�����
										
									}
									
								} else {	// Header, Body ��ȿ�� �˻� ����
									
									System.out.println("��ȿ���˻� ����!!!!! " + packet.getData());
									sendPacket(packet.getMode(), Body.INVALID.getCode());	// Ŭ���̾�Ʈ���� ���û �䱸
									
								}
								
							/*�������� �߻��ϴ� ��� ���� ó��*/
							} catch (SocketException se) {
								try {
									stop = true;
									//sendPacket(packet.getMode(), Body.ERROR.getCode());
									se.printStackTrace();
									connectionReadWriteLock.writeLock().lock();
									connections.remove(ClientHandler.this);
									System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
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
									System.out.println("[ó�� ������ : " + Thread.currentThread().getName() + "] Ŭ���̾�Ʈ '" + socket.getRemoteSocketAddress() + "'�� ������ ������ϴ�.");
									socket.close();
								} catch (IOException e2) {} finally {
									connectionReadWriteLock.writeLock().unlock();
								}
							}
							
						}
					
				}

			};
			executorService.submit(runnable);
		} // getRequest ��

		/**
		 * Ŭ���̾�Ʈ���� ��û�� ���� �������� Packet�� �����ϴ� method�Դϴ�.
		 * @param mode
		 * @param data
		 * @exception IO�� ���õ� Exception, protocol parser�� ���� ���޹��� exception
		 * @see UnsupportedEncodingException, IOException, Exception
		 */
		private void sendPacket(int mode, Object data) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());	// Ŭ���̾�Ʈ���� ��Ŷ�� �����ϱ� ���� stream
						ProtocolParser protocolParser = new ProtocolParser();	// ����Ʈ ��Ŷ�� ����� ���� ��ü
						
						byte[] packet = protocolParser.getBytePacket(mode, data);	// ����Ʈ ��Ŷ ����
						
						dataOutputStream.write(packet);	// Ŭ���̾�Ʈ���� ����
						
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
		
		/**
		 * ��� Ŭ���̾�Ʈ���� message�� �����ϴ� method�Դϴ�.
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
		
	} // ClientHandler ��
	
}
