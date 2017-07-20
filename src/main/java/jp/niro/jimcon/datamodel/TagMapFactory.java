package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/14.
 */
public class TagMapFactory {
    private Map<Long, TagMap> pool = new HashMap<>();

    private static TagMapFactory singleton = new TagMapFactory();

    private TagMapFactory() {
    }

    public static TagMapFactory getInstance() {
        return singleton;
    }

    public synchronized TagMap getTagMap(long tagMapId) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(tagMapId,
                k -> {
                    try {
                        return TagMap.create(SQL.create(), tagMapId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    public synchronized TagMap getTagMapWithSave(SQL sql, Tag tag, Product product) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(TagMap.TAG_MAP_ID)
                        .add(TagMap.TAG_ID)
                        .add(TagMap.PRODUCT_CODE)
                        .add(TagMap.DELETED))
                .from(TagMap.TABLE_NAME)
                .where(TagMap.TAG_ID).isEqualTo(tag.getTagId())
                .and(TagMap.PRODUCT_CODE).isEqualTo(product.getProductCode())
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            //
            return getTagMap(sql.getLong(TagMap.TAG_MAP_ID));
        } else {
            // データベースに存在しない場合、TagMapクラスを作成
            return TagMap.create(tag, product);
        }
    }
}
