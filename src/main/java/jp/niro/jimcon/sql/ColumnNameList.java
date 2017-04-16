package jp.niro.jimcon.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by niro on 2017/04/15.
 */
public class ColumnNameList {
    List<String> list = new ArrayList();

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

    public String enumerate(String delimiter, String prefix, String suffix){
        return list.stream().collect(Collectors.joining(delimiter,prefix,suffix));
    }
}
