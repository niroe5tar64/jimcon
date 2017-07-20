package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/13.
 */
public class Tags {

    private ObservableList<Tag> tagsData = FXCollections.observableArrayList();

    public Tags() {
    }

    public Tags(ObservableList<Tag> tagsData) {
        this.tagsData = tagsData;
    }

    public ObservableList<Tag> getTags() {
        return tagsData;
    }

    public void loadTags(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Tag.TAG_ID)
                .from(Tag.TABLE_NAME)
                .where(Tag.DELETED).isFalse()
                .orderByASC(Tag.TAG_ID)
                .terminate());
        sql.executeQuery();
        // データリストを空にしてから、Selectの結果を追加する。
        tagsData.clear();
        Tag tag = null;
        while (sql.next()) {
            tag = TagFactory.getInstance().getTag(
                    sql.getLong(Tag.TAG_ID));
            tagsData.add(tag);
        }
    }

    public void loadTags(SQL sql, String tagName) throws SQLException {
        // データリストを空にしてから、Selectの結果を追加する。
        tagsData.clear();

        // 検索値が空の場合、検索しない。
        if (Validator.isEmpty(tagName)) return;

        // タグ検索
        sql.preparedStatement(QueryBuilder.create()
                .select(Tag.TAG_ID)
                .from(Tag.TABLE_NAME)
                .where(Tag.DELETED).isFalse()
                .and(Tag.TAG_NAME).isLike(tagName)
                .orderByASC(Tag.TAG_ID)
                .terminate());
        sql.executeQuery();

        Tag tag = null;
        while (sql.next()) {
            tag = TagFactory.getInstance().getTag(
                    sql.getLong(Tag.TAG_ID));
            tagsData.add(tag);
        }
    }
}
