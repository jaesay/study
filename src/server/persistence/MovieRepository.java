package server.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBCPConfig;
import domain.Movie;

public class MovieRepository {

	private DBCPConfig dbcpConfig;
	
	public int save(Movie movie) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {

			String sql = "INSERT INTO movies (title, director, content, actor) VALUES (?, ?, ?, ?)";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, movie.getTitle());
			preparedStatement.setString(2, movie.getDirector());
			preparedStatement.setString(3, movie.getContent());
			preparedStatement.setString(4, movie.getActor());
			
			int isPosted = preparedStatement.executeUpdate();
			
			connection.commit();
			
			
			return isPosted;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement);
		}
		
	}
	
	public List<Movie> findWatchedMoviesInOneWeek(int memberId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {

			String sql = "select distinct m.movie_id, m.title from orders o " + 
					"inner join order_ticket ot on o.order_id = ot.order_id " + 
					"inner join tickets t on ot.ticket_id = t.ticket_id " + 
					"inner join schedules s on t.schedule_id = s.schedule_id AND DATE(s.schedule_date) < CURDATE() AND DATE_ADD(DATE(schedule_date), INTERVAL +  7 DAY) > CURDATE() " + 
					"inner join movies m on s.movie_id = m.movie_id " + 
					"where o.member_id = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, memberId);
			List<Movie> resultList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Movie movie = new Movie(); 
				movie.setMovieId(resultSet.getInt("movie_id"));
				movie.setTitle(resultSet.getString("title"));
				resultList.add(movie);
			}
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}

	
}
