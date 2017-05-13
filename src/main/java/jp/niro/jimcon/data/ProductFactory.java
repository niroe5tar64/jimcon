package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.LoginInfo;

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

    public synchronized Product getProduct(LoginInfo login, String productCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(productCode,
                k -> Product.create(login, productCode));
    }
}
