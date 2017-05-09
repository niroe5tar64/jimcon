package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import jp.niro.jimcon.sql.LoginInfo;

public interface Department {
    static final String TABLE_NAME = "m000_departments";
    static final String DEPARTMENT_CODE = "m000_department_code";
    static final String DEPARTMENT_NAME = "m000_department_name";
    static final String POSTCODE = "m000_postcode";
    static final String ADDRESS = "m000_address";
    static final String TEL_NUMBER = "m000_tel_number";
    static final String FAX_NUMBER = "m000_fax_number";

    public int getDepartmentCode();

    public void setDepartmentCode(int departmentCode);

    public IntegerProperty departmentCodeProperty();

    public String getDepartmentName();

    public void setDepartmentName(String departmentName);

    public StringProperty departmentNameProperty();

    public String getPostcode();

    public void setPostcode(String postcode);

    public StringProperty postcodeProperty();

    public String getAddress();

    public void setAddress(String address);

    public StringProperty addressProperty();

    public String getTelNumber();

    public void setTelNumber(String telNumber);

    public StringProperty telNumberProperty();

    public String getFaxNumber();

    public void setFaxNumber(String faxNumber);

    public StringProperty faxNumberProperty();

    public boolean saveNewData(LoginInfo login);

    public void saveEditedData(LoginInfo login);
}
