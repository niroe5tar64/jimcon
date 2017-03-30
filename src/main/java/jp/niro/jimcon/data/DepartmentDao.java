package jp.niro.jimcon.data;

public class DepartmentDao {
	public static final String TABLE_NAME = "m000_departments";
	public static final int INDEX_DEPARTMENT_CODE = 1;
	public static final int INDEX_DEPARTMENT_NAME = 2;
	public static final int INDEX_POSTCODE = 3;
	public static final int INDEX_ADDRESS = 4;
	public static final int INDEX_TEL_NUMBER = 5;
	public static final int INDEX_FAX_NUMBER = 6;
	public static final String DEPARTMENT_CODE = "m000_department_code";
	public static final String DEPARTMENT_NAME = "m000_department_name";
	public static final String POSTCODE = "m000_postcode";
	public static final String ADDRESS = "m000_address";
	public static final String TEL_NUMBER = "m000_tel_number";
	public static final String FAX_NUMBER = "m000_fax_number";

	private int departmentCode = 0;
	private String departmentName = "";
	private String postcode = "";
	private String address = "";
	private String telNumber = "";
	private String faxNumber = "";

	public DepartmentDao(){}

    public DepartmentDao(int departmentCode,
            String departmentName,
            String postcode,
            String address,
            String telNumber,
            String faxNumber){
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.postcode = postcode;
        this.address = address;
        this.telNumber = telNumber;
        this.faxNumber = faxNumber;
    }

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
