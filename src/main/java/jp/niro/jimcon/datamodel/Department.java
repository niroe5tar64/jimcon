package jp.niro.jimcon.datamodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.*;

import java.sql.SQLException;

public class Department {
    public static final String DUPLICATED_ERROR = "Duplicated Error：部署コード";
    public static final String NO_SELECTION_ERROR = "No Selection Error：部署コード";
    public static final String DO_NOT_DELETE_ERROR = "Don't delete";

    public static final String DEPARTMENT_CODE_DUPLICATED = "この部署コードは既に使われています。\n";
    public static final String DEPARTMENT_CODE_HAS_NOT_BEEN_REGISTERED = "この部署コードは登録されていません。\n";

    public static final String DEPARTMENT_CODE_IS_EMPTY = "部署コードが空欄です。\n";
    public static final String DEPARTMENT_NAME_IS_EMPTY = "部署名が空欄です。\n";
    public static final String POSTCODE_IS_EMPTY = "郵便番号が空欄です。\n";
    public static final String ADDRESS_IS_EMPTY = "住所が空欄です。\n";
    public static final String TEL_NUMBER_IS_EMPTY = "電話番号が空欄です。\n";
    public static final String FAX_NUMBER_IS_EMPTY = "FAX番号が空欄です。\n";

    public static final String DEPARTMENT_CODE_IS_NOT_IN_RANGE = "部署コードが不正な値です。0～255の範囲で入力して下さい。\n";
    public static final String DEPARTMENT_CODE_IS_NOT_INTEGER = "部署コードが不正な値です。整数を入力して下さい。\n";

    public static final String NO_SELECTION = "部署を選択して下さい。";
    public static final String DO_NOT_DELETE = "部署を削除する場合は管理者に問い合わせて下さい";

    static final String TABLE_NAME = "m000_departments";
    static final String DEPARTMENT_CODE = "m000_department_code";
    static final String DEPARTMENT_NAME = "m000_department_name";
    static final String POSTCODE = "m000_postcode";
    static final String ADDRESS = "m000_address";
    static final String TEL_NUMBER = "m000_tel_number";
    static final String FAX_NUMBER = "m000_fax_number";

    private final IntegerProperty departmentCode;
    private final StringProperty departmentName;
    private final StringProperty postcode;
    private final StringProperty address;
    private final StringProperty telNumber;
    private final StringProperty faxNumber;

    public Department() {
        this(
                0,
                "",
                "",
                "",
                "",
                ""
        );
    }

    public Department(int departmentCode,
                      String departmentName,
                      String postcode,
                      String address,
                      String telNumber,
                      String faxNumber
    ) {
        this.departmentCode = new SimpleIntegerProperty(departmentCode);
        this.departmentName = new SimpleStringProperty(departmentName);
        this.postcode = new SimpleStringProperty(postcode);
        this.address = new SimpleStringProperty(address);
        this.telNumber = new SimpleStringProperty(telNumber);
        this.faxNumber = new SimpleStringProperty(faxNumber);
    }

    public static Department create(SQL sql, int departmentCodePK) throws SQLException {
        Department department = new Department();
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Department.DEPARTMENT_NAME)
                        .add(Department.POSTCODE)
                        .add(Department.ADDRESS)
                        .add(Department.TEL_NUMBER)
                        .add(Department.FAX_NUMBER))
                .from(Department.TABLE_NAME)
                .where(Department.DEPARTMENT_CODE).isEqualTo(departmentCodePK)
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            department.departmentCode.set(departmentCodePK);
            department.departmentName.set(sql.getString(Department.DEPARTMENT_NAME));
            department.postcode.set(sql.getString(Department.POSTCODE));
            department.address.set(sql.getString(Department.ADDRESS));
            department.telNumber.set(sql.getString(Department.TEL_NUMBER));
            department.faxNumber.set(sql.getString(Department.FAX_NUMBER));
            return department;
        }
        return null;
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

    public boolean saveNewData(SQL sql) throws SQLException {
        // レコードが既に存在する場合、エラーメッセージを表示する。
        if (isExisted(sql)) {
            new WarningAlert(
                    DUPLICATED_ERROR,
                    DEPARTMENT_CODE_DUPLICATED,
                    ""
            ).showAndWait();
        } else {
            // Save new data
            sql.preparedStatement(QueryBuilder.create()
                    .insert(Department.TABLE_NAME, DataPairList.create()
                            .add(Department.DEPARTMENT_CODE, getDepartmentCode())
                            .add(Department.DEPARTMENT_NAME, getDepartmentName())
                            .add(Department.POSTCODE, getPostcode())
                            .add(Department.ADDRESS, getAddress())
                            .add(Department.TEL_NUMBER, getTelNumber())
                            .add(Department.FAX_NUMBER, getFaxNumber()))
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    public void saveEditedData(SQL sql) throws SQLException {
        // レコードが存在するならば、更新する。
        if (isExisted(sql)) {
            ;
            sql.preparedStatement(QueryBuilder.create()
                    .update(Department.TABLE_NAME,
                            Department.DEPARTMENT_CODE, getDepartmentCode())
                    .addSet(Department.DEPARTMENT_NAME, getDepartmentName())
                    .addSet(Department.POSTCODE, getPostcode())
                    .addSet(Department.ADDRESS, getAddress())
                    .addSet(Department.TEL_NUMBER, getTelNumber())
                    .addSet(Department.FAX_NUMBER, getFaxNumber())
                    .where(Department.DEPARTMENT_CODE).isEqualTo(getDepartmentCode())
                    .terminate());
            sql.executeUpdate();
        }
    }

    private Boolean isExisted(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Department.DEPARTMENT_CODE)
                .from(Department.TABLE_NAME)
                .where(Department.DEPARTMENT_CODE).isEqualTo(departmentCode.get())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }
}
