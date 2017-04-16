package jp.niro.jimcon.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection getConnection(String driver, String url, String user, String password) throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}

	public static Connection getConnection(QueryBean bean) throws SQLException {
		return DriverManager.getConnection(
				bean.getUrl(),
				bean.getUser(),
				bean.getPassword()
		);
	}
}
