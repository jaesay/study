package domain;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Ticket {

	private int ticketId;
    private int scheduleId;
    private int theaterId;
    private int seatId;
    private BigInteger price;
    private Timestamp createdDate;
    
    // Getter, Setter method 정의
	public int getTicketId() {return ticketId;}
	public void setTicketId(int ticketId) {this.ticketId = ticketId;}
	public int getScheduleId() {return scheduleId;}
	public void setScheduleId(int scheduleId) {this.scheduleId = scheduleId;}
	public int getTheaterId() {return theaterId;}
	public void setTheaterId(int theaterId) {this.theaterId = theaterId;}
	public int getSeatId() {return seatId;}
	public void setSeatId(int seatId) {this.seatId = seatId;}
	public BigInteger getPrice() {return price;}
	public void setPrice(BigInteger price) {this.price = price;}
	public Timestamp getCreatedDate() {return createdDate;}
	public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

	// ToString 오버라이딩
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", scheduleId=" + scheduleId + ", theaterId=" + theaterId + ", seatId="
				+ seatId + ", price=" + price + ", createdDate=" + createdDate + "]";
	}
}
