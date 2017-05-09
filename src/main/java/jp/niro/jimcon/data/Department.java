package jp.niro.jimcon.data;

import javafx.beans.property.StringProperty;
import jp.niro.jimcon.sql.LoginInfo;

public class Department {
    static final String TABLE_NAME = "m000_departments";
    static final String DEPARTMENT_CODE = "m000_department_code";
    static final String DEPARTMENT_NAME = "m000_department_name";
    static final String POSTCODE = "m000_postcode";
    static final String ADDRESS = "m000_address";
    static final String TEL_NUMBER = "m000_tel_number";
    static final String FAX_NUMBER = "m000_fax_number";

    private int departmentCode;
    private String departmentName;
    private String postcode;
    private String address;
    private String telNumber;
    private String faxNumber;

    public Department() {
        this(
                0,
                "",
                "",
                "",
                "",
                "");
    }

    public Department(int departmentCode,
                         String departmentName,
                         String postcode,
                         String address,
                         String telNumber,
                         String faxNumber) {
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

    public StringProperty faxNumberProperty() {
        return null;
    }

    public boolean saveNewData(LoginInfo login){
        return false;
    }

    public void saveEditedData(LoginInfo login){

    }
}
