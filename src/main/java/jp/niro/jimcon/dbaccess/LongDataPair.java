package jp.niro.jimcon.dbaccess;

/**
 * Created by niro on 2017/07/12.
 */
public class LongDataPair implements DataPair {
    String columnName;
    long value;

    LongDataPair(String columnName, long value) {
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
