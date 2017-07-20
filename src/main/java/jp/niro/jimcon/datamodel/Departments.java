package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/08.
 */
public class Departments {
    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();

    public ObservableList<Department> getObservableList() {
        return departmentsData;
    }

    public void loadDepartments(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Department.DEPARTMENT_CODE)
                        .add(Department.DEPARTMENT_NAME)
                        .add(Department.POSTCODE)
                        .add(Department.ADDRESS)
                        .add(Department.TEL_NUMBER)
                        .add(Department.FAX_NUMBER))
                .from(Department.TABLE_NAME)
                .orderByASC(Department.DEPARTMENT_CODE)
                .terminate());
        sql.executeQuery();
        // データリストを空にしてから、Selectの結果を追加する。
        departmentsData.clear();

        while (sql.next()) {
            departmentsData.add(
                    DepartmentFactory.getInstance().getDepartment(
                            sql.getInt(Department.DEPARTMENT_CODE)
                    )
            );
        }
    }
}
