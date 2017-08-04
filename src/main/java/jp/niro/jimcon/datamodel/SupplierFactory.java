package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/08/02.
 */
public class SupplierFactory {
    private Map<String, Supplier> pool = new HashMap<>();

    private static SupplierFactory singleton = new SupplierFactory();

    private SupplierFactory(){
    }

    public static SupplierFactory getInstance() {
        return singleton;
    }

    public synchronized Supplier getSupplier(String supplierCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(supplierCode,
                k -> {
                    try {
                        return Supplier.create(SQL.create(), supplierCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );
    }
}
