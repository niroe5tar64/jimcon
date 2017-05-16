package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.ColumnNameList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

/**
 * Created by niro on 2017/05/08.
 */
public class Departments {
    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();

    public ObservableList<Department> getObservableList() {
        return departmentsData;
    }

    public void loadDepartments(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            String querySelect = QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Department.DEPARTMENT_CODE)
                            .add(Department.DEPARTMENT_NAME)
                            .add(Department.POSTCODE)
                            .add(Department.ADDRESS)
                            .add(Department.TEL_NUMBER)
                            .add(Department.FAX_NUMBER))
                    .from(Department.TABLE_NAME)
                    .orderByASC(Department.DEPARTMENT_CODE)
                    .terminate();


            sql.preparedStatement(querySelect);
            sql.executeQuery();
            // データリストを空にしてから、Selectの結果を追加する。
            departmentsData.clear();

            while (sql.next()) {
                departmentsData.add(
                        DepartmentFactory.getInstance().getDepartment(
                                LoginInfo.create(),
                                sql.getInt(Department.DEPARTMENT_CODE)
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    public void saveDepartments(LoginInfo login) {

    }
}
