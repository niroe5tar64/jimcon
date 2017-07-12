package jp.niro.jimcon.dbaccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niro on 2017/04/15.
 */
public class DataPairList {
    List<DataPair> dataPairList = new ArrayList();

    private DataPairList() {
    }

    private DataPairList(List<DataPair> dataPairList) {
        this.dataPairList = dataPairList;
    }

    public static DataPairList create() {
        return new DataPairList();
    }

    public DataPairList add(String columnName, int value) {
        dataPairList.add(DataPairFactory.create(columnName, value));
        return new DataPairList(dataPairList);
    }

    public DataPairList add(String columnName, long value) {
        dataPairList.add(DataPairFactory.create(columnName, value));
        return new DataPairList(dataPairList);
    }

    public DataPairList add(String columnName, double value) {
        dataPairList.add(DataPairFactory.create(columnName, value));
        return new DataPairList(dataPairList);
    }

    public DataPairList add(String columnName, String value) {
        dataPairList.add(DataPairFactory.create(columnName, value));
        return new DataPairList(dataPairList);
    }

    public DataPairList add(String columnName, boolean value) {
        dataPairList.add(DataPairFactory.create(columnName, value));
        return new DataPairList(dataPairList);
    }

    public List<DataPair> getDataPairList() {
        return this.dataPairList;
    }

}
