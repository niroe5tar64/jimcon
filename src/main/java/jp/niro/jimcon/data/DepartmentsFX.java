package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.LoginInfo;

/**
 * Created by niro on 2017/05/08.
 */
public class DepartmentsFX {
    private Departments departments = new Departments();
    private ObservableList<DepartmentFX> observableList = FXCollections.observableArrayList();

    public ObservableList<DepartmentFX> getObservableList() {
        return observableList;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void loadDepartments(LoginInfo login) {
        departments.loadDepartments(login);
        departments.getDepartmentMap().forEach(
                (key, value) -> observableList.add(new DepartmentFX(value))
        );
    }

    public void saveDepartments(LoginInfo login) {

    }
}
