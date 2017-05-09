package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import jp.niro.jimcon.sql.LoginInfo;

public class DepartmentFX implements Department {
    private final IntegerProperty departmentCode;
    private final StringProperty departmentName;
    private final StringProperty postcode;
    private final StringProperty address;
    private final StringProperty telNumber;
    private final StringProperty faxNumber;

    public DepartmentFX() {
        this(
                0,
                "",
                "",
                "",
                "",
                "");
    }

    public DepartmentFX(Department department) {
        this.departmentCode = new SimpleIntegerProperty(department.getDepartmentCode());
        this.departmentName = new SimpleStringProperty(department.getDepartmentName());
        this.postcode = new SimpleStringProperty(department.getPostcode());
        this.address = new SimpleStringProperty(department.getAddress());
        this.telNumber = new SimpleStringProperty(department.getTelNumber());
        this.faxNumber = new SimpleStringProperty(department.getFaxNumber());
    }

    public DepartmentFX(int departmentCode,
                        String departmentName,
                        String postcode,
                        String address,
                        String telNumber,
                        String faxNumber) {
        this.departmentCode = new SimpleIntegerProperty(departmentCode);
        this.departmentName = new SimpleStringProperty(departmentName);
        this.postcode = new SimpleStringProperty(postcode);
        this.address = new SimpleStringProperty(address);
        this.telNumber = new SimpleStringProperty(telNumber);
        this.faxNumber = new SimpleStringProperty(faxNumber);
    }

    @Override
    public int getDepartmentCode() {
        return departmentCode.get();
    }

    @Override
    public void setDepartmentCode(int departmentCode) {
        this.departmentCode.set(departmentCode);
    }

    @Override
    public IntegerProperty departmentCodeProperty() {
        return departmentCode;
    }

    @Override
    public String getDepartmentName() {
        return departmentName.get();
    }

    @Override
    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    @Override
    public StringProperty departmentNameProperty() {
        return departmentName;
    }

    @Override
    public String getPostcode() {
        return postcode.get();
    }

    @Override
    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    @Override
    public StringProperty postcodeProperty() {
        return postcode;
    }

    @Override
    public String getAddress() {
        return address.get();
    }

    @Override
    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public String getTelNumber() {
        return telNumber.get();
    }

    @Override
    public void setTelNumber(String telNumber) {
        this.telNumber.set(telNumber);
    }

    @Override
    public StringProperty telNumberProperty() {
        return telNumber;
    }

    @Override
    public String getFaxNumber() {
        return faxNumber.get();
    }

    @Override
    public void setFaxNumber(String faxNumber) {
        this.faxNumber.set(faxNumber);
    }

    @Override
    public StringProperty faxNumberProperty() {
        return faxNumber;
    }

    @Override
    public boolean saveNewData(LoginInfo login) {
        return false;
    }

    @Override
    public void saveEditedData(LoginInfo login) {

    }
}
