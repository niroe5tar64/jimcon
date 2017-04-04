package jp.niro.jimcon.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.niro.jimcon.data.DepartmentDao;

public class SQLManager {
	
	public Connection getConnection(String driver, String url, String user, String password) throws SQLException{
		return DriverManager.getConnection(url,user,password);
	}
	
	public void tryLogin(QueryBean bean) {
		
		SQL sql = null;
		try {
			sql = new SQL(getConnection(bean.getDriver(),
					bean.getUrl(),
					bean.getUser(),
					bean.getPassword()));
			
		} catch(Throwable t){
			bean.setSuccess(false);
			bean.setMessage(t.getMessage());
		}
		
		close(sql);
	}
	
	public void doExecute(QueryBean bean){
		
		if(bean.isDml()){
			executeUpdate(bean);
		} else {
			executeQuery(bean);
		}
		
	}
	
	private void executeUpdate(QueryBean bean){
		
		SQL sql = null;
		try {
			sql = new SQL(getConnection(bean.getDriver(),
					bean.getUrl(), 
					bean.getUser(),
					bean.getPassword()));
		
			sql.preparedStatement(bean.getQuery());
			bean.setMessage(sql.executeUpdate() + "data updated");
		} catch (Throwable t) {
			bean.setSuccess(false);
			bean.setMessage(t.getMessage());
		}
		
		close(sql);
	}
	
	private void executeQuery(QueryBean bean) {
		
		SQL sql = null;
		try {
			sql = new SQL(getConnection(bean.getDriver(),
					bean.getUrl(), 
					bean.getUser(),
					bean.getPassword()));
			
			sql.preparedStatement(bean.getQuery());
			sql.executeQuery();
			
			ResultSetMetaData metaData = sql.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] columns = new String[columnCount];
			for (int i = 0; i < columns.length; i++) {
				columns[i] = metaData.getColumnName(i+1);
			}
			
			Object[] row = null;
			List<Object[]> data = new ArrayList<Object[]>();
			
			while(sql.next()){
				row = new Object[columnCount];
				for (int i = 0; i < columns.length; i++) {
					row[i] = sql.getObject(i+1);
				}
				data.add(row);
			}
			
			bean.setColumns(columns);
			bean.setData(data);
			
		} catch (Throwable t) {
			bean.setSuccess(false);
			bean.setMessage(t.getMessage());
		}
		
		close(sql);
	}
	
	public void close(SQL sql){
		if(sql != null){
			sql.close();
		}
	}
	
	public static void main(String[] args){
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jimcondb";
		String user = "fromclient";
		String password  = "motpL@26";
		String query = "SELECT m000_department_code, "
						+ "m000_department_name, "
						+ "m000_postcode, "
						+ "m000_address, "
						+ "m000_tel_number, "
						+ "m000_fax_number " 
						+ "FROM jimcondb.m000_departments;";
		
		SQL sql = null;
		
		try {
			sql = new SQL(ConnectionFactory.getConnection(
					driver, 
					url, 
					user, 
					password));
			
			sql.preparedStatement(query);
			sql.executeQuery();
			
			DepartmentDao dao = null;
			List<DepartmentDao> daoList = new ArrayList<>();
 			while (sql.next()){
 				dao = new DepartmentDao();
 				dao.setDepartmentCode(sql.getResultSet().getInt(1));
 				dao.setDepartmentName(sql.getResultSet().getString(2));
 				dao.setPostcode(sql.getResultSet().getString(3));
 				dao.setAddress(sql.getResultSet().getString(4));
 				dao.setTelNumber(sql.getResultSet().getString(5));
 				dao.setFaxNumber(sql.getResultSet().getString(6));
 				daoList.add(dao);	
			}
			
 			System.out.println(daoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (sql != null){
			sql.close();
		}
		
		
	}
}
