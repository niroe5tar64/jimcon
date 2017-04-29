package jp.niro.jimcon.sql;

/**
 * Created by niro on 2017/04/26.
 */
public class BooleanDataPair implements DataPair {
    String columnName;
    boolean value;

    BooleanDataPair(String columnName, boolean value) {
        this.columnName = columnName;
        this.value = value;
    }

    @Override
    public String getColumnName(){
        return columnName;
    }
    @Override
    public String getValue() {
        return String.valueOf(value);
    }
}
