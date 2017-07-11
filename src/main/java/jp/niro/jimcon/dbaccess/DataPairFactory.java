package jp.niro.jimcon.dbaccess;

/**
 * Created by niro on 2017/04/15.
 */
public class DataPairFactory {
    public static DataPair create(String columnName, int value) {
        return new IntDataPair(columnName, value);
    }

    public static DataPair create(String columnName, double value) {
        return new DoubleDataPair(columnName, value);
    }

    public static DataPair create(String columnName, String value) {
        return new StringDataPair(columnName, value);
    }

    public static DataPair create(String columnName, boolean value) {
        return new BooleanDataPair(columnName, value);
    }
}
