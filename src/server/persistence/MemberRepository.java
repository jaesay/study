package server.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBCPConfig;
import domain.Member;

public class MemberRepository {
	
	private DBCPConfig dbcpConfig;

	public int save(Member member) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String sql = "INSERT INTO members (member_name, password, name) VALUES (?, ?, ?)";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, member.getMemberName());
			preparedStatement.setString(2, member.getPassword());
			preparedStatement.setString(3, member.getName());
			
			int isSignedUp = preparedStatement.executeUpdate();
			
			connection.commit();
			
			
			return isSignedUp;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement);
		}
		
	}
		
	public Member findByIdAndPassword(Member member) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; 
		
		try {
			String sql = "SELECT * FROM members where member_name = ? AND password = ? AND enabled = 1";
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, member.getMemberName());
			preparedStatement.setString(2, member.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.isBeforeFirst()) {    
				while (resultSet.next()) {
					member.setMemberId(resultSet.getInt("member_id"));
					member.setMemberName(resultSet.getString("member_name"));
					member.setPassword(resultSet.getString("password"));
					member.setName(resultSet.getString("name"));
					member.setRole(resultSet.getString("role"));
					member.setCreatedDate(resultSet.getTimestamp("created_date"));
					member.setUpdatedDate(resultSet.getTimestamp("updated_date"));                
				}
			} else {
				member = null; 
			}
			
			return member;
			
		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);
		}
	}

	public int delete(int memberId) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String sql = "update members set enabled = 0 where member_id = ?";
			
			
			dbcpConfig = DBCPConfig.getDataSource();
			connection = dbcpConfig.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, memberId);
			
			int isDeleted = preparedStatement.executeUpdate();
			
			connection.commit();
			
			
			return isDeleted;

		} finally {
			DBCPConfig.getDataSource().freeConnection(connection, preparedStatement);
		}
		
		
	}
}
