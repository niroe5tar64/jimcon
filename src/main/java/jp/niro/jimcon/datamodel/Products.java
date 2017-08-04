package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.DataPairList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by niro on 2017/04/22.
 */
public class Products {
    public static final String NOT_INPUT_ERROR = "必須項目が未入力です。";
    public static final String TAG_IS_REQUIRED = "検索タグが未入力です。";

    private ObservableList<Product> productsData = FXCollections.observableArrayList();

    public ObservableList<Product> getData() {
        return productsData;
    }

    public void load(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Product.PRODUCT_CODE)
                .from(Product.TABLE_NAME)
                .where(Product.DELETED).isFalse()
                .orderByASC(Product.PRODUCT_CODE)
                .terminate());
        sql.executeQuery();

        // データリストを空にしてから、Selectの結果を追加する。
        productsData.clear();
        Product product = null;
        while (sql.next()) {
            product = ProductFactory.getInstance().getProduct(
                    sql.getString(Product.PRODUCT_CODE));
            productsData.add(product);
        }
    }

    public void load(SQL sql, List<Tag> searchedTags) throws SQLException {
        // タグが未入力ならアラート表示
        if (Validator.isEmpty(searchedTags)) {
            new WarningAlert(
                    NOT_INPUT_ERROR,
                    TAG_IS_REQUIRED,
                    ""
            ).showAndWait();
        } else {
            DataPairList dataPairList = DataPairList.create();
            for (Tag tag : searchedTags) {
                dataPairList.add(TagMap.TAG_ID, tag.getTagId());
            }

            sql.preparedStatement(QueryBuilder.create()
                    .select(TagMap.PRODUCT_CODE)
                    .from(TagMap.TABLE_NAME)
                    .where(TagMap.DELETED).isFalse()
                    .and(dataPairList)
                    .groupBy(TagMap.PRODUCT_CODE)
                    .havingCount(TagMap.PRODUCT_CODE).isEqualTo(searchedTags.size())
                    .orderByASC(TagMap.PRODUCT_CODE)
                    .terminate());
            sql.executeQuery();

            // データリストを空にしてから、Selectの結果を追加する。
            productsData.clear();
            Product product = null;
            while (sql.next()) {
                product = ProductFactory.getInstance().getProduct(
                        sql.getString(TagMap.PRODUCT_CODE));
                productsData.add(product);
            }
        }
    }
}
