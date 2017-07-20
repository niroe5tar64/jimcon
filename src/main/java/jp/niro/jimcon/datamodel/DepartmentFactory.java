package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/11.
 */
public class DepartmentFactory {
    private Map<Integer, Department> pool = new HashMap<>();

    private static DepartmentFactory singleton = new DepartmentFactory();

    private DepartmentFactory() {
    }

    public static DepartmentFactory getInstance() {
        return singleton;
    }

    public synchronized Department getDepartment(int departmentCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(departmentCode,
                k -> {
                    try {
                        return Department.create(SQL.create(), departmentCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
