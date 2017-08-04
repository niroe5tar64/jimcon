package jp.niro.jimcon.datamodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/02.
 */
public class Invoice {
    public static final String DUPLICATED_ERROR = "Duplicated Error：請求書分類コード";

    public static final String INVOICE_CODE_DUPLICATED = "この請求書分類コードは既に使われています。\n";
    public static final String INVOICE_CODE_HAS_NOT_BEEN_REGISTERED = "この請求書分類コードは登録されていません。\n";

    public static final String INVOICE_CODE_IS_EMPTY = "請求書分類コードが空欄です。\n";
    public static final String INVOICE_NAME_IS_EMPTY = "請求書分類名が空欄です。\n";

    public static final String INVOICE_CODE_IS_NOT_IN_RANGE = "請求書分類コードが不正な値です。0～255の範囲で入力して下さい。\n";
    public static final String INVOICE_CODE_IS_NOT_INTEGER = "請求書分類コードが不正な値です。整数を入力して下さい。\n";

    static final String TABLE_NAME = "m500_invoice_categories";
    static final String INVOICE_CODE = "m500_invoice_code";
    static final String INVOICE_NAME = "m500_invoice_name";
    static final String CUTOFF_DATE = "m500_cutoff_date";

    private final IntegerProperty invoiceCode;
    private final StringProperty invoiceName;
    private final IntegerProperty cutoffDate;

    public Invoice() {
        this(0, "", 0);
    }

    public Invoice(int invoiceCode,
                   String invoiceName,
                   int cutoffDate) {
        this.invoiceCode = new SimpleIntegerProperty(invoiceCode);
        this.invoiceName = new SimpleStringProperty(invoiceName);
        this.cutoffDate = new SimpleIntegerProperty(cutoffDate);
    }

    public static Invoice create(SQL sql, int invoiceCodePK) throws SQLException {
        Invoice invoice = new Invoice();
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Invoice.INVOICE_NAME)
                        .add(Invoice.CUTOFF_DATE))
                .from(Invoice.TABLE_NAME)
                .where(Invoice.INVOICE_CODE).isEqualTo(invoiceCodePK)
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            invoice.invoiceCode.set(invoiceCodePK);
            invoice.invoiceName.set(sql.getString(Invoice.INVOICE_NAME));
            invoice.cutoffDate.set(sql.getInt(Invoice.CUTOFF_DATE));
            return invoice;
        }

        return null;
    }

    // The getter and setter of "invoiceCode"
    public int getInvoiceCode() {
        return invoiceCode.get();
    }

    public void setInvoiceCode(int invoiceCode) {
        this.invoiceCode.set(invoiceCode);
    }

    public IntegerProperty invoiceCodeProperty() {
        return invoiceCode;
    }

    // The getter and setter of "invoiceName"
    public String getInvoiceName() {
        return invoiceName.get();
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName.set(invoiceName);
    }

    public StringProperty invoiceNameProperty() {
        return invoiceName;
    }

    // The getter and setter of "cutoffDate"
    public int getCutoffDate() {
        return cutoffDate.get();
    }

    public void setCutoffDate(int cutoffDate) {
        this.cutoffDate.set(cutoffDate);
    }

    public IntegerProperty cutoffDateProperty() {
        return cutoffDate;
    }

}
