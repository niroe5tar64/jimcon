package jp.niro.jimcon.data;

public interface Department {
	static final String TABLE_NAME = "m000_departments";
	static final int INDEX_DEPARTMENT_CODE = 1;
	static final int INDEX_DEPARTMENT_NAME = 2;
	static final int INDEX_POSTCODE = 3;
	static final int INDEX_ADDRESS = 4;
	static final int INDEX_TELNUMBER = 5;
	static final int INDEX_FAXNUMBER = 6;
	static final String DEPARTMENT_CODE = "m000_department_code";
	static final String DEPARTMENT_NAME = "m000_department_name";
	static final String POSTCODE = "m000_postcode";
	static final String ADDRESS = "m000_address";
	static final String TELNUMBER = "m000_tel_number";
	static final String FAXNUMBER = "m000_fax_number";

	int departmentCode = 0;
	String departmentName = "";
	String postcode = "";
	String address = "";
	String telNumber = "";
	String faxNumber = "";
}
