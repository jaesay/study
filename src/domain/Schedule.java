package domain;

import java.sql.Timestamp;

public class Schedule {

	private int scheduleId;
    private Timestamp scheduleDate;
    private int movieId;
    private int theaterId;
    
    // Getter, Setter method 정의
    public void setScheduleId(int scheduleId) {this.scheduleId = scheduleId;}
    public Timestamp getScheduleDate() {return scheduleDate;}
    public void setScheduleDate(Timestamp scheduleDate) {this.scheduleDate = scheduleDate;}
    public int getMovieId() {return movieId;}
    public void setMovieId(int movieId) {this.movieId = movieId;}
    public int getTheaterId() {return theaterId;}
    public void setTheaterId(int theaterId) {this.theaterId = theaterId;}
    
	// ToString 정의
    public int getScheduleId() {return scheduleId;}
    @Override
    public String toString() {
    	return "Schedule [scheduleId=" + scheduleId + ", scheduleDate=" + scheduleDate + ", movieId=" + movieId
    			+ ", theaterId=" + theaterId + "]";
    }
}
