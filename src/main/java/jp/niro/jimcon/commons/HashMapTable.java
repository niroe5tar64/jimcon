package jp.niro.jimcon.commons;

import jp.niro.jimcon.data.TagMap;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMapをネストすることによりテーブルを実現する。
 *
 * @param <R> 行
 * @param <C> 列
 * @param <V> 値
 */
public class HashMapTable<R, C, V> {

    private Map<R, Map<C, V>> table = new HashMap<R, Map<C, V>>();

    public void put(R rowKey, C columnKey, V value) {
        Map<C, V> row = table.computeIfAbsent(rowKey, k -> new HashMap<C, V>());
        row.put(columnKey, value);
    }

    public V get(R rowKey, C columnKey) {
        Map<C, V> row = table.get(rowKey);
        if (row == null) {
            return null;
        }
        return row.get(columnKey);
    }

    public Map<C, V> getRow(R rowKey) {
        return table.get(rowKey);
    }

    public Map<R, V> getColumn(C columnKey) {
        Map<R, V> column = new HashMap<R, V>();
        table.forEach((rowKey, cvMap) -> column.put(rowKey,cvMap.get(columnKey)));
        return column;
    }



    public void clear(){
        table.clear();
    }

    public static void main(String[] args) {
        HashMapTable<Long, String, TagMap> t = new HashMapTable<>();




    }
}
