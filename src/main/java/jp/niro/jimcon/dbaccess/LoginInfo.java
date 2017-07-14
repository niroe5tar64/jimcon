package jp.niro.jimcon.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by niro on 2017/04/13.
 */
public class LoginInfo {
    public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/jimcondb";
    private String driver;
    private String url;
    private String user;
    private String password;


    private LoginInfo() {}

    private static LoginInfo singleton = new LoginInfo();

    public static LoginInfo getInstance() {
        return singleton;
    }

    public void setLoginInfo(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
