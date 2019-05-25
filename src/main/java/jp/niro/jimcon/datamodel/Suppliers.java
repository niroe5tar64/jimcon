package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/02.
 */
public class Suppliers {

    private ObservableList<Supplier> suppliersData = FXCollections.observableArrayList();

    public ObservableList<Supplier> getData() {
        return suppliersData;
    }

    public void load(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Supplier.SUPPLIER_CODE)
                .from(Supplier.TABLE_NAME)
                .where(Supplier.DELETED).isFalse()
                .orderByASC(Supplier.SUPPLIER_CODE)
                .terminate());
        sql.executeQuery();

        // データリストを空にしてから、Selectの結果を追加する。
        suppliersData.clear();
        Supplier supplier = null;
        while (sql.next()) {
            supplier = SupplierFactory.getInstance().getSupplier(
                    sql.getString(Supplier.SUPPLIER_CODE));
            suppliersData.add(supplier);
        }
    }

    public void load(SQL sql, String supplierCode) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Supplier.SUPPLIER_CODE)
                .from(Supplier.TABLE_NAME)
                .where(Supplier.DELETED).isFalse()
                .and(Supplier.SUPPLIER_CODE).isLikeForwardMatch(supplierCode)
                .orderByASC(Supplier.SUPPLIER_CODE)
                .terminate());
        sql.executeQuery();

        // データリストを空にしてから、Selectの結果を追加する。
        suppliersData.clear();
        Supplier supplier = null;
        while (sql.next()) {
            supplier = SupplierFactory.getInstance().getSupplier(
                    sql.getString(Supplier.SUPPLIER_CODE));
            suppliersData.add(supplier);
        }
    }
}
