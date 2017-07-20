package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/12.
 */
public class ProductFactory {
    private Map<String, Product> pool = new HashMap<>();

    private static ProductFactory singleton = new ProductFactory();

    private ProductFactory() {
    }

    public static ProductFactory getInstance() {
        return singleton;
    }

    public synchronized Product getProduct(String productCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(productCode,
                k -> {
                    try {
                        return Product.create(SQL.create(), productCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
