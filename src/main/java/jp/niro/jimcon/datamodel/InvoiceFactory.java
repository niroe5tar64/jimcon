package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/08/02.
 */
public class InvoiceFactory {
    private Map<Integer, Invoice> pool = new HashMap<>();

    private static InvoiceFactory singleton = new InvoiceFactory();

    private InvoiceFactory() {
    }

    public static InvoiceFactory getInstance() {
        return singleton;
    }

    public synchronized Invoice getInvoice(int invoiceCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(invoiceCode,
                k -> {
                    try {
                        return Invoice.create(SQL.create(), invoiceCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );
    }
}
