package jp.niro.jimcon.datamodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.DataPairList;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Unit {
    public static final String DUPLICATED_ERROR = "Duplicated Error：単位コード";

    public static final String UNIT_CODE_DUPLICATED = "この単位コードは既に使われています。\n";
    public static final String UNIT_CODE_HAS_NOT_BEEN_REGISTERED = "この単位コードは登録されていません。\n";

    public static final String UNIT_CODE_IS_EMPTY = "単位コードが空欄です。\n";
    public static final String UNIT_NAME_IS_EMPTY = "単位名が空欄です。\n";

    public static final String UNIT_CODE_IS_NOT_IN_RANGE = "単位コードが不正な値です。0～255の範囲で入力して下さい。\n";
    public static final String UNIT_CODE_IS_NOT_INTEGER = "単位コードが不正な値です。整数を入力して下さい。\n";
    public static final String UNIT_NAME_IS_TOO_LONG = "単位名が長過ぎます。7文字以内で入力して下さい。\n";

    public static final String NO_SELECTION = "単位を選択して下さい。";
    public static final String DO_NOT_DELETE = "単位を削除する場合は管理者に問い合わせて下さい";

    public static final int UNIT_NAME_LENGTH = 7;

    static final String TABLE_NAME = "m301_units";
    static final String UNIT_CODE = "m301_unit_code";
    static final String UNIT_NAME = "m301_unit_name";

    private final IntegerProperty unitCode;
    private final StringProperty unitName;

    public Unit() {
        this(0, "");
    }

    public Unit(int unitCode, String unitName) {
        this.unitCode = new SimpleIntegerProperty(unitCode);
        this.unitName = new SimpleStringProperty(unitName);
    }

    public static Unit create(LoginInfo login, int unitCodePK) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Unit.UNIT_NAME)
                    .from(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).isEqualTo(unitCodePK)
                    .terminate());
            sql.executeQuery();

            if (sql.next()) {
                Unit unit = new Unit();
                unit.unitCode.set(unitCodePK);
                unit.unitName.set(sql.getString(Unit.UNIT_NAME));
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

            // レコードが既に存在する場合、エラーメッセージを表示する。
            if (isExisted(login)) {
                new WarningAlert(
                        DUPLICATED_ERROR,
                        UNIT_CODE_DUPLICATED,
                        "")
                .showAndWait();
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

            // レコードが存在するならば、更新する。
            if (isExisted(login)) {
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

    private Boolean isExisted(LoginInfo login) throws SQLException {
        SQL sql = new SQL(login.getConnection());

        sql.preparedStatement(QueryBuilder.create()
                .select(Unit.UNIT_CODE)
                .from(Unit.TABLE_NAME)
                .where(Unit.UNIT_CODE).isEqualTo(getUnitCode())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }

}
