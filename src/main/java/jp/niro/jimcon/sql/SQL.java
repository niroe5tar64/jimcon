package jp.niro.jimcon.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQL {
	
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;
	
	public SQL(Connection connection){
		this.connection = connection;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public ResultSet getResultSet(){
		return this.resultSet;
	}
	
	public PreparedStatement getPreparedStatement(){
		return this.preparedStatement;
	}
	
	public void preparedStatement(String query) throws SQLException{
		this.preparedStatement = connection.prepareStatement(query);
	}
	
	public ResultSetMetaData getMetaData() throws SQLException{
		return preparedStatement.getMetaData();
	}
	
	public void executeQuery() throws SQLException{
		this.resultSet = preparedStatement.executeQuery();
	}
	
	public int executeUpdate() throws SQLException{
		return preparedStatement.executeUpdate();
	}
	
	public Object getObject(int columnIndex) throws SQLException{
		return resultSet.getObject(columnIndex);
	}
	
	public boolean next() throws SQLException{
		return resultSet.next();
	}
	
	public void close(){
		try{
			if(resultSet != null ) resultSet.close();
		} catch (Exception ignore){}
		try{
			if(preparedStatement != null ) preparedStatement.close();
		} catch (Exception ignore){}
		try{
			if(connection != null ) connection.close();
		} catch (Exception ignore){}
	}
}
