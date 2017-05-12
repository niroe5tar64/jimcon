package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.sql.DataPairList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Unit {
    static final String TABLE_NAME = "m301_units";
    static final String UNIT_CODE = "m301_unit_code";
    static final String UNIT_NAME = "m301_unit_name";

    private final IntegerProperty unitCode;
    private final StringProperty unitName;

    // TODO: テスト用、後で消す。
    private static int count;

    public Unit() {
        this(0, "");
    }

    public Unit(int unitCode, String unitName) {
        this.unitCode = new SimpleIntegerProperty(unitCode);
        this.unitName = new SimpleStringProperty(unitName);

        // TODO: テスト用、後で消す。
        System.out.println("Unit: " + count++);
    }

    public static Unit create(LoginInfo login, int unitCodePK) {
        Unit unit = new Unit();
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Unit.UNIT_NAME)
                    .from(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).isEqualTo(unitCodePK)
                    .terminate());
            sql.executeQuery();

            if (sql.getResultSet().next()) {
                unit.unitCode.set(unitCodePK);
                unit.unitName.set(sql.getResultSet().getString(Unit.UNIT_NAME));
                return unit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // The getter and setter of "unitCode"
    public int getUnitCode() {
        return unitCode.get();
    }

    public void setUnitCode(int unitCode) {
        this.unitCode.set(unitCode);
    }

    public IntegerProperty unitCodeProperty() {
        return unitCode;
    }

    // The getter and setter of "unitName"
    public String getUnitName() {
        return unitName.get();
    }

    public void setUnitName(String unitName) {
        this.unitName.set(unitName);
    }

    public StringProperty unitNameProperty() {
        return unitName;
    }


    // Save new data.
    public boolean saveNewData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Unit.UNIT_CODE)
                    .from(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).isEqualTo(unitCode.get())
                    .terminate());
            sql.executeQuery();

            // レコードが既に存在する場合、エラーメッセージを表示する。
            if (sql.next()) {
                Commons.showWarningAlert(
                        Constant.ErrorMessages.Title.DUPLICATED_UNIT_CODE,
                        Constant.ErrorMessages.Unit.UNIT_CODE_DUPLICATED,
                        "",
                        true);
            } else {
                // Save new data
                sql.preparedStatement(QueryBuilder.create()
                        .insert(Unit.TABLE_NAME, DataPairList.create()
                                .add(Unit.UNIT_CODE, getUnitCode())
                                .add(Unit.UNIT_NAME, getUnitName()))
                        .terminate());
                sql.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void saveEditedData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Unit.UNIT_CODE)
                    .from(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).isEqualTo(getUnitCode())
                    .terminate());
            sql.executeQuery();

            // レコードが存在するならば、更新する。
            if (sql.getResultSet().next()) {
                // Save update data.
                sql.preparedStatement(QueryBuilder.create()
                        .update(Unit.TABLE_NAME,
                                Unit.UNIT_CODE, getUnitCode())
                        .addSet(Unit.UNIT_NAME, getUnitName())
                        .where(Unit.UNIT_CODE).isEqualTo(getUnitCode())
                        .terminate());
                sql.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
