package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/12.
 */
public class Units {

    private ObservableList<Unit> unitsData = FXCollections.observableArrayList();

    public ObservableList<Unit> getData() {
        return unitsData;
    }

    public void load(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Unit.UNIT_CODE)
                .from(Unit.TABLE_NAME)
                .orderByASC(Unit.UNIT_CODE)
                .terminate());
        sql.executeQuery();
        // データリストを空にしてから、Selectの結果を追加する。
        unitsData.clear();
        Unit unit = null;
        while (sql.next()) {
            unit = UnitFactory.getInstance().getUnit(
                    sql.getInt(Unit.UNIT_CODE));
            unitsData.add(unit);
        }

    }
}
