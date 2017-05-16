package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/14.
 */
public class TagMaps {

    private ObservableList<TagMap> tagMapsData = FXCollections.observableArrayList();

    public ObservableList<TagMap> getTagMaps() {
        return tagMapsData;
    }

    public void loadTagMaps(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

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
                tagMap = TagMapFactory.getInstance().getTagMap(LoginInfo.create(),
                        sql.getLong(TagMap.TAG_MAP_ID));
                tagMapsData.add(tagMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }
}
