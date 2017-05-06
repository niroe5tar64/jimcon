package jp.niro.jimcon.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.sql.ColumnNameList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/22.
 */
public class Products {

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return productData;
    }

    public void loadProductsData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            // TODO rename querySelect
            String querySelect = QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Product.PRODUCT_CODE)
                            .add(Product.PRODUCT_NAME)
                            .add(Product.SIZE_COLOR)
                            .add(Product.MODEL_NUMBER)
                            .add(Product.ANOTHER_NAME)
                            .add(Product.CATALOG_PRICE)
                            .add(Product.UNIT_CODE)
                            .add(Product.STANDARD_UNIT_PRICE)
                            .add(Product.STOCK_QUANTITY)
                            .add(Product.CUTTING_CONSTANT)
                            .add(Product.FUNCTION_CONSTANT)
                            .add(Product.MEMO)
                            .add(Product.PROCESSED)
                            .add(Product.DELETED))
                    .from(Product.TABLE_NAME)
                    .where(Product.DELETED).isFalse()
                    .orderByASC(Product.PRODUCT_CODE)
                    .terminate();

            sql.preparedStatement(querySelect);
            sql.executeQuery();

            // データリストを空にしてから、Selectの結果を追加する。
            productData.clear();
            Product product = null;
            while (sql.next()) {
                product = new Product();
                product.setProductCode(sql.getResultSet().getString(Product.PRODUCT_CODE));
                product.setProductName(sql.getResultSet().getString(Product.PRODUCT_NAME));
                product.setSizeColor(sql.getResultSet().getString(Product.SIZE_COLOR));
                product.setModelNumber(sql.getResultSet().getString(Product.MODEL_NUMBER));
                product.setAnotherName(sql.getResultSet().getString(Product.ANOTHER_NAME));
                product.setCatalogPrice(sql.getResultSet().getDouble(Product.CATALOG_PRICE));
                product.setUnit(
                        Unit.create(
                                LoginInfo.create(),
                                sql.getResultSet().getInt(Product.UNIT_CODE)));
                product.setStandardUnitPrice(sql.getResultSet().getDouble(Product.STANDARD_UNIT_PRICE));
                product.setStockQuantity(sql.getResultSet().getDouble(Product.STOCK_QUANTITY));
                product.setCuttingConstant(sql.getResultSet().getDouble(Product.CUTTING_CONSTANT));
                product.setFunctionConstant(sql.getResultSet().getDouble(Product.FUNCTION_CONSTANT));
                product.setMemo(sql.getResultSet().getString(Product.MEMO));
                product.setProcessed(sql.getResultSet().getBoolean(Product.PROCESSED));
                product.setDeleted(sql.getResultSet().getBoolean(Product.DELETED));
                productData.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

}
