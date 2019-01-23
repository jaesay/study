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
		
		String sql = "INSERT INTO members (member_name, password, name) VALUES (?, ?, ?)";
		
		dbcpConfig = DBCPConfig.getDataSource();
		Connection connection = dbcpConfig.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, member.getMemberName());
		preparedStatement.setString(2, member.getPassword());
		preparedStatement.setString(3, member.getName());
		
		int isSignedUp = preparedStatement.executeUpdate();
		
		connection.commit();
		
		DBCPConfig.getDataSource().freeConnection(connection, preparedStatement);
		
		return isSignedUp;
	}
		
	public Member findByIdAndPassword(Member member) throws SQLException {
		
		String sql = "SELECT * FROM members where member_name = ? and password = ?";

		dbcpConfig = DBCPConfig.getDataSource();
		Connection connection = dbcpConfig.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, member.getMemberName());
		preparedStatement.setString(2, member.getPassword());
		
		ResultSet resultSet = preparedStatement.executeQuery();

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

		DBCPConfig.getDataSource().freeConnection(connection, preparedStatement, resultSet);

		return member;
	}
}
