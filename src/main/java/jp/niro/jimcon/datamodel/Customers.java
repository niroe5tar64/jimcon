package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/04.
 */
public class Customers {

    private ObservableList<Customer> customerData = FXCollections.observableArrayList();

    public ObservableList<Customer> getData() {
        return customerData;
    }

    public void load(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Customer.CUSTOMER_CODE)
                .from(Customer.TABLE_NAME)
                .where(Customer.DELETED).isFalse()
                .orderByASC(Customer.CUSTOMER_CODE)
                .terminate());
        sql.executeQuery();

        // データリストを空にしてから、Selectの結果を追加する。
        customerData.clear();
        Customer customer = null;
        while (sql.next()) {
            customer = CustomerFactory.getInstance().getCustomer(
                    sql.getString(Customer.CUSTOMER_CODE));
            customerData.add(customer);
        }
    }
}
