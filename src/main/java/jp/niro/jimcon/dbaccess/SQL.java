package jp.niro.jimcon.dbaccess;

import java.sql.*;

public class SQL {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    public SQL(Connection connection) {
        this.connection = connection;
    }

    public SQL() throws SQLException {
        new SQL(LoginInfo.getInstance().getConnection());
    }

    public Connection getConnection() {
        return this.connection;
    }

    public ResultSet getResultSet() {
        return this.resultSet;
    }

    public String getString(String columnName) throws SQLException {
        return this.resultSet.getString(columnName);
    }

    public String getString(int columnIndex) throws SQLException {
        return this.resultSet.getString(columnIndex);
    }

    public int getInt(String columnName) throws SQLException {
        return this.resultSet.getInt(columnName);
    }

    public int getInt(int columnIndex) throws SQLException {
        return this.resultSet.getInt(columnIndex);
    }

    public long getLong(String columnName) throws SQLException {
        return this.resultSet.getInt(columnName);
    }

    public long getLong(int columnIndex) throws SQLException {
        return this.resultSet.getLong(columnIndex);
    }

    public double getDouble(String columnName) throws SQLException {
        return this.resultSet.getDouble(columnName);
    }

    public double getDouble(int columnIndex) throws SQLException {
        return this.resultSet.getDouble(columnIndex);
    }

    public Boolean getBoolean(String columnName) throws SQLException {
        return this.resultSet.getBoolean(columnName);
    }

    public Boolean getBoolean(int columnIndex) throws SQLException {
        return this.resultSet.getBoolean(columnIndex);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        preparedStatement.setString(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        preparedStatement.setInt(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        preparedStatement.setDouble(parameterIndex, x);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        preparedStatement.setBoolean(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        preparedStatement.setDate(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        preparedStatement.setObject(parameterIndex, x);
    }

    public PreparedStatement getPreparedStatement() {
        return this.preparedStatement;
    }

    public void preparedStatement(String query) throws SQLException {
        this.preparedStatement = connection.prepareStatement(query);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return preparedStatement.getMetaData();
    }

    public void executeQuery() throws SQLException {
        this.resultSet = preparedStatement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return preparedStatement.executeUpdate();
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object getObject(int columnIndex) throws SQLException {
        return resultSet.getObject(columnIndex);
    }

    public boolean next() throws SQLException {
        return resultSet.next();
    }

    public void close() {
        try {
            if (resultSet != null) resultSet.close();
        } catch (Exception ignore) {
        }
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (Exception ignore) {
        }
        try {
            if (connection != null) connection.close();
        } catch (Exception ignore) {
        }
    }
}
