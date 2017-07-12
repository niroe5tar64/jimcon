package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.DataPairList;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by niro on 2017/04/22.
 */
public class Products {

    private ObservableList<Product> productsData = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return productsData;
    }

    public void loadProducts(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            // TODO rename querySelect
            String querySelect = QueryBuilder.create()
                    .select(Product.PRODUCT_CODE)
                    .from(Product.TABLE_NAME)
                    .where(Product.DELETED).isFalse()
                    .orderByASC(Product.PRODUCT_CODE)
                    .terminate();

            sql.preparedStatement(querySelect);
            sql.executeQuery();

            // データリストを空にしてから、Selectの結果を追加する。
            productsData.clear();
            Product product = null;
            while (sql.next()) {
                product = ProductFactory.getInstance().getProduct(LoginInfo.create(),
                        sql.getString(Product.PRODUCT_CODE));
                productsData.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    public void loadProducts(LoginInfo login, List<Tag> searchedTags) {
        // タグが未入力ならアラート表示
        if (Validator.isEmpty(searchedTags)) {
            new WarningAlert(
                    "",
                    "",
                    ""
            ).showAndWait();
        } else {
            SQL sql = null;
            try {
                sql = new SQL(login.getConnection());

                DataPairList dataPairList = DataPairList.create();
                for (Tag tag:searchedTags) {
                    dataPairList.add(TagMap.TAG_ID, tag.getTagId());
                }

                // TODO rename querySelect
                String querySelect = QueryBuilder.create()
                        .select(TagMap.PRODUCT_CODE)
                        .from(TagMap.TABLE_NAME)
                        .where(TagMap.DELETED).isFalse()
                        .and(dataPairList)
                        .groupBy(TagMap.PRODUCT_CODE)
                        .havingCount(TagMap.PRODUCT_CODE).isEqualTo(searchedTags.size())
                        .orderByASC(TagMap.PRODUCT_CODE)
                        .terminate();

                sql.preparedStatement(querySelect);
                sql.executeQuery();

                // データリストを空にしてから、Selectの結果を追加する。
                productsData.clear();
                Product product = null;
                while (sql.next()) {
                    product = ProductFactory.getInstance().getProduct(LoginInfo.create(),
                            sql.getString(TagMap.PRODUCT_CODE));
                    productsData.add(product);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (sql != null) {
                sql.close();
            }
        }
    }
}
