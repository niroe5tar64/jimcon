package jp.niro.jimcon.sql;

import java.util.List;

public class QueryBean {
	// TODO fix bug "SHOW SQL does not work"
	private static final String[] NOT_DML_KEYWORD = { "SELECT", "SHOW" };
	private String event = "";
	private String driver = "";
	private String url = "";
	private String user = "";
	private String password = "";
	private String query = "";
	private boolean dml = true;
	private String[] columns = null;
	private List<Object[]> data = null;
	private String message = "";
	private boolean success = true;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public List<Object[]> getData() {
		return data;
	}

	public void setData(List<Object[]> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
		for (String s : NOT_DML_KEYWORD) {
			if (query.trim().toUpperCase().startsWith(s)) {
				dml = false;
				return;
			}
		}

	}

	public boolean isDml() {
		return dml;
	}

	public void setDml(boolean dml) {
		this.dml = dml;
	}

}
