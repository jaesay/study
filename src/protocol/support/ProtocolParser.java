package protocol.support;

import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import domain.Member;
import domain.Movie;
import domain.Schedule;
import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Header;
import protocol.enums.Mode;

public class ProtocolParser {
	
	private static final short VERSION = 1;
	private static final short BODY_MAX_SIZE = 1 * 1024;
	
	public Packet getObjectPacket(DataInputStream dataInputStream) throws Exception {
		Packet packet = new Packet();
		packet.setMode(dataInputStream.readInt());
		packet.setContentLength(dataInputStream.readInt());

		if(packet.getContentLength() > BODY_MAX_SIZE) {
			System.out.println("Content-Length의 값이 유효하지 않습니다. : " + "[Packet의 길이: " + packet.getContentLength() + "]");
			Packet errorPacket = new Packet();
			errorPacket.setMode(packet.getMode());
			errorPacket.setVersion((short) -1);
			return errorPacket;
		}			
		
		packet.setVersion(dataInputStream.readShort());
		
		if(packet.getVersion() != VERSION) {
			System.out.println("Protocol의 Version이 일치하지 않습니다. : " + "[서버 프로토콜 버전: " + VERSION + ", 클라이언트 프로토콜 버전: " + packet.getVersion() + "]");
			Packet errorPacket = new Packet();
			errorPacket.setMode(packet.getMode());
			errorPacket.setVersion((short) -1);
			return errorPacket;
		}

		byte[] byteData = new byte[packet.getContentLength()];
		dataInputStream.readFully(byteData, 0, packet.getContentLength());
		packet.setData(new String(byteData).trim());

		System.out.println("1. 클라이언트에서 받은 패킷 =======");
		System.out.println(packet.toString());
		System.out.println("===================================\n");
		
		return packet;
	}
	
