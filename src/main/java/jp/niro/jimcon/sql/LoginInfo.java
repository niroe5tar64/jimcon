package jp.niro.jimcon.sql;

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

    public LoginInfo(String driver,String url,String user,String password){
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}
