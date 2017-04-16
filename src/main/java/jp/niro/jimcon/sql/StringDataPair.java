package jp.niro.jimcon.sql;

/**
 * Created by niro on 2017/04/15.
 */
public class StringDataPair implements DataPair {
    String columnName;
    String value;

    StringDataPair(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
    }
}
