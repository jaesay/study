package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCTests {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql//127.0.0.1/bajo";
			String user = "intern";
			String password = "1234";
			
			String insertSql = "insert into test values(?,?,?)";
			
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
			
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, "jaesung");
			preparedStatement.setInt(3, 1);
			
			preparedStatement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
