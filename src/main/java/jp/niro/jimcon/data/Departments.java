package jp.niro.jimcon.data;

import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.LoginInfo;

import java.util.Map;

/**
 * Created by niro on 2017/05/08.
 */
public interface Departments {
    public ObservableList<Department> getDepartmentObservableList();
    public Map<Integer, Department> getDepartmentMap();
    public void loadDepartments(LoginInfo login);
    public void saveDepartments(LoginInfo login);
}
