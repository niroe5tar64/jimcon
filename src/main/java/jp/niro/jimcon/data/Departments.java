package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.ColumnNameList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/08.
 */
public class Departments {
    Map<Integer, Department> departmentMap = new HashMap<>();

    public Map<Integer, Department> getDepartmentMap() {
        return departmentMap;
    }

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
            departmentMap.clear();
            Department department = null;
            while (sql.next()) {
                department = new Department();
                department.setDepartmentCode(sql.getResultSet().getInt(Department.DEPARTMENT_CODE));
                department.setDepartmentName(sql.getResultSet().getString(Department.DEPARTMENT_NAME));
                departmentMap.put(department.getDepartmentCode(), department);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }
}
