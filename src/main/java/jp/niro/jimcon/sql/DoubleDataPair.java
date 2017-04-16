package jp.niro.jimcon.sql;

/**
 * Created by niro on 2017/04/15.
 */
public class DoubleDataPair implements DataPair {
    String columnName;
    double value;

    DoubleDataPair(String columnName, double value) {
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
