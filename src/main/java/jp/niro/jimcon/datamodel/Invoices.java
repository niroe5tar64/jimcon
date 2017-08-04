package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/02.
 */
public class Invoices {

    private ObservableList<Invoice> invoicesData = FXCollections.observableArrayList();

    public ObservableList<Invoice> getData() {
        return invoicesData;
    }

    public void load(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Invoice.INVOICE_CODE)
                .from(Invoice.TABLE_NAME)
                .orderByASC(Invoice.INVOICE_CODE)
                .terminate());
        sql.executeQuery();
        // データリストを空にしてから、Selectの結果を追加する。
        invoicesData.clear();
        Invoice invoice = null;
        while (sql.next()) {
            invoice = InvoiceFactory.getInstance().getInvoice(
                    sql.getInt(Invoice.INVOICE_CODE));
            invoicesData.add(invoice);
        }
    }
}
