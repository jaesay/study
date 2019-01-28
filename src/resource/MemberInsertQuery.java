package resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberInsertQuery {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bajo?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "intern";
    private static final String PASSWORD = "1234";
	
	public static void main(String[] args) {
		new MemberInsertQuery().inertMembers();
	}

	private void inertMembers() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		   
		try {
			Class.forName(DRIVER);
		    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		    connection.setAutoCommit(false);
		    String sql = "insert into members (member_name, password, name) values (?,?,?);";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(2, "1234");
			
			for(int i=1; i<10001; i++) {
				preparedStatement.setString(1, "id" + i);
				preparedStatement.setString(3, "name" + i);
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
		    
			connection.commit();
		    
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
