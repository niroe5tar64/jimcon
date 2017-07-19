package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.*;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/13.
 */
public class TagMap {
    public static final String TABLE_NAME = "m311_tag_maps";
    public static final String TAG_MAP_ID = "m311_tag_map_id";
    public static final String TAG_ID = "m311_tag_id";
    public static final String PRODUCT_CODE = "m311_product_code";
    public static final String DELETED = "m311_is_deleted";

    private final LongProperty tagMapId;
    private final LongProperty tagId;
    private final StringProperty productCode;
    private final BooleanProperty deleted;

    public TagMap() {
        this(0, 0, "0000000000", false);
    }

    public TagMap(long tagMapId, long tagId, String productCode, boolean deleted) {
        this.tagMapId = new SimpleLongProperty(tagMapId);
        this.tagId = new SimpleLongProperty(tagId);
        this.productCode = new SimpleStringProperty(productCode);
        this.deleted = new SimpleBooleanProperty(deleted);
    }

    public static TagMap create(LoginInfo login, long tagMapIdPK) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(TagMap.TAG_ID)
                            .add(TagMap.PRODUCT_CODE)
                            .add(TagMap.DELETED))
                    .from(TagMap.TABLE_NAME)
                    .where(TagMap.TAG_MAP_ID).isEqualTo(tagMapIdPK)
                    .terminate());
            sql.executeQuery();

            if (sql.next()) {
                TagMap tagMap = new TagMap();
                tagMap.tagMapId.set(tagMapIdPK);
                tagMap.tagId.set(sql.getLong(TagMap.TAG_ID));
                tagMap.productCode.set(sql.getString(TagMap.PRODUCT_CODE));
                tagMap.deleted.set(sql.getBoolean(TagMap.DELETED));
                return tagMap;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TagMap();
    }

    public static TagMap create(LoginInfo login, Tag tag, Product product) throws SQLException {
        TagMap tagMap = new TagMap();
        tagMap.tagId.set(tag.getTagId());
        tagMap.productCode.set(product.getProductCode());
        tagMap.deleted.set(false);
        return tagMap;
    }

    // The getter and setter of "tagMapId"
    public long getTagMapId() {
        return tagMapId.get();
    }

    public void setTagMapId(long tagMapId) {
        this.tagMapId.set(tagMapId);
    }

    public LongProperty tagMapIdProperty() {
        return tagMapId;
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

    // The getter and setter of "productCode"
    public String getProductCode() {
        return productCode.get();
    }

    public void setProductCode(String productCode) {
        this.productCode.set(productCode);
    }

    public StringProperty productCodeProperty() {
        return productCode;
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

    public Tag getTag(LoginInfo login) {
        return TagFactory.getInstance().getTag(login, getTagId());
    }

    public Product getProduct(LoginInfo login) {
        return ProductFactory.getInstance().getProduct(login, getProductCode());
    }

    public boolean saveNewData(SQL sql) throws SQLException {
        // レコードが存在する時、エラーメッセージを表示する。
        if (isExisted(sql)) {
            new WarningAlert(
                    Tag.DUPLICATED_ERROR,
                    Tag.TAG_ID_DUPLICATED,
                    ""
            ).showAndWait();
        } else {
            // Save new data.
            sql.preparedStatement(QueryBuilder.create()
                    .insert(TagMap.TABLE_NAME, DataPairList.create()
                            .add(TagMap.TAG_ID, getTagId())
                            .add(TagMap.PRODUCT_CODE, getProductCode())
                            .add(TagMap.DELETED, isDeleted()))
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    public boolean saveEditData(SQL sql) throws SQLException {
        // レコードが存在するならば、更新する。
        if (isExisted(sql)) {

        } else {
            // Save update data.
            sql.preparedStatement(QueryBuilder.create()
                    .update(TagMap.TABLE_NAME,
                            TagMap.TAG_MAP_ID, getTagMapId())
                    .addSet(TagMap.TAG_ID, getTagId())
                    .addSet(TagMap.PRODUCT_CODE, getProductCode())
                    .addSet(TagMap.DELETED, isDeleted())
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    private boolean isExisted(SQL sql) throws SQLException {

        sql.preparedStatement(QueryBuilder.create()
                .select(TagMap.TAG_MAP_ID)
                .from(TagMap.TABLE_NAME)
                .where(TagMap.TAG_ID).isEqualTo(getTagId())
                .and(TagMap.PRODUCT_CODE).isEqualTo(getProductCode())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }

}
