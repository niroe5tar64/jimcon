package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

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

    public static TagMap create(SQL sql, long tagMapIdPK) throws SQLException {
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
        return new TagMap();
    }

    public static TagMap create(Tag tag, Product product) throws SQLException {
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

    public Tag getTag() {
        return TagFactory.getInstance().getTag(getTagId());
    }

    public Product getProduct() {
        return ProductFactory.getInstance().getProduct(getProductCode());
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
