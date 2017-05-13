package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.LoginInfo;

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

    public synchronized Department getDepartment(LoginInfo login, int departmentCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(departmentCode,
                k -> Department.create(login, departmentCode));
    }

}
