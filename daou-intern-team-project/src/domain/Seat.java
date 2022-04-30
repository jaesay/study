package domain;

public class Seat {

	private int theaterId;
	private int seatId;
	private boolean available;
	
	// Getter, Setter method ����
	public int getTheaterId() {return theaterId;}
	public void setTheaterId(int theaterId) {this.theaterId = theaterId;}
	public int getSeatId() {return seatId;}
	public void setSeatId(int seatId) {this.seatId = seatId;}
	public boolean isAvailable() {return available;}
	public void setAvailable(boolean available) {this.available = available;}
	
	// ToString �������̵�
	@Override
	public String toString() {
		return "Seat [theaterId=" + theaterId + ", seatId=" + seatId + ", available=" + available + "]";
	}
}
