package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.ColumnNameList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/08.
 */
public class DepartmentsFX implements Departments{
    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();

    @Override
    public ObservableList<Department> getDepartmentObservableList() {
        return departmentsData;
    }

    @Override
    public Map<Integer, Department> getDepartmentMap() {
        Map<Integer, Department> departmentMap = new HashMap<>();
        for(Department department : departmentsData) {
            departmentMap.put(department.getDepartmentCode(), department);
        }
        return departmentMap;
    }

    @Override
    public void loadDepartments(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            String querySelect = QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Department.DEPARTMENT_CODE)
                            .add(Department.DEPARTMENT_NAME))
                    .from(Department.TABLE_NAME)
                    .orderByASC(Department.DEPARTMENT_CODE).terminate();


            sql.preparedStatement(querySelect);
            sql.executeQuery();
            // データリストを空にしてから、Selectの結果を追加する。
            departmentsData.clear();
            Department department = null;
            while (sql.next()) {
                department = new DepartmentFX();
                department.setDepartmentCode(sql.getResultSet().getInt(Department.DEPARTMENT_CODE));
                department.setDepartmentName(sql.getResultSet().getString(Department.DEPARTMENT_NAME));
                departmentsData.add(department);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    @Override
    public void saveDepartments(LoginInfo login) {

    }
}
