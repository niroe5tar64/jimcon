package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niro on 2017/04/12.
 */
public class Units {

    private ObservableList<Unit> unitsData = FXCollections.observableArrayList();

    public ObservableList<Unit> getUnits() {
        return unitsData;
    }

    public void loadUnitsData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            String querySelect = QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Unit.UNIT_CODE)
                            .add(Unit.UNIT_NAME))
                    .from(Unit.TABLE_NAME)
                    .orderByASC(Unit.UNIT_CODE).terminate();


            sql.preparedStatement(querySelect);
            sql.executeQuery();
            // データリストを空にしてから、Selectの結果を追加する。
            unitsData.clear();
            Unit unit = null;
            while (sql.next()) {
                unit = new Unit();
                unit.setUnitCode(sql.getResultSet().getInt(Unit.UNIT_CODE));
                unit.setUnitName(sql.getResultSet().getString(Unit.UNIT_NAME));
                unitsData.add(unit);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    public void saveUnitsData(LoginInfo login, ObservableList<Unit> storedList) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.
                    beginTransaction();

            ValueList valuelist = ValueList.create();
            for (Unit unit : storedList) {
                valuelist.add(unit.getUnitCode());
            }

            sql.preparedStatement(QueryBuilder.create()
                    .deleteFrom(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).in(valuelist)
                    .terminate());


            sql.preparedStatement(QueryBuilder.create()
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

            sql.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (sql != null) {
                sql.rollback();
            }
        }


        if (sql != null) {
            sql.close();
        }
    }


    private List createExistList(SQL sql) throws SQLException {
        ValueList valuelist = ValueList.create();
        for (Unit unit : unitsData) {
            valuelist.add(unit.getUnitCode());
        }
        sql.preparedStatement(QueryBuilder.create()
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
        return list;
    }
}
