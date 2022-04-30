package study;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class DBCPTests {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost/bajo";
    public static final String USERNAME = "intern";
    public static final String PASSWORD = "1234";

    private GenericObjectPool<DataSource> connectionPool = null;

    public DataSource setUp() throws Exception {
        Class.forName(DRIVER).newInstance();

        connectionPool = new GenericObjectPool<DataSource>();
        connectionPool.setMaxActive(10);

        ConnectionFactory cf = new DriverManagerConnectionFactory(URL, USERNAME, PASSWORD);

        new PoolableConnectionFactory(cf, connectionPool, null, null, false, true);
        return new PoolingDataSource(connectionPool);
    }

    public GenericObjectPool<DataSource> getConnectionPool() {
        return connectionPool;
    }

    public static void main(String[] args) throws Exception {
    	DBCPTests demo = new DBCPTests();
        DataSource dataSource = demo.setUp();
        
        demo.printStatus();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            demo.printStatus();

            stmt = conn.prepareStatement("SELECT * FROM members");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Member_id: " + rs.getString("member_id"));
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        demo.printStatus();
    }

    /**
     * Prints connection pool status.
     */
    private void printStatus() {
        System.out.println("Max   : " + getConnectionPool().getMaxActive() + "; " +
                "Active: " + getConnectionPool().getNumActive() + "; " +
                "Idle  : " + getConnectionPool().getNumIdle());
    }
}
