package jp.niro.jimcon.dbaccess;

import jp.niro.jimcon.commons.Validator;

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
        StringBuilder builder = new StringBuilder();
        if (Validator.isEqual(value, "?")) {
            return builder
                    .append(String.valueOf(value))
                    .toString();
        } else {
            return builder
                    .append("'")
                    .append(String.valueOf(value))
                    .append("'")
                    .toString();
        }

    }
}
