package jp.niro.jimcon.data;

public interface Department {
	static final String TABLE_NAME = "m000_departments";
	static final int INDEX_DEPARTMENT_CODE = 1;
	static final int INDEX_DEPARTMENT_NAME = 2;
	static final int INDEX_POSTCODE = 3;
	
	
	
	int departmentCode = 0;
	String departmentName = "";
	String postcode = "";
	String address = "";
	String telNumber = "";
	String faxNumber = "";
}
