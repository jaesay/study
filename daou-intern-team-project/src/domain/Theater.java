package domain;

public class Theater {

	private int theaterId;
	private String theaterName;
	private int seatTotalNumber;
	
	// Getter, Setter method Á¤ÀÇ
	public int getTheaterId() {return theaterId;}
	public void setTheaterId(int theaterId) {this.theaterId = theaterId;}
	public String getTheaterName() {return theaterName;}
	public void setTheaterName(String theaterName) {this.theaterName = theaterName;}
	public int getSeatTotalNumber() {return seatTotalNumber;}
	public void setSeatTotalNumber(int seatTotalNumber) {this.seatTotalNumber = seatTotalNumber;}

	// ToString
	@Override
	public String toString() {
		return "Theater [theaterId=" + theaterId + ", theaterName=" + theaterName + ", seatTotalNumber="
				+ seatTotalNumber + "]";
	}
}
