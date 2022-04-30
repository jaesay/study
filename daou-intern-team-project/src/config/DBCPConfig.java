package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;


public class DBCPConfig {

	private static DBCPConfig dataSource;
	
	// DB Resource ����
	private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bajo?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "intern";
    private static final String PASSWORD = "1234";

    synchronized public static DBCPConfig getDataSource() {

        try {
            if (dataSource == null) {
                dataSource = new DBCPConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    private DBCPConfig() {
    	System.out.println("Database Connection pool�� �����մϴ�.");
        initFirstConnection();
    }

    private void initFirstConnection(){
        try {
            setupFirstDriver();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:apache:commons:dbcp:first_connection");
        } catch (SQLException e) {
        	e.printStackTrace();
        }

        return con;
    }

    public void setupFirstDriver() throws Exception {
    	Class.forName(DRIVER).newInstance();

        // Connection Pool ����, �ɼǼ���
        GenericObjectPool<DataSource> connectionPool = new GenericObjectPool<DataSource>();
        connectionPool.setMaxActive(5000);	// ���ÿ� ����� �� �ִ� �ִ� Ŀ�ؼ� ����
        connectionPool.setMinIdle(3000); // �ּ������� ������ Ŀ�ؼ� ����
        connectionPool.setMaxWait(15000);
        connectionPool.setTimeBetweenEvictionRunsMillis(360000);
        connectionPool.setMinEvictableIdleTimeMillis(180000);
        connectionPool.setMaxIdle(5000);	// Ŀ�ؼ� Ǯ�� �ݳ��� �� �ִ�� ������ �� �ִ� Ŀ�ؼ� ����
        connectionPool.setTestOnBorrow(true);

        // ���� DB���� Ŀ�ؼ��� �������ִ� ���丮 ����
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(URL, USERNAME, PASSWORD);

        new PoolableConnectionFactory(
                connectionFactory,
                connectionPool,
                null, // statement pool
                "SELECT 1", // Ŀ�ؼ� �׽�Ʈ ����: Ŀ�ؼ��� ��ȿ���� �׽�Ʈ�� �� ���Ǵ� ����.
                false, // read only ����
                false);

        // Pooling�� ���� JDBC ����̹� ���� �� ���
        PoolingDriver driver = new PoolingDriver();

        // JDBC ����̹��� Ŀ�ؼ� Ǯ ���
        driver.registerPool("first_connection", connectionPool);
    }

    public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection con, PreparedStatement pstmt) {
        try {
            if (pstmt != null) pstmt.close();
            freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection con, Statement stmt) {
        try {
            if (stmt != null) stmt.close();
            freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection con) {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(PreparedStatement pstmt) {
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}