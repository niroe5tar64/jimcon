package jp.niro.jimcon.data;

public class DepartmentDao {
	static final String TABLE_NAME = "m000_departments";
	static final int INDEX_DEPARTMENT_CODE = 1;
	static final int INDEX_DEPARTMENT_NAME = 2;
	static final int INDEX_POSTCODE = 3;
	static final int INDEX_ADDRESS = 4;
	static final int INDEX_TEL_NUMBER = 5;
	static final int INDEX_FAX_NUMBER = 6;
	static final String DEPARTMENT_CODE = "m000_department_code";
	static final String DEPARTMENT_NAME = "m000_department_name";
	static final String POSTCODE = "m000_postcode";
	static final String ADDRESS = "m000_address";
	static final String TEL_NUMBER = "m000_tel_number";
	static final String FAX_NUMBER = "m000_fax_number";

	int departmentCode = 0;
	String departmentName = "";
	String postcode = "";
	String address = "";
	String telNumber = "";
	String faxNumber = "";

	public int getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(int departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	@Override
	public String toString() {
		return departmentCode + " "
				+ departmentName + " "
				+ postcode + " "
				+ address + " "
				+ telNumber + " "
				+ faxNumber + " ";
	}
	
	
}
