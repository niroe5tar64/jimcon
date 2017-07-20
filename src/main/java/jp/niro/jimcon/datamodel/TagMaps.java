package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.dbaccess.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by niro on 2017/07/18.
 */
public class TagMaps {
    private ObservableList<TagMap> tagMapData = FXCollections.observableArrayList();

    public ObservableList<TagMap> getTagMaps() {
        return tagMapData;
    }

    public void setTagMaps(ObservableList<TagMap> tagMapData) {
        this.tagMapData = tagMapData;
    }

    public void add(TagMap tagMap) {
        tagMapData.add(tagMap);
    }

    public boolean save(SQL sql) throws SQLException {
        // 現在、データベースに保存されているTagMapをリスト化する。
        ObservableList<TagMap> sourceList = FXCollections.observableArrayList();

        sql.preparedStatement(QueryBuilder.create()
                .select(TagMap.TAG_MAP_ID)
                .from(TagMap.TABLE_NAME)
                .where(TagMap.PRODUCT_CODE).isEqualTo(tagMapData.get(0).getProductCode())
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            sourceList.add(TagMapFactory.getInstance().getTagMap(
                    LoginInfo.getInstance(),
                    sql.getLong(TagMap.TAG_MAP_ID))
            );
        }
        // 削除リストを作成後、TagMapを削除する。 差集合（削除リスト ＝ 既存リスト ー 現行リスト）
        Collection<TagMap> deleteList = subtract(sourceList, tagMapData);

        if (Validator.isNotEmpty(deleteList)) {
            ValueList deleteValueList = ValueList.create();
            deleteList.forEach(item -> deleteValueList.add(item.getTagMapId()));

            sql.preparedStatement(QueryBuilder.create()
                    .deleteFrom(TagMap.TABLE_NAME)
                    .where(TagMap.TAG_MAP_ID).in(deleteValueList)
                    .terminate());
            sql.executeUpdate();
        }


        // 新規リストを作成後、TagMapを作成する。 差集合（新規リスト ＝ 現行リスト － 既存リスト）
        Collection<TagMap> createList = subtract(tagMapData, sourceList);

        if (Validator.isNotEmpty(createList)) {
            sql.preparedStatement(QueryBuilder.create()
                    .insert(TagMap.TABLE_NAME,
                            DataPairList.create()
                                    .add(TagMap.TAG_ID, "?")
                                    .add(TagMap.PRODUCT_CODE, "?")
                                    .add(TagMap.DELETED, "?"))
                    .terminate());

            for (TagMap tagMap : createList) {
                sql.setLong(1, tagMap.getTagId());
                sql.setString(2, tagMap.getProductCode());
                sql.setBoolean(3, tagMap.isDeleted());
                sql.executeUpdate();
            }
        }
        return true;
    }

    public boolean delete(SQL sql) throws SQLException {
        // 削除リストを作成後、TagMapを削除する。（削除リスト ＝ 現行リスト）
        if (Validator.isNotEmpty(tagMapData)) {
            ValueList deleteValueList = ValueList.create();
            tagMapData.forEach(item -> deleteValueList.add(item.getTagMapId()));

            sql.preparedStatement(QueryBuilder.create()
                    .deleteFrom(TagMap.TABLE_NAME)
                    .where(TagMap.TAG_MAP_ID).in(deleteValueList)
                    .terminate());
            sql.executeUpdate();
        }
        return true;
    }

    private Collection<TagMap> subtract(final Collection a, final Collection b) {
        ArrayList<TagMap> list = new ArrayList<TagMap>(a);
        for (Iterator it = b.iterator(); it.hasNext(); ) {
            list.remove(it.next());
        }
        return list;
    }

    public static void main(String[] args) {
        LoginInfo login = LoginInfo.getInstance();
        login.setLoginInfo(
                LoginInfo.DEFAULT_DRIVER,
                LoginInfo.DEFAULT_URL,
                "root",
                "nirosama26");
        TagMaps maps = new TagMaps();
        ArrayList<TagMap> tag1 = new ArrayList<>();
        tag1.add(TagMapFactory.getInstance().getTagMap(login, 1));
        tag1.add(TagMapFactory.getInstance().getTagMap(login, 2));
        tag1.add(TagMapFactory.getInstance().getTagMap(login, 3));
        tag1.add(TagMapFactory.getInstance().getTagMap(login, 4));
        tag1.add(TagMapFactory.getInstance().getTagMap(login, 5));

        ArrayList<TagMap> tag2 = new ArrayList<>();
        tag2.add(TagMapFactory.getInstance().getTagMap(login, 2));
        tag2.add(TagMapFactory.getInstance().getTagMap(login, 4));
        tag2.add(TagMapFactory.getInstance().getTagMap(login, 6));

        Collection<TagMap> a = maps.subtract(tag1, tag2);
        Collection<TagMap> b = maps.subtract(tag2, tag1);

        a.forEach(tagMap -> System.out.println("tag1: " + tagMap.getTagMapId()));

        b.forEach(tagMap -> System.out.println("tag2: " + tagMap.getTagMapId()));


    }
}
