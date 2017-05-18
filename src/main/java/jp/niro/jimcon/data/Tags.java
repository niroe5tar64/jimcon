package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

/**
 * Created by niro on 2017/05/13.
 */
public class Tags {

    private ObservableList<Tag> tagsData = FXCollections.observableArrayList();

    public Tags(){}
    public Tags(ObservableList<Tag> tagsData){
        this.tagsData = tagsData;
    }

    public ObservableList<Tag> getTags() {
        return tagsData;
    }

    public void loadTags(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            String querySelect = QueryBuilder.create()
                    .select(Tag.TAG_ID)
                    .from(Tag.TABLE_NAME)
                    .where(Tag.DELETED).isFalse()
                    .orderByASC(Tag.TAG_ID)
                    .terminate();

            sql.preparedStatement(querySelect);
            sql.executeQuery();
            // データリストを空にしてから、Selectの結果を追加する。
            tagsData.clear();
            Tag tag = null;
            while (sql.next()) {
                tag = TagFactory.getInstance().getTag(LoginInfo.create(),
                        sql.getLong(Tag.TAG_ID));
                tagsData.add(tag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }
}
