package server.persistence;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import config.DBCPConfig;
import domain.Movie;
import domain.Schedule;

public class BookRepository {

	private DBCPConfig dbcpConfig;
		
	public List<Movie> findScheduledMovieList() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT DISTINCT m.* FROM movies m INNER JOIN schedules s ON m.movie_id = s.movie_id WHERE DATE(reserved_date) >= CURDATE() ORDER BY movie_id";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			
			List<Movie> movies = new ArrayList<>();
			
			if (resultSet.isBeforeFirst() ) {    
				while (resultSet.next()) {
					Movie movie = new Movie();
					
					movie.setMovieId(resultSet.getInt("movie_id"));
					movie.setTitle(resultSet.getString("title"));
					movie.setDirector(resultSet.getString("director"));
					movie.setContent(resultSet.getString("content"));
					movie.setActor(resultSet.getString("actor"));
					movie.setCreatedDate(resultSet.getTimestamp("created_date"));
					movie.setUpdatedDate(resultSet.getTimestamp("updated_date"));
					
					movies.add(movie);
				}
			} else {
				movies = null;
			}
			
			
			return movies;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}

	public List<Map<String, String>> findMovieSchedule(int movie_id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT s.schedule_id, TIMESTAMP(s.reserved_date, s.start_time) as schedule_date, s.theater_id " +
						 "FROM schedules s INNER JOIN movies m ON m.movie_id = s.movie_id " +
						 "WHERE m.movie_id = ? AND DATE(s.reserved_date) >= CURDATE() " +
						 "ORDER BY s.reserved_date, s.start_time";
			
			List<Map<String, String>> resultList = new ArrayList<>(); 
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, movie_id);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Map<String, String> resultMap = new HashMap<>(); 
				resultMap.put("scheduleId", resultSet.getString("schedule_id"));
				resultMap.put("scheduleDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(resultSet.getTimestamp("schedule_date")));
				resultMap.put("theaterId", resultSet.getString("theater_id"));
				resultList.add(resultMap);
			}
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}

	public List<Map<String, String>> findSeats(int theaterId, int scheduleId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT s.seat_id, s.theater_id, CASE WHEN bs.schedule_id IS NOT NULL THEN 1 ELSE 0 END AS available " + 
						 "FROM seats s " + 
						 "LEFT join booked_seats bs " + 
						 "ON s.seat_id = bs.seat_id AND s.theater_id = bs.theater_id " + 
						 "AND (bs.schedule_id = ? OR bs.schedule_id IS NULL) " + 
						 "WHERE s.theater_id = ?";
			
			
			List<Map<String, String>> resultList = new ArrayList<>(); 
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, scheduleId);
			preparedStatement.setInt(2, theaterId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Map<String, String> resultMap = new HashMap<>(); 
				resultMap.put("seatId", resultSet.getString("seat_id"));
				resultMap.put("available", resultSet.getString("available"));
				resultList.add(resultMap);
			}
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}

	public BigInteger getTicketPrice(int rankId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT price FROM ranks WHERE rank_id = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, rankId);
			resultSet = preparedStatement.executeQuery();
			BigInteger ticketPrice = BigInteger.ZERO;
			
			while (resultSet.next()) {
				ticketPrice = BigInteger.valueOf(resultSet.getInt("price"));
			}
						
			return ticketPrice;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}

	public Schedule findSchedule(int scheduleId) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {

			String sql = "SELECT *, TIMESTAMP(reserved_date, start_time) as schedule_date FROM schedules WHERE schedule_id = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, scheduleId);
			resultSet = preparedStatement.executeQuery();
			Schedule schedule = new Schedule();
			
			while (resultSet.next()) {
				schedule.setScheduleId(resultSet.getInt("schedule_id"));
				schedule.setScheduleDate(resultSet.getTimestamp("schedule_date"));
				schedule.setMovieId(resultSet.getInt("movie_id"));
				schedule.setTheaterId(resultSet.getInt("theater_id"));
			}
			
			return schedule;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}

	@SuppressWarnings("resource")
	public int save(int memberId, Schedule schedule, BigInteger total_price, Integer[] seats) {
		
		String bookedSeatSql = "INSERT INTO booked_seats (schedule_id, theater_id, seat_id) VALUES (?, ?, ?)";
		String ticketSql = "INSERT INTO tickets (schedule_id, theater_id, seat_id) VALUES (?, ?, ?)";
		String orderSql = "INSERT INTO orders (member_id, total_price) VALUES (?, ?)";
		String orderTicketSql = "INSERT INTO order_ticket (order_id, ticket_id) VALUES (?, ?)";

		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			
			connection.setAutoCommit(false);
						
			preparedStatement = connection.prepareStatement(bookedSeatSql);
			
			preparedStatement.setInt(1, schedule.getScheduleId());
			preparedStatement.setInt(2, schedule.getTheaterId());
			
			for(int seatId : seats) {
				preparedStatement.setInt(3, seatId);
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();

//			connection.commit();
			result = true;

			if(result) { 
				//
				preparedStatement = connection.prepareStatement(ticketSql, Statement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setInt(1, schedule.getScheduleId());
				preparedStatement.setInt(2, schedule.getTheaterId());
				
				for(int seatId : seats) {
					preparedStatement.setInt(3, seatId);
					preparedStatement.addBatch();
				}
				
				preparedStatement.executeBatch();
				resultSet = preparedStatement.getGeneratedKeys();
				
				List<Integer> ticketPKs = new ArrayList<>();
				
				while (resultSet.next()) {
					ticketPKs.add(resultSet.getBigDecimal(1).intValue());
				}
				
				//
				preparedStatement = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
				BigDecimal orderId = BigDecimal.ZERO;
				
				preparedStatement.setInt(1, memberId);
				preparedStatement.setInt(2, total_price.intValue());
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();
				while (resultSet.next()) {
					orderId = resultSet.getBigDecimal(1);
				}
				
				//
				preparedStatement = connection.prepareStatement(orderTicketSql);
				preparedStatement.setInt(1, orderId.intValue());
				for(int ticketId : ticketPKs) {
					preparedStatement.setInt(2, ticketId);
					preparedStatement.addBatch();
				}
				
				preparedStatement.executeBatch();
				connection.commit();
	
				return orderId.intValue();
			} else {
				connection.commit();
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;

		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}

		return 0;
	}

	public List<Map<String, String>> findOrders(int orderId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT o.order_id, o.member_id, o.total_price, o.order_date, sc.schedule_id, TIMESTAMP(sc.reserved_date, sc.start_time) as schedule_date, m.movie_id, m.title, th.theater_id, th.floor, bs.seat_id FROM orders o " + 
					"INNER JOIN order_ticket ot ON o.order_id = ot.order_id " + 
					"INNER JOIN tickets t ON ot.ticket_id = t.ticket_id " + 
					"INNER JOIN booked_seats bs on t.schedule_id = bs.schedule_id AND t.seat_id = bs.seat_id " + 
					"INNER JOIN schedules sc ON bs.schedule_id = sc.schedule_id AND DATE(sc.reserved_date) >= CURDATE() " + 
					"INNER JOIN theaters th ON sc.theater_id = th.theater_id " + 
					"INNER JOIN movies m ON sc.movie_id = m.movie_id " + 
					"WHERE o.order_id = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);
			resultSet = preparedStatement.executeQuery();
			List<Map<String, String>> resultList = new ArrayList<>();
			
			while (resultSet.next()) {
				Map<String, String> resultMap = new HashMap<>();
				resultMap.put("orderId", resultSet.getString("order_id"));
				resultMap.put("memberId", resultSet.getString("member_id"));
				resultMap.put("totalPrice", resultSet.getString("total_price"));
				resultMap.put("orderDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("order_date")));
				resultMap.put("scheduleId", resultSet.getString("schedule_id"));
				resultMap.put("scheduleDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(resultSet.getTimestamp("schedule_date")));
				resultMap.put("movieId", resultSet.getString("movie_id"));
				resultMap.put("title", resultSet.getString("title"));
				resultMap.put("theaterId", resultSet.getString("theater_id"));
				resultMap.put("floor", resultSet.getString("floor"));
				resultMap.put("seatId", resultSet.getString("seat_id"));
				resultList.add(resultMap);
			}
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}

	@SuppressWarnings("resource")
	public boolean cancel(List<Map<String, String>> resultList) {

		String offFKSql = "SET FOREIGN_KEY_CHECKS = 0";
		String orderTicketSql = "DELETE FROM order_ticket WHERE order_id = ?";
		String orderSql = "DELETE FROM orders WHERE order_id = ?";
		String ticketSql = "DELETE FROM tickets WHERE ticket_id IN (SELECT ticket_id FROM order_ticket WHERE order_id = ?)";
		String bookedSeatSql = "DELETE FROM booked_seats WHERE schedule_id = ? AND seat_id IN ";
		String onFKSql = "SET FOREIGN_KEY_CHECKS = 1";

		boolean result = false;
		Map<String, String> map = resultList.get(0);
		int orderId = Integer.parseInt(map.get("orderId"));
		int scheduleId = Integer.parseInt(map.get("scheduleId"));
		
		/*if(resultList.size() > 0) {
			map = resultList.get(0);
		}*/
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(offFKSql);
			preparedStatement.execute();
			
			// Ticket 테이블 데이터 삭제
			preparedStatement = connection.prepareStatement(ticketSql);
			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();

			// Order_Ticket 테이블 데이터 삭제
			preparedStatement = connection.prepareStatement(orderTicketSql);
			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();
			
			// Order 테이블 데이터 삭제
			preparedStatement = connection.prepareStatement(orderSql);
			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();

			// Booked_Seats 테이블 데이터 삭제
			StringJoiner inClause = new StringJoiner(",", "(", ")");
			for(int i=0; i<resultList.size(); i++) {
				inClause.add("?");
			}
			
			StringBuilder bookedSeatSqlStringBuilder = new StringBuilder().append(bookedSeatSql).append(inClause);
			
			preparedStatement = connection.prepareStatement(bookedSeatSqlStringBuilder.toString());
			preparedStatement.setInt(1, scheduleId);
			for(int i=0; i<resultList.size(); i++) {
				Map<String, String> m = resultList.get(i);
				preparedStatement.setInt(i+2, Integer.parseInt(m.get("seatId")));
			}
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(onFKSql);
			preparedStatement.execute();
			
			connection.commit();
			result = true;
			
		} catch (SQLException e) {

			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		return result;
	}
	
	public List<Map<String, String>> findCurrentOrders(int memberId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			String sql = "SELECT  DISTINCT o.order_id, o.member_id, o.total_price, o.order_date, sc.schedule_id, TIMESTAMP(sc.reserved_date, sc.start_time) as schedule_date, m.movie_id, m.title, th.theater_id, th.floor from orders o " + 
					"INNER JOIN order_ticket ot ON o.order_id = ot.order_id " + 
					"INNER JOIN tickets t ON ot.ticket_id = t.ticket_id " + 
					"INNER JOIN booked_seats bs on t.schedule_id = bs.schedule_id AND t.seat_id = bs.seat_id " + 
					"INNER JOIN schedules sc ON bs.schedule_id = sc.schedule_id AND DATE(sc.reserved_date) >= CURDATE() " + 
					"INNER JOIN theaters th ON sc.theater_id = th.theater_id " + 
					"INNER JOIN movies m ON sc.movie_id = m.movie_id " + 
					"WHERE o.member_id = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, memberId);
			resultSet = preparedStatement.executeQuery();
			List<Map<String, String>> resultList = new ArrayList<>();
			
			while (resultSet.next()) {
				Map<String, String> resultMap = new HashMap<>();
				resultMap.put("orderId", resultSet.getString("order_id"));
				resultMap.put("memberId", resultSet.getString("member_id"));
				resultMap.put("totalPrice", resultSet.getString("total_price"));
				resultMap.put("orderDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("order_date")));
				resultMap.put("scheduleId", resultSet.getString("schedule_id"));
				resultMap.put("scheduleDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(resultSet.getTimestamp("schedule_date")));
				resultMap.put("movieId", resultSet.getString("movie_id"));
				resultMap.put("title", resultSet.getString("title"));
				resultMap.put("theaterId", resultSet.getString("theater_id"));
				resultMap.put("floor", resultSet.getString("floor"));
				resultList.add(resultMap);
			}
			
			return resultList;

		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}

	public int findMembersByOrderId(int orderId) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "select member_id from orders where order_id = ?";

			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);
			resultSet = preparedStatement.executeQuery();
			int memberId = 0;
			
			while (resultSet.next()) {
				memberId = resultSet.getInt("member_id");
			}
			
			return memberId;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
}
