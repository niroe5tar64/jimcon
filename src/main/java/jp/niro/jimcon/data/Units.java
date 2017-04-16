package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by niro on 2017/04/12.
 */
public class Units {

    private ObservableList<Unit> unitsData = FXCollections.observableArrayList();

    public ObservableList<Unit> getUnits() {
        return unitsData;
    }

    private void diff() throws Exception {
        ObservableList<Unit> currentData = FXCollections.observableArrayList();

        List diffList = ListUtils.subtract(currentData,unitsData);

        // DateUtil
        DateUtils.addDays(new Date(),1);
        Date aprilFool = DateUtils.parseDate("2017/04/01","YYYY/MM/DD");
        aprilFool = DateUtils.parseDate("04/01","MM/DD");

        // BeanUtil copy properties dependson property name and class
        Unit a = new Unit();
        Unit b = new Unit();
        BeanUtils.copyProperties(a,b);

        Method[] methods = a.getClass().getMethods();
        for (Method meth : methods){
            // check method name of unit a and b
            meth.getName();
            //invoke method same name of method of a and b
            // a.code -> int b.code ->string -> Exception otherwise copy properties
        }

        // if (a != null) &&
        StringUtils.isBlank(a.getUnitName());

        // use contains
        for (Unit unit :unitsData){
            if (currentData.contains(unit)){
                //add diffList
            }
        }

        // use removeall
        currentData.removeAll(unitsData);
    }

    public void loadUnitsData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            String querySelect = QueryBuilder.CreateSQLBuilder()
                    .select(ColumnNameList.create()
                            .add(Unit.UNIT_CODE)
                            .add(Unit.UNIT_NAME))
                    .from(Unit.TABLE_NAME)
                    .orderByASC(Unit.UNIT_CODE).terminate();


            sql.preparedStatement(querySelect);
            sql.executeQuery();

            while (sql.next()) {
                Unit unit = new Unit();
                unit.setUnitCode(sql.getResultSet().getInt(Unit.UNIT_CODE));
                unit.setUnitName(sql.getResultSet().getString(Unit.UNIT_NAME));
                unitsData.add(unit);
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

        if (sql != null)

        {
            sql.close();
        }

    }

    public void saveUnitsData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());


            sql.preparedStatement(QueryBuilder.CreateSQLBuilder()
                    .insert(Unit.TABLE_NAME,
                            DataPairList.create()
                                    .add(Unit.UNIT_CODE, "?")
                                    .add(Unit.UNIT_NAME, "?"))
                    .terminate());

            for (Unit unit : unitsData) {
                sql.getPreparedStatement().setInt(1, unit.getUnitCode());
                sql.getPreparedStatement().setString(2, unit.getUnitName());
                sql.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    /*private boolean isUnique(LoginInfo login) throws SQLException {
        SQL sql = new SQL(login.getConnection());


    }*/

    private List createUniqueList(SQL sql) throws SQLException {
        ValueList valuelist = ValueList.create();
        for (Unit unit : unitsData) {
            valuelist.add(unit.getUnitCode());
        }
        sql.preparedStatement(QueryBuilder.CreateSQLBuilder()
                .select(Unit.UNIT_CODE)
                .from(Unit.TABLE_NAME)
                .where(Unit.UNIT_CODE).in(valuelist)
                .orderByASC(Unit.UNIT_CODE)
                .terminate());
        sql.executeQuery();

        List<Unit> list = new ArrayList();
        for (Unit unit : unitsData) {
            unit.setUnitCode(sql.getResultSet().getInt(Unit.UNIT_CODE));
            unit.setUnitName(sql.getResultSet().getString(Unit.UNIT_NAME));

            list.add(unit);
        }
        return new ArrayList();
    }
}