	@SuppressWarnings("unchecked")
	public byte[] getBytePacket(int mode, Object data) throws UnsupportedEncodingException {

		Packet packet = new Packet();
		packet.setMode(mode);
		packet.setVersion(VERSION);
		
		if(data != null && !String.valueOf(data).isEmpty() && data != Body.INVALID.getCode()) {

			if(packet.getMode() == Mode.SIGNIN.getCode()) {		// 로그인

				packet.setData(new StringJoiner(",").add(String.valueOf(((Member)data).getMemberId()))
						.add(((Member)data).getMemberName())
						.add(((Member)data).getPassword())
						.add(((Member)data).getName())
						.add(((Member)data).getRole())
						.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((Member)data).getCreatedDate()))
						.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((Member)data).getUpdatedDate()))
						.toString());
				
			} else if(packet.getMode() == Mode.SIGNUP.getCode()) {	// 회원가입
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.BOOK.getCode()) {	// 예매하기
				
				StringJoiner hashStringJoiner = new StringJoiner("#");
				
				List<Movie> movies = (ArrayList<Movie>) data;
				for(Movie movie : movies) {
					StringJoiner commaStringJoiner = new StringJoiner(",");
					hashStringJoiner.add(commaStringJoiner.add(String.valueOf(movie.getMovieId()))
							.add(movie.getTitle())
							.add(movie.getDirector())
							.add(movie.getActor())
							.toString());
				}
				packet.setData(hashStringJoiner.toString());
				
			} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// 영화선택
				
				StringJoiner hashStringJoiner = new StringJoiner("#");
				
				List<Map<String,String>> resultList = (ArrayList<Map<String, String>>) data;
				
				for(Map<String, String> resultMap : resultList) {
					StringJoiner commaStringJoiner = new StringJoiner(",");
					hashStringJoiner.add(commaStringJoiner.add(resultMap.get("scheduleId"))
							.add(resultMap.get("scheduleDate"))
							.add(resultMap.get("theaterId"))
							.toString());
				}
				packet.setData(hashStringJoiner.toString());
				
			} else if(packet.getMode() == Mode.SCHEDULE.getCode()) {	// 일정
				
				StringJoiner hashStringJoiner = new StringJoiner("#");
				
				List<Map<String,String>> resultList = (ArrayList<Map<String, String>>) data;
				
				for(Map<String, String> resultMap : resultList) {
					StringJoiner commaStringJoiner = new StringJoiner(",");
					hashStringJoiner.add(commaStringJoiner.add(resultMap.get("seatId"))
							.add(resultMap.get("available")).toString());
				}
				packet.setData(hashStringJoiner.toString());
				
			} else if(packet.getMode() == Mode.SEAT.getCode()) {	// 좌석선택
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.PAY.getCode()) {	// 결제
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.CANCEL.getCode()) {	// 결제
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.CHECK.getCode()) {	// 조회
				
				List<Map<String,String>> resultList = (ArrayList<Map<String, String>>) data;

				if(resultList.size() > 0) {

					StringJoiner hashStringJoiner = new StringJoiner("#");

					for(Map<String, String> resultMap : resultList) {
						StringJoiner commaStringJoiner = new StringJoiner(",");
						hashStringJoiner.add(commaStringJoiner.add(resultMap.get("orderId"))
								.add(resultMap.get("totalPrice"))
								.add(resultMap.get("orderDate"))
								.add(resultMap.get("title"))
								.add(resultMap.get("scheduleDate"))
								.toString());
					}
					
					packet.setData(hashStringJoiner.toString());
					
				} else {
					packet.setData(Body.EMPTY.getCode());
				}
				
			} else if(packet.getMode() == Mode.BROADCAST.getCode()) {	// 공지사항
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.SIGNOUT.getCode()) {		// 로그아웃
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.DELETE_ACCOUNT.getCode()) {		// 회원 탈퇴
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.POST_MOVIE.getCode()) {		// 영화 포스팅
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.THEATER_SCHEDULE.getCode()) {
				
				List<Schedule> schedules = (ArrayList<Schedule>) data;
				if(schedules.size() > 0) {
					
					StringJoiner hashStringJoiner = new StringJoiner("#");
					
					for(Schedule schedule : schedules) {
						StringJoiner commaStringJoiner = new StringJoiner(",");
						hashStringJoiner.add(commaStringJoiner.add(String.valueOf(schedule.getReservedDate()))
								.add(String.valueOf(schedule.getStartTime()))
								.add(String.valueOf(schedule.getEndTime()))
								.toString());
					}
					packet.setData(hashStringJoiner.toString());

				} else {
					packet.setData(Body.EMPTY.getCode());
				}
				
			} else if(packet.getMode() == Mode.POST_SCHEDULE.getCode()) {		// 일정 포스팅
				
				packet.setData(data);
				
			} else if(packet.getMode() == Mode.CLIENT_LIST.getCode()) {
				
				List<Map<String, String>> clientList = (ArrayList<Map<String, String>>) data;
				
				if(clientList.size() > 0) {
					
					StringJoiner hashStringJoiner = new StringJoiner("#");
					
					for(Map<String, String> clientInfo : clientList) {
						StringJoiner commaStringJoiner = new StringJoiner(",");
						hashStringJoiner.add(commaStringJoiner.add(clientInfo.get("ip:port"))
								.add(clientInfo.get("memberId"))
								.add(clientInfo.get("memberName"))
								.toString());
					}
					packet.setData(hashStringJoiner.toString());

				} else {
					packet.setData(Body.EMPTY.getCode());
				}
				
			} else if(packet.getMode() == Mode.CLOSE_SOCEKT.getCode()) {
				packet.setData(data);
			}
			
		} else {
			packet.setData(data);
		}
		
		byte[] byteData = String.valueOf(packet.getData()).getBytes();
		int contentLength = byteData.length;
		packet.setContentLength(String.valueOf(packet.getData()).getBytes("EUC_KR").length);

		ByteBuffer headerByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength());
		headerByteBuffer.putInt(packet.getMode())
						.putInt(packet.getContentLength())
						.putShort(packet.getVersion());
		
		System.out.println("2. 서버에서 보내는 패킷 ==============");
		System.out.println(packet.toString());
		System.out.println("======================================\n");
		
		ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + contentLength);
		
		packetByteBuffer.put(headerByteBuffer.array()) //header
						.put(byteData); //body
		
		return packetByteBuffer.array();
	}
	
}
