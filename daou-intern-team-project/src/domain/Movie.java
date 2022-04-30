package domain;

import java.sql.Timestamp;

public class Movie {
	
	private int movieId;
	private String title;
	private String director;
	private String content;
	private String actor;
	private String movieImage;
	private float rating;
	private int ratingCount;
	private int memberId;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	// Getter, Setter 정의
	public int getMovieId() {return movieId;}
	public void setMovieId(int movieId) {this.movieId = movieId;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getDirector() {return director;}
	public void setDirector(String director) {this.director = director;}
	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}
	public String getActor() {return actor;}
	public void setActor(String actor) {this.actor = actor;}
	public String getMovieImage() {return movieImage;}
	public void setMovieImage(String movieImage) {this.movieImage = movieImage;}
	public float getRating() {return rating;}
	public void setRating(float rating) {this.rating = rating;}
	public int getRatingCount() {return ratingCount;}
	public void setRatingCount(int ratingCount) {this.ratingCount = ratingCount;}
	public int getMemberId() {return memberId;}
	public void setMemberId(int memberId) {this.memberId = memberId;}
	public Timestamp getCreatedDate() {return createdDate;}
	public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}
	public Timestamp getUpdatedDate() {return updatedDate;}
	public void setUpdatedDate(Timestamp updatedDate) {this.updatedDate = updatedDate;}

	// ToString 정의
	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", title=" + title + ", director=" + director + ", content=" + content
				+ ", actor=" + actor + ", movieImage=" + movieImage + ", rating=" + rating + ", ratingCount="
				+ ratingCount + ", memberId=" + memberId + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}
	
	
}
