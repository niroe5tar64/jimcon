package jp.niro.jimcon.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Unit {
    static final String TABLE_NAME = "m301_unit";
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


}
