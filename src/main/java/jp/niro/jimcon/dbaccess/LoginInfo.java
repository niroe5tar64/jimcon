package jp.niro.jimcon.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by niro on 2017/04/13.
 */
public class LoginInfo {
    private String driver;
    private String url;
    private String user;
    private String password;

    // TODO singletonを適用する事
    public static LoginInfo create() {
        return new LoginInfo(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/jimcondb",
                "fromclient",
                "motpL@26");
    }

    public static LoginInfo create(String driver, String url, String user, String password) {
        return new LoginInfo(driver, url, user, password);
    }

    private LoginInfo(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
