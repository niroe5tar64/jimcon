package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/08/04.
 */
public class CustomerFactory {
    private Map<String, Customer> pool = new HashMap<>();

    private static CustomerFactory singleton = new CustomerFactory();

    private CustomerFactory() {
    }

    public static CustomerFactory getInstance() {
        return singleton;
    }

    public synchronized Customer getCustomer(String customerCode) {
        return pool.computeIfAbsent(customerCode,
                k -> {
                    try{
                        return Customer.create(SQL.create(), customerCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );
    }
}
