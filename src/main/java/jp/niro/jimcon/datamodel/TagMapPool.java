package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.HashMapTable;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/14.
 */
public class TagMapPool {

    private static TagMapPool singleton = new TagMapPool();

    private TagMapPool() {
    }

    public static TagMapPool getInstance() {
        return singleton;
    }

    private HashMapTable<Long, String, TagMap> tagMapsData = new HashMapTable<>();

    public HashMapTable<Long, String, TagMap> getTagMaps() {
        return tagMapsData;
    }

    public void loadTagMaps(SQL sql) throws SQLException {
        String querySelect = QueryBuilder.create()
                .select(TagMap.TAG_MAP_ID)
                .from(TagMap.TABLE_NAME)
                .where(TagMap.DELETED).isFalse()
                .orderByASC(TagMap.TAG_MAP_ID)
                .terminate();
        sql.preparedStatement(querySelect);
        sql.executeQuery();

        // データリストを空にしてから、Selectの結果を追加する。
        tagMapsData.clear();
        TagMap tagMap = null;
        while (sql.next()) {
            tagMap = TagMapFactory.getInstance().getTagMap(
                    sql.getLong(TagMap.TAG_MAP_ID));
            tagMapsData.put(tagMap.getTagId(), tagMap.getProductCode(), tagMap);
        }
    }

    public ObservableList<Tag> getTagsData(LoginInfo login, String productCode) {
        ObservableList<Tag> tagsData = FXCollections.observableArrayList();
        tagMapsData.getColumn(productCode).forEach((tagId, tagMap) -> {
            if (Validator.isNotNull(tagMap)) tagsData.add(tagMap.getTag());
        });
        return tagsData;
    }

    public ObservableList<Product> getProductsData(LoginInfo login, long tagId) {
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        tagMapsData.getRow(tagId).forEach((productCode, tagMap) -> {
            if (Validator.isNotNull(tagMap)) productsData.add(tagMap.getProduct());
        });
        return productsData;
    }
}
