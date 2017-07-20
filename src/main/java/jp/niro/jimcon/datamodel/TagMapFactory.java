package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.LoginInfo;
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

    public synchronized TagMap getTagMap(LoginInfo login, long tagMapId) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(tagMapId,
                k -> TagMap.create(login, tagMapId));
    }

    public synchronized TagMap getTagMapWithSave(LoginInfo login, Tag tag, Product product) throws SQLException {
        SQL sql = new SQL(login.getConnection());

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
            return getTagMap(login,sql.getLong(TagMap.TAG_MAP_ID));
        } else {
            System.out.println("Debug:新規TagMap作成");
            return TagMap.create(login, tag, product);
        }
    }
}
