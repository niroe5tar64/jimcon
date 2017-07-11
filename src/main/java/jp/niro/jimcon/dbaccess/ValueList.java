package jp.niro.jimcon.dbaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by niro on 2017/04/15.
 */
public class ValueList {
    List<String> list = new ArrayList();

    private ValueList() {
    }

    private ValueList(List<String> list) {
        this.list = list;
    }

    public static ValueList create() {
        return new ValueList();
    }

    public static ValueList create(List<String> list){
        return new ValueList(list);
    }

    public ValueList add(int value) {
        list.add(String.valueOf(value));
        return new ValueList(list);
    }

    public ValueList add(double value) {
        list.add(String.valueOf(value));
        return new ValueList(list);
    }

    public ValueList add(String value) {
        list.add(value);
        return new ValueList(list);
    }

    public List<String> getValueList() {
        return list;
    }

    public String enumerate(String delimiter, String prefix, String suffix){
        return list.stream().collect(Collectors.joining(delimiter,prefix,suffix));
    }
}
