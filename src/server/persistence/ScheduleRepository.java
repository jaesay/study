package server.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DBCPConfig;
import domain.Schedule;

public class ScheduleRepository {

	private DBCPConfig dbcpConfig;
	
	public List<Schedule> findScheduleByTheaterId(int theaterId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT *, TIMESTAMP(reserved_date, start_time) as schedule_date FROM schedules WHERE reserved_date >= curdate() and theater_id = ? ORDER BY reserved_date, start_time";

			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, theaterId);
			List<Schedule> resultList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Schedule schedule = new Schedule();
				schedule.setScheduleId(resultSet.getInt("schedule_id"));
				schedule.setScheduleDate(resultSet.getTimestamp("schedule_date"));
				schedule.setMovieId(resultSet.getInt("movie_id"));
				schedule.setTheaterId(resultSet.getInt("theater_id"));
				schedule.setReservedDate(resultSet.getDate("reserved_date").toLocalDate());
				schedule.setStartTime(resultSet.getTime("start_time").toLocalTime());
				schedule.setEndTime(resultSet.getTime("end_time").toLocalTime());
				
				resultList.add(schedule);
			}
			
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	public List<Schedule> getTheaterDateSchedules(int theaterId, LocalDate date) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {

			String sql = "SELECT *, TIMESTAMP(reserved_date, start_time) as schedule_date FROM schedules WHERE theater_id = ? AND reserved_date = ?";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, theaterId);
			preparedStatement.setString(2, String.valueOf(date));
			List<Schedule> resultList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Schedule schedule = new Schedule();
				schedule.setScheduleId(resultSet.getInt("schedule_id"));
				schedule.setScheduleDate(resultSet.getTimestamp("schedule_date"));
				schedule.setMovieId(resultSet.getInt("movie_id"));
				schedule.setTheaterId(resultSet.getInt("theater_id"));
				schedule.setReservedDate(resultSet.getDate("reserved_date").toLocalDate());
				schedule.setStartTime(resultSet.getTime("start_time").toLocalTime());
				schedule.setEndTime(resultSet.getTime("end_time").toLocalTime());
				
				resultList.add(schedule);
			}
			
			return resultList;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}

	public int save(Schedule schedule) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String sql = "INSERT INTO schedules (movie_id, theater_id, start_time, end_time, reserved_date) VALUES (?, ?, ?, ?, ?)";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, schedule.getMovieId());
			preparedStatement.setInt(2, schedule.getTheaterId());
			preparedStatement.setTime(3, Time.valueOf(schedule.getStartTime()));
			preparedStatement.setTime(4, Time.valueOf(schedule.getEndTime()));
			preparedStatement.setDate(5, Date.valueOf(schedule.getReservedDate()));
			
			int isSaved = preparedStatement.executeUpdate();
			
			connection.commit();
			
			return isSaved;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement);
		}
	}
}
