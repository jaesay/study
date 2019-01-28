package domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

	private int scheduleId;
    private Timestamp scheduleDate;
    private int movieId;
    private int theaterId;
    private LocalDate reservedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    
	// Getter, Setter method 정의
    public void setScheduleId(int scheduleId) {this.scheduleId = scheduleId;}
    public int getScheduleId() {return scheduleId;}
    public Timestamp getScheduleDate() {return scheduleDate;}
    public void setScheduleDate(Timestamp scheduleDate) {this.scheduleDate = scheduleDate;}
    public int getMovieId() {return movieId;}
    public void setMovieId(int movieId) {this.movieId = movieId;}
    public int getTheaterId() {return theaterId;}
    public void setTheaterId(int theaterId) {this.theaterId = theaterId;}
    public LocalDate getReservedDate() {return reservedDate;}
	public void setReservedDate(LocalDate reservedDate) {this.reservedDate = reservedDate;}
	public LocalTime getStartTime() {return startTime;}
	public void setStartTime(LocalTime startTime) {this.startTime = startTime;}
	public LocalTime getEndTime() {return endTime;}
	public void setEndTime(LocalTime endTime) {this.endTime = endTime;}
	
	// ToString 정의
	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", scheduleDate=" + scheduleDate + ", movieId=" + movieId
				+ ", theaterId=" + theaterId + ", reservedDate=" + reservedDate + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	
	public boolean overlap(Schedule schedule) {
        if (this.theaterId != schedule.getTheaterId()) {
            return false;
        }
        if (startTime.equals(schedule.startTime) && endTime.equals(schedule.endTime)) {
            return true;
        }
        
        return schedule.endTime.isAfter(startTime) && endTime.isAfter(schedule.startTime);
    }
	
}
