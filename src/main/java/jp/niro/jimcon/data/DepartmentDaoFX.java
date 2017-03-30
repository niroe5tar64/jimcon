package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DepartmentDaoFX {
    private final IntegerProperty departmentCode;
    private final StringProperty departmentName;
    private final StringProperty postcode;
    private final StringProperty address;
    private final StringProperty telNumber;
    private final StringProperty faxNumber;

    public DepartmentDaoFX(DepartmentDao dao) {
        this.departmentCode = new SimpleIntegerProperty(dao.getDepartmentCode());
        this.departmentName = new SimpleStringProperty(dao.getDepartmentName());
        this.postcode = new SimpleStringProperty(dao.getPostcode());
        this.address = new SimpleStringProperty(dao.getAddress());
        this.telNumber = new SimpleStringProperty(dao.getTelNumber());
        this.faxNumber = new SimpleStringProperty(dao.getFaxNumber());
    }

    public DepartmentDaoFX(int departmentCode,
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

    public int getDepartmentCode() {
        return departmentCode.get();
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public String getPostcode() {
        return postcode.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getTelNumber() {
        return telNumber.get();
    }

    public String getFaxNumber() {
        return faxNumber.get();
    }

    public void setDepartmentCode(int departmentCode) {
        this.departmentCode.set(departmentCode);
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setTelNumber(String telNumber) {
        this.telNumber.set(telNumber);
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber.set(faxNumber);
    }

    public IntegerProperty departmentCode() {
        return departmentCode;
    }

    public StringProperty departmentName() {
        return departmentName;
    }

    public StringProperty postcode() {
        return postcode;
    }

    public StringProperty address() {
        return address;
    }

    public StringProperty telNumber() {
        return telNumber;
    }

    public StringProperty faxNumber() {
        return faxNumber;
    }
}
