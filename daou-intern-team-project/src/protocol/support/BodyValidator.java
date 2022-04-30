package protocol.support;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import domain.Member;
import domain.Movie;
import domain.Schedule;
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
	
	private boolean validateInteger(String data) {
		try {
	        int tmp = Integer.parseInt(data);
	        if(tmp < 0) {
	        	return false;
	        }
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
		
	    return true;
	}
	
	private boolean validateStringLength(int min, int max, String data) {
		if(data.length() < min || data.length() > max) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateDate(String date) {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
	        String result = localDate.format(dateTimeFormatter);
	        return result.equals(date);
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	private boolean validateTime(String time) {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(time, dateTimeFormatter);
            String result = localTime.format(dateTimeFormatter);
            return result.equals(time);
        } catch (DateTimeParseException e2) {
            return false;
        }
	}
	
	private boolean validateSeats(String startSeatIdStr, String peopleStr) {
		int startSeatId = Integer.parseInt(startSeatIdStr);
		int people = Integer.parseInt(peopleStr);
		
		if(startSeatId > 100 || (startSeatId + people - 1) > 100 || startSeatId < 0 || people > 5) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateStartEndtime(String strStartTime, String strEndTime) {
		
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(strStartTime, dateTimeFormatter);
            LocalTime endTime = LocalTime.parse(strEndTime, dateTimeFormatter);
			if(endTime.isAfter(startTime)) {
				return true;
			} else {
				return false;
			}
            
		} catch (DateTimeParseException e2) {
            return false;
        }
	}
	

	public boolean validate(Packet packet) {
		
		System.out.println("BodyValidator..................");
		System.out.println("1. " + String.valueOf(packet.getData()));
		
		String[] data = String.valueOf(packet.getData()).split(",");
		System.out.println("2. " + Arrays.toString(data));
		System.out.println("3. " + data.length);
		
		
		if(packet.getMode() == Mode.SIGNIN.getCode()) {			// �α���
			
			if(validateDataNum(2, data) && validateStringLength(3, 20, data[0].trim()) && validateStringLength(3, 20, data[1].trim())) {
				Member member = new Member();
				member.setMemberName(data[0].trim());
				member.setPassword(data[1].trim());
				packet.setData(member);
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SIGNUP.getCode()) {	// ȸ������
		
			if(validateDataNum(3, data) && validateStringLength(3, 20, data[0].trim()) && validateStringLength(3, 20, data[1].trim()) && validateStringLength(1, 30, data[2].trim())) {
				Member member = new Member();
				member.setMemberName(data[0].trim());
				member.setPassword(data[1].trim());
				member.setName(data[2].trim());
				packet.setData(member);
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.BOOK.getCode()) {	// �����ϱ�
			//
		} else if(packet.getMode() == Mode.MOVIE.getCode()) {	// ��ȭ����
			
			if(validateDataNum(1, data) && validateInteger(data[0].trim())) {	// PK�� �������� üũ
				packet.setData(data[0].trim());
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SCHEDULE.getCode()) { // ����
			
			if(validateDataNum(1, data) && validateInteger(data[0].trim())) {
				packet.setData(data[0].trim());
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.SEAT.getCode()) { // �¼� ����
			
			if(validateDataNum(2, data) && validateInteger(data[0].trim()) && validateInteger(data[1].trim())
					&& validateSeats(data[0].trim(), data[1].trim())) {
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
			
			
		} else if(packet.getMode() == Mode.PAY.getCode()) {	// ����
			
			if(validateDataNum(1, data) && validateInteger(data[0].trim())) {
				
				packet.setData(new BigInteger(data[0].trim()));	
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.CANCEL.getCode()) {	// ���
			
			if(validateDataNum(1, data) && validateInteger(data[0].trim())) {
				packet.setData(data[0].trim());	
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.CHECK.getCode()) {	// ���� ��ȸ
			//
		} else if(packet.getMode() == Mode.POST_MOVIE.getCode()) {
			
			if(validateDataNum(4, data) && 
					validateStringLength(1, 50, data[0].trim()) && // ��ȭ����
					validateStringLength(1, 30, data[1].trim()) && // ����
					validateStringLength(1, 255, data[3].trim())) { // ��ȭ����
				
				Movie movie = new Movie();
				movie.setTitle(data[0].trim());
				movie.setDirector(data[1].trim());
				movie.setContent(data[2].trim());
				movie.setActor(data[3].trim());
				packet.setData(movie);
				
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.THEATER_SCHEDULE.getCode()) {	// �󿵰� ���� ��������

			if(!validateDataNum(1, data) || !validateInteger(data[0].trim())) {
				packet.setData(Body.INVALID.getCode());
			}
			
		} else if(packet.getMode() == Mode.POST_SCHEDULE.getCode()) {	// ���� ������
			
			if(validateDataNum(5, data) && validateInteger(data[0].trim()) && validateInteger(data[1].trim()) && 
					validateDate(data[2].trim()) && validateTime(data[3].trim()) && validateTime(data[4].trim()) && validateStartEndtime(data[3].trim(), data[4].trim())) {
				
				Schedule schedule = new Schedule();
				schedule.setTheaterId(Integer.parseInt(data[0].trim()));
				schedule.setMovieId(Integer.parseInt(data[1].trim()));
				schedule.setReservedDate(LocalDate.parse(data[2].trim()));
				schedule.setStartTime(LocalTime.parse(data[3].trim()));
				schedule.setEndTime(LocalTime.parse(data[4].trim()));
				packet.setData(schedule);
				
			} else {
				packet.setData(Body.INVALID.getCode());
			}
			
		}
		
		if(packet.getData() == Body.INVALID.getCode()) {
			return false;
		} else {
			return true;
		}
		
	}

}
