package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import jdk.nashorn.internal.ir.annotations.Immutable;
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

    public Unit() {
        this(0,"");
    }

    public Unit(int unitCode, String unitName) {
        this.unitCode = new SimpleIntegerProperty(unitCode);
        this.unitName = new SimpleStringProperty(unitName);
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


    // Save
    public void save(LoginInfo login) {
        SQL sql = null;
        try{
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.CreateSQLBuilder()
                    .select(Unit.UNIT_CODE)
                    .from(Unit.TABLE_NAME)
                    .where(Unit.UNIT_CODE).isEqualTo(unitCode.get())
                    .terminate());
            sql.executeQuery();

            if (sql.getResultSet().next()){
                // Show the error message.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                //alert.initOwner(stage);
                alert.setTitle("This unit code is duplicated.");
                alert.setHeaderText("この単位コードは既に使われています。");

                alert.showAndWait();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
