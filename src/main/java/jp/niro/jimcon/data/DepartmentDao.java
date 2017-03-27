package jp.niro.jimcon.data;

public class DepartmentDao {
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
