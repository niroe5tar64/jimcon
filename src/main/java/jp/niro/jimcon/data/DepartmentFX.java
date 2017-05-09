package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import jp.niro.jimcon.sql.LoginInfo;

public class DepartmentFX {
    private final Department department;

    private final IntegerProperty departmentCode;
    private final StringProperty departmentName;
    private final StringProperty postcode;
    private final StringProperty address;
    private final StringProperty telNumber;
    private final StringProperty faxNumber;

    public DepartmentFX() {
        this(new Department());
    }

    public DepartmentFX(int departmentCode,
                        String departmentName,
                        String postcode,
                        String address,
                        String telNumber,
                        String faxNumber
    ) {
        this(
                new Department(
                        departmentCode,
                        departmentName,
                        postcode,
                        address,
                        telNumber,
                        faxNumber
                )
        );
    }

    public DepartmentFX(Department department) {
        this.department = department;
        this.departmentCode = new SimpleIntegerProperty(department.getDepartmentCode());
        this.departmentName = new SimpleStringProperty(department.getDepartmentName());
        this.postcode = new SimpleStringProperty(department.getPostcode());
        this.address = new SimpleStringProperty(department.getAddress());
        this.telNumber = new SimpleStringProperty(department.getTelNumber());
        this.faxNumber = new SimpleStringProperty(department.getFaxNumber());
    }

    public Department getDepartment(){
        return department;
    }

    public int getDepartmentCode() {
        return departmentCode.get();
    }

    public void setDepartmentCode(int departmentCode) {
        this.departmentCode.set(departmentCode);
    }

    public IntegerProperty departmentCodeProperty() {
        return departmentCode;
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public StringProperty departmentNameProperty() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public String getPostcode() {
        return postcode.get();
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getTelNumber() {
        return telNumber.get();
    }

    public void setTelNumber(String telNumber) {
        this.telNumber.set(telNumber);
    }

    public StringProperty telNumberProperty() {
        return telNumber;
    }

    public String getFaxNumber() {
        return faxNumber.get();
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber.set(faxNumber);
    }

    public StringProperty faxNumberProperty() {
        return faxNumber;
    }

    public boolean saveNewData(LoginInfo login) {
        return false;
    }

    public void saveEditedData(LoginInfo login) {

    }
}
