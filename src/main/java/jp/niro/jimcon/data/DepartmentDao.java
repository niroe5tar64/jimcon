package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import jp.niro.jimcon.sql.LoginInfo;

public class DepartmentDao implements Department {

    private int departmentCode;
    private String departmentName;
    private String postcode;
    private String address;
    private String telNumber;
    private String faxNumber;

    public DepartmentDao() {
        this(
                0,
                "",
                "",
                "",
                "",
                "");
    }

    public DepartmentDao(int departmentCode,
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

    @Override
    public int getDepartmentCode() {
        return departmentCode;
    }

    @Override
    public void setDepartmentCode(int departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public IntegerProperty departmentCodeProperty() {
        return null;
    }

    @Override
    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public StringProperty departmentNameProperty() {
        return null;
    }

    @Override
    public String getPostcode() {
        return postcode;
    }

    @Override
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public StringProperty postcodeProperty() {
        return null;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public StringProperty addressProperty() {
        return null;
    }

    @Override
    public String getTelNumber() {
        return telNumber;
    }

    @Override
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Override
    public StringProperty telNumberProperty() {
        return null;
    }

    @Override
    public String getFaxNumber() {
        return faxNumber;
    }

    @Override
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Override
    public StringProperty faxNumberProperty() {
        return null;
    }

    @Override
    public boolean saveNewData(LoginInfo login) {
        return false;
    }

    @Override
    public void saveEditedData(LoginInfo login) {

    }
}
