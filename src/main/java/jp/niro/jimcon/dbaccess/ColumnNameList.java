package jp.niro.jimcon.dbaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by niro on 2017/04/15.
 */
public class ColumnNameList {
    private List<String> list = new ArrayList<>();

    private ColumnNameList() {
    }

    private ColumnNameList(List<String> list) {
        this.list = list;
    }

    public static ColumnNameList create() {
        return new ColumnNameList();
    }

    public static ColumnNameList create(List<String> list) {
        return new ColumnNameList(list);
    }

    public ColumnNameList add(String column) {
        list.add(column);
        return new ColumnNameList(list);
    }

    public List<String> getColumnList() {
        return list;
    }

    String enumerate(){
        return list.stream().collect(Collectors.joining(", ", "", ""));
    }
}
