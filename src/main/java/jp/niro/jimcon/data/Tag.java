package jp.niro.jimcon.data;

import javafx.beans.property.*;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.sql.*;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/13.
 */
public class Tag {
    static final String TABLE_NAME = "m310_tags";
    static final String TAG_ID = "m310_tag_id";
    static final String TAG_NAME = "m310_tag_name";
    static final String DELETED = "m310_is_deleted";

    private final LongProperty tagId;
    private final StringProperty tagName;
    private final BooleanProperty deleted;

    public Tag() {
        this(0, "", false);
    }

    public Tag(long tagId, String tagName, boolean deleted) {
        this.tagId = new SimpleLongProperty(tagId);
        this.tagName = new SimpleStringProperty(tagName);
        this.deleted = new SimpleBooleanProperty(deleted);
    }

    public static Tag create(LoginInfo login, long tagIdPK) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Tag.TAG_NAME)
                            .add(Tag.DELETED))
                    .from(Tag.TABLE_NAME)
                    .where(Tag.TAG_ID).isEqualTo(tagIdPK)
                    .terminate());
            sql.executeQuery();

            if (sql.next()) {
                Tag tag = new Tag();
                tag.tagId.set(tagIdPK);
                tag.tagName.set(sql.getString(Tag.TAG_NAME));
                tag.deleted.set(sql.getBoolean(Tag.DELETED));
                return tag;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // The getter and setter of "tagId"
    public long getTagId() {
        return tagId.get();
    }

    public void setTagId(long tagId) {
        this.tagId.set(tagId);
    }

    public LongProperty tagIdProperty() {
        return tagId;
    }

    // The getter and setter of "tagName"
    public String getTagName() {
        return tagName.get();
    }

    public void setTagName(String tagName) {
        this.tagName.set(tagName);
    }

    public StringProperty tagNameProperty() {
        return tagName;
    }

    // The getter and setter of "deleted"
    public boolean isDeleted() {
        return deleted.get();
    }

    public void setDeleted(boolean deleted) {
        this.deleted.set(deleted);
    }

    public BooleanProperty deletedProperty() {
        return deleted;
    }

    // Save new data.
    public boolean saveNewData(LoginInfo login){
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            // レコードが既に存在する場合、エラーメッセージを表示する。
            if (isExisted(login)) {
                Commons.showWarningAlert(
                        Constant.ErrorMessages.Title.DUPLICATED_TAG_ID,
                        Constant.ErrorMessages.Tag.TAG_ID_DUPLICATED,
                        "",
                        true);
            } else {
                // Save new data
                sql.preparedStatement(QueryBuilder.create()
                        .insert(Tag.TABLE_NAME, DataPairList.create()
                                .add(Tag.TAG_ID, getTagId())
                                .add(Tag.TAG_NAME, getTagName()))
                        .terminate());
                sql.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void saveEditedData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            // レコードが存在するならば、更新する。
            if (isExisted(login)) {
                // Save update data.
                sql.preparedStatement(QueryBuilder.create()
                        .update(Tag.TABLE_NAME,
                                Tag.TAG_ID, getTagId())
                        .addSet(Tag.TAG_NAME, getTagName())
                        .where(Tag.TAG_ID).isEqualTo(getTagId())
                        .terminate());
                sql.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Boolean isExisted(LoginInfo login) throws SQLException {
        SQL sql = new SQL(login.getConnection());

        sql.preparedStatement(QueryBuilder.create()
                .select(Tag.TAG_ID)
                .from(Tag.TABLE_NAME)
                .where(Tag.TAG_ID).isEqualTo(tagId.get())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }
}
