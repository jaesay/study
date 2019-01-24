package protocol.support;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

import domain.Member;
import protocol.Packet;
import protocol.enums.Body;
import protocol.enums.Mode;

public class BodyValidator {
	
	private boolean validateDataNum(int validLengh, String[] data) {
		
		if( (data.length != validLengh) || (Arrays.stream(data).filter(String::isEmpty).collect(Collectors.toList()).size() > 0)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	private boolean validateIneger(String data) {
		try { 
	        Integer.parseInt(data); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
		
	    return true;
	}
	
	private boolean validateStringLength(int min, int max, String data) {
		if(data.length() < min && data.length() > max) {
			return false;
		} else {
			return true;
		}
	}
	

	public boolean validate(Packet packet) {
		
		System.out.println("BodyValidator..................");
		System.out.println("1. " + String.valueOf(packet.getData()));
		
		String[] data = String.valueOf(packet.getData()).split(",");
		System.out.println("2. " + Arrays.toString(data));
		System.out.println("3. " + data.length);
		
		
		if(packet.getMode() == Mode.SIGNIN.getCode()) {			// 로그인
			
			if(validateDataNum(2, data) && validateStringLength(3, 20, data[0].trim()) && validateStringLength(3, 20, data[1].trim())) {
				Member member = new Member();
				member.setMemberName(data[0].trim());
				member.setPassword(data[1].trim());
				packet.setData(member);
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SIGNUP.getCode()) {	// 회원가입
		
			if(validateDataNum(3, data) && validateStringLength(3, 20, data[0].trim()) && validateStringLength(3, 20, data[1].trim()) && validateStringLength(1, 30, data[2].trim())) {
				Member member = new Member();
				member.setMemberName(data[0].trim());
				member.setPassword(data[1].trim());
				member.setName(data[2].trim());
				packet.setData(member);
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.BOOK.getCode()) {	// 예약하기
			//
		} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// 영화선택
			
			if(validateDataNum(1, data) && validateIneger(data[0].trim())) {	// PK가 숫자인지 체크
				packet.setData(data[0].trim());
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // 일정
			
			if(validateDataNum(1, data) && validateIneger(data[0].trim())) {
				packet.setData(data[0].trim());
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SEAT.getCode()) { // 일정
			
			if(validateDataNum(2, data) && validateIneger(data[0].trim()) && validateIneger(data[1].trim())) {
				Integer seat = Integer.parseInt(data[0].trim());
				int people = Integer.parseInt(data[1].trim());
				
				Integer[] seats = new Integer[people];
				for(int i=0; i<people; i++) {
					seats[i] = seat++;
				}
				packet.setData(seats);
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
			
		} else if(packet.getMode() == Mode.PAY.getCode()) {	// 결제
			
			if(validateDataNum(1, data) && validateIneger(data[0].trim())) {
				System.out.println("ProtocolParser PAY.............................");
				
				packet.setData(new BigInteger(data[0].trim()));	
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.CANCEL.getCode()) {	// 취소
			
			if(validateDataNum(1, data) && validateIneger(data[0].trim())) {
				packet.setData(data[0].trim());	
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.CHECK.getCode()) {	// 예매 조회
			//
		}
		
		if(packet.getData() == Body.INVALID.getCode()) {
			return false;
		} else {
			return true;
		}
		
	}

}
