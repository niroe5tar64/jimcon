package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.DataPairList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/02.
 */
public class Supplier {
    public static final String DUPLICATED_ERROR = "Duplicated Error：仕入先コード";
    public static final String NO_SELECTION_ERROR = "No Selection Error：仕入先コード";
    public static final String DO_NOT_DELETE_ERROR = "Don't delete";

    public static final String SUPPLIER_CODE_DUPLICATED = "この仕入先コードは既に使われています。\n";
    public static final String SUPPLIER_CODE_HAS_NOT_BEEN_REGISTERED = "この仕入先コードは登録されていません。\n";

    public static final String SUPPLIER_CODE_IS_EMPTY = "仕入先コードが空欄です。\n";
    public static final String SUPPLIER_NAME_IS_EMPTY = "仕入先名が空欄です。\n";
    public static final String POSTCODE_IS_EMPTY = "郵便番号が空欄です。\n";
    public static final String ADDRESS_IS_EMPTY = "住所が空欄です。\n";
    public static final String BUILDING_ET_AL_IS_EMPTY = "建物名等が空欄です。\n";
    public static final String PRINTING_NAME_1_IS_EMPTY = "宛名印刷１が空欄です。\n";
    public static final String PRINTING_NAME_2_IS_EMPTY = "宛名印刷２が空欄です。\n";
    public static final String TEL_NUMBER_IS_EMPTY = "電話番号が空欄です。\n";
    public static final String FAX_NUMBER_IS_EMPTY = "FAX番号が空欄です。\n";
    public static final String INVOICE_CODE_IS_EMPTY = "請求書分類コードが空欄です。\n";
    public static final String BANK_INFORMATION_IS_EMPTY = "銀行口座情報が空欄です。\n";
    public static final String BANK_TRANSFER_NAME_IS_EMPTY = "振込依頼人名が空欄です。\n";
    public static final String REMARKS_IS_EMPTY = "備考欄が空欄です。\n";

    public static final String SUPPLIER_CODE_IS_INVALID_NUMBER_OF_DIGITS = "請求書分類コードが不正な値です。10桁の値を入力して下さい。\n";
    public static final String SUPPLIER_CODE_IS_NOT_INTEGER = "請求書分類コードが不正な値です。整数を入力して下さい。\n";
    public static final String POSTCODE_IS_INVALID_NUMBER_OF_DIGITS = "郵便番号が不正な値です。7桁の値を入力して下さい。\n";
    public static final String POSTCODE_IS_NOT_NUMBER = "郵便番号には数字を入力して下さい。\n";

    public static final String NO_SELECTION = "仕入先を選択して下さい。";

    public static final int SUPPLIER_CODE_DIGITS = 10;
    public static final int POSTCODE_LENGTH = 7;

    static final String TABLE_NAME = "m200_suppliers";
    static final String SUPPLIER_CODE = "m200_supplier_code";
    static final String SUPPLIER_NAME = "m200_supplier_name";
    static final String POSTCODE = "m200_postcode";
    static final String ADDRESS = "m200_address";
    static final String BUILDING_ET_AL = "m200_building_et_al";
    static final String PRINTING_NAME_1 = "m200_printing_name_1";
    static final String PRINTING_NAME_2 = "m200_printing_name_2";
    static final String TEL_NUMBER = "m200_tel_number";
    static final String FAX_NUMBER = "m200_fax_number";
    static final String INVOICE_CODE = "m200_invoice_code";
    static final String BANK_INFORMATION = "m200_bank_information";
    static final String BANK_TRANSFER_NAME = "m200_bank_transfer_name";
    static final String REMARKS = "m200_remarks";
    static final String DELETED = "m200_is_deleted";
    static final String PREVIOUS_BILLING = "m200_previous_billing";
    static final String PURCHASE_AMOUNT = "m200_purchase_amount";
    static final String CONSUMPTION_TAX = "m200_consumption_tax";
    static final String PAY_OUT_AMOUNT = "m200_pay_out_amount";
    static final String PAY_OUT_DISCOUNT = "m200_pay_out_discount";
    static final String CARRY_FORWARD = "m200_carry_forward";
    static final String THIS_MONTH_BILLING = "m200_this_month_billing";
    static final String FIX_THIS_MONTH_BILLING = "m200_fix_this_month_billing";
    static final String LAST_UPDATE = "m200_last_update";

    private final StringProperty supplierCode;
    private final StringProperty supplierName;
    private final StringProperty postcode;
    private final StringProperty address;
    private final StringProperty buildingEtAl;
    private final StringProperty printingName1;
    private final StringProperty printingName2;
    private final StringProperty telNumber;
    private final StringProperty faxNumber;
    private final ObjectProperty<Invoice> invoice;
    private final StringProperty bankInformation;
    private final StringProperty bankTransferName;
    private final StringProperty remarks;
    private final BooleanProperty deleted;
    private final DoubleProperty previousBilling;
    private final DoubleProperty purchaseAmount;
    private final DoubleProperty consumptionTax;
    private final DoubleProperty payOutAmount;
    private final DoubleProperty payOutDiscount;
    private final DoubleProperty carryForward;
    private final DoubleProperty thisMonthBilling;
    private final DoubleProperty fixThisMonthBilling;

    public Supplier() {
        this(
                "0000000000",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                "",
                "",
                "",
                false,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00
        );
    }

    public Supplier(String supplierCode,
                    String supplierName,
                    String postcode,
                    String address,
                    String buildingEtAl,
                    String printingName1,
                    String printingName2,
                    String telNumber,
                    String faxNumber,
                    Invoice invoice,
                    String bankInformation,
                    String bankTransferName,
                    String remarks,
                    Boolean deleted,
                    Double previousBilling,
                    Double purchaseAmount,
                    Double consumptionTax,
                    Double payOutAmount,
                    Double payOutDiscount,
                    Double carryForward,
                    Double thisMonthBilling,
                    Double fixThisMonthBilling) {
        this.supplierCode = new SimpleStringProperty(supplierCode);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.postcode = new SimpleStringProperty(postcode);
        this.address = new SimpleStringProperty(address);
        this.buildingEtAl = new SimpleStringProperty(buildingEtAl);
        this.printingName1 = new SimpleStringProperty(printingName1);
        this.printingName2 = new SimpleStringProperty(printingName2);
        this.telNumber = new SimpleStringProperty(telNumber);
        this.faxNumber = new SimpleStringProperty(faxNumber);
        this.invoice = new SimpleObjectProperty<Invoice>(invoice);
        this.bankInformation = new SimpleStringProperty(bankInformation);
        this.bankTransferName = new SimpleStringProperty(bankTransferName);
        this.remarks = new SimpleStringProperty(remarks);
        this.deleted = new SimpleBooleanProperty(deleted);
        this.previousBilling = new SimpleDoubleProperty(previousBilling);
        this.purchaseAmount = new SimpleDoubleProperty(purchaseAmount);
        this.consumptionTax = new SimpleDoubleProperty(consumptionTax);
        this.payOutAmount = new SimpleDoubleProperty(payOutAmount);
        this.payOutDiscount = new SimpleDoubleProperty(payOutDiscount);
        this.carryForward = new SimpleDoubleProperty(carryForward);
        this.thisMonthBilling = new SimpleDoubleProperty(thisMonthBilling);
        this.fixThisMonthBilling = new SimpleDoubleProperty(fixThisMonthBilling);
    }

    public Supplier(String supplierCode,
                    String supplierName,
                    String postcode,
                    String address,
                    String buildingEtAl,
                    String printingName1,
                    String printingName2,
                    String telNumber,
                    String faxNumber,
                    int invoiceCode,
                    String bankInformation,
                    String bankTransferName,
                    String remarks,
                    Boolean deleted,
                    Double previousBilling,
                    Double purchaseAmount,
                    Double consumptionTax,
                    Double payOutAmount,
                    Double payOutDiscount,
                    Double carryForward,
                    Double thisMonthBilling,
                    Double fixThisMonthBilling) {
        this(
                supplierCode,
                supplierName,
                postcode,
                address,
                buildingEtAl,
                printingName1,
                printingName2,
                telNumber,
                faxNumber,
                InvoiceFactory.getInstance().getInvoice(invoiceCode),
                bankInformation,
                bankTransferName,
                remarks,
                deleted,
                previousBilling,
                purchaseAmount,
                consumptionTax,
                payOutAmount,
                payOutDiscount,
                carryForward,
                thisMonthBilling,
                fixThisMonthBilling
        );
    }

    public static Supplier create(SQL sql, String supplierCodePK) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Supplier.SUPPLIER_NAME)
                        .add(Supplier.POSTCODE)
                        .add(Supplier.ADDRESS)
                        .add(Supplier.BUILDING_ET_AL)
                        .add(Supplier.PRINTING_NAME_1)
                        .add(Supplier.PRINTING_NAME_2)
                        .add(Supplier.TEL_NUMBER)
                        .add(Supplier.FAX_NUMBER)
                        .add(Supplier.INVOICE_CODE)
                        .add(Supplier.BANK_INFORMATION)
                        .add(Supplier.BANK_TRANSFER_NAME)
                        .add(Supplier.REMARKS)
                        .add(Supplier.DELETED)
                        .add(Supplier.PREVIOUS_BILLING)
                        .add(Supplier.PURCHASE_AMOUNT)
                        .add(Supplier.CONSUMPTION_TAX)
                        .add(Supplier.PAY_OUT_AMOUNT)
                        .add(Supplier.PAY_OUT_DISCOUNT)
                        .add(Supplier.CARRY_FORWARD)
                        .add(Supplier.THIS_MONTH_BILLING)
                        .add(Supplier.FIX_THIS_MONTH_BILLING))
                .from(Supplier.TABLE_NAME)
                .where(Supplier.SUPPLIER_CODE).isEqualTo(supplierCodePK)
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            Supplier supplier = new Supplier();
            supplier.supplierCode.set(supplierCodePK);
            supplier.supplierName.set(sql.getString(Supplier.SUPPLIER_NAME));
            supplier.postcode.set(sql.getString(Supplier.POSTCODE));
            supplier.address.set(sql.getString(Supplier.ADDRESS));
            supplier.buildingEtAl.set(sql.getString(Supplier.BUILDING_ET_AL));
            supplier.printingName1.set(sql.getString(Supplier.PRINTING_NAME_1));
            supplier.printingName2.set(sql.getString(Supplier.PRINTING_NAME_2));
            supplier.telNumber.set(sql.getString(Supplier.TEL_NUMBER));
            supplier.faxNumber.set(sql.getString(Supplier.FAX_NUMBER));
            supplier.invoice.set(InvoiceFactory.getInstance()
                    .getInvoice(sql.getInt(Supplier.INVOICE_CODE)));
            supplier.bankInformation.set(sql.getString(Supplier.BANK_INFORMATION));
            supplier.bankTransferName.set(sql.getString(Supplier.BANK_TRANSFER_NAME));
            supplier.remarks.set(sql.getString(Supplier.REMARKS));
            supplier.deleted.set(sql.getBoolean(Supplier.DELETED));
            supplier.previousBilling.set(sql.getDouble(Supplier.PREVIOUS_BILLING));
            supplier.purchaseAmount.set(sql.getDouble(Supplier.PURCHASE_AMOUNT));
            supplier.consumptionTax.set(sql.getDouble(Supplier.CONSUMPTION_TAX));
            supplier.payOutAmount.set(sql.getDouble(Supplier.PAY_OUT_AMOUNT));
            supplier.payOutDiscount.set(sql.getDouble(Supplier.PAY_OUT_DISCOUNT));
            supplier.carryForward.set(sql.getDouble(Supplier.CARRY_FORWARD));
            supplier.thisMonthBilling.set(sql.getDouble(Supplier.THIS_MONTH_BILLING));
            supplier.fixThisMonthBilling.set(sql.getDouble(Supplier.FIX_THIS_MONTH_BILLING));
            return supplier;
        }
        return null;
    }

    // The getter and setter of "supplierCode"
    public String getSupplierCode() {
        return supplierCode.get();
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode.set(supplierCode);
    }

    public StringProperty supplierCodeProperty() {
        return supplierCode;
    }

    // The getter and setter of "supplierName"
    public String getSupplierName() {
        return supplierName.get();
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    // The getter and setter of "postcode"
    public String getPostcode() {
        return postcode.get();
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    // The getter and setter of "address"
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    // The getter and setter of "buildingEtAl"
    public String getBuildingEtAl() {
        return buildingEtAl.get();
    }

    public void setBuildingEtAl(String buildingEtAl) {
        this.buildingEtAl.set(buildingEtAl);
    }

    public StringProperty buildingEtAlProperty() {
        return buildingEtAl;
    }

    // The getter and setter of "printingName1"
    public String getPrintingName1() {
        return printingName1.get();
    }

    public void setPrintingName1(String printingName1) {
        this.printingName1.set(printingName1);
    }

    public StringProperty printingName1Property() {
        return printingName1;
    }

    // The getter and setter of "printingName2"
    public String getPrintingName2() {
        return this.printingName2.get();
    }

    public void setPrintingName2(String printingName2) {
        this.printingName2.set(printingName2);
    }

    public StringProperty printingName2Property() {
        return printingName2;
    }

    // The getter and setter of "telNumber"
    public String getTelNumber() {
        return telNumber.get();
    }

    public void setTelNumber(String telNumber) {
        this.telNumber.set(telNumber);
    }

    public StringProperty telNumberProperty() {
        return telNumber;
    }

    // The getter and setter of "faxNumber"
    public String getFaxNumber() {
        return faxNumber.get();
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber.set(faxNumber);
    }

    public StringProperty faxNumberProperty() {
        return faxNumber;
    }

    // The getter and setter of "invoice"
    public Invoice getInvoice() {
        return invoice.get();
    }

    public void setInvoice(Invoice invoice) {
        this.invoice.set(invoice);
    }

    public void setInvoice(int invoiceCode, String invoiceName, int cutoffDate) {
        this.setInvoice(new Invoice(invoiceCode, invoiceName, cutoffDate));
    }

    public ObjectProperty invoiceProperty() {
        return invoice;
    }

    // The getter and setter of "bankInformation"
    public String getBankInformation() {
        return bankInformation.get();
    }

    public void setBankInformation(String bankInformation) {
        this.bankInformation.set(bankInformation);
    }

    public StringProperty bankInformationProperty() {
        return bankInformation;
    }

    // The getter and setter of "bankTransferName"
    public String getBankTransferName() {
        return bankTransferName.get();
    }

    public void setBankTransferName(String bankTransferName) {
        this.bankTransferName.set(bankTransferName);
    }

    public StringProperty bankTransferName() {
        return bankTransferName;
    }

    // The getter and setter of "remarks"
    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    // The getter and setter of "deleted"
    public boolean isDeleted() {
        return deleted.get();
    }

    public void setDeleted(boolean deleted) {
        this.deleted.set(deleted);
    }

    public BooleanProperty deletedProperty() {
        return deleted;
    }

    // The getter and setter of "previousBilling"
    public double getPreviousBilling() {
        return previousBilling.get();
    }

    public void setPreviousBilling(double previousBilling) {
        this.previousBilling.set(previousBilling);
    }

    public DoubleProperty previousBillingProperty() {
        return previousBilling;
    }

    // The getter and setter of "purchaseAmount"
    public double getPurchaseAmount() {
        return purchaseAmount.get();
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount.set(purchaseAmount);
    }

    public DoubleProperty purchaseAmountProperty() {
        return purchaseAmount;
    }

    // The getter and setter of "consumptionTax"
    public double getConsumptionTax() {
        return consumptionTax.get();
    }

    public void setConsumptionTax(double consumptionTax) {
        this.consumptionTax.set(consumptionTax);
    }

    public DoubleProperty consumptionTaxProperty() {
        return consumptionTax;
    }

    // The getter and setter of "payOutAmount"
    public double getPayOutAmount() {
        return payOutAmount.get();
    }

    public void setPayOutAmount(double payOutAmount) {
        this.payOutAmount.set(payOutAmount);
    }

    public DoubleProperty payOutAmountProperty() {
        return payOutAmount;
    }

    // The getter and setter of "payOutDiscount"
    public double getPayOutDiscount() {
        return payOutDiscount.get();
    }

    public void setPayOutDiscount(double payOutDiscount) {
        this.payOutDiscount.set(payOutDiscount);
    }

    public DoubleProperty payOutDiscountProperty() {
        return payOutDiscount;
    }

    // The getter and setter of "carryForward"
    public double getCarryForward() {
        return carryForward.get();
    }

    public void setCarryForward(double carryForward) {
        this.carryForward.set(carryForward);
    }

    public DoubleProperty carryForwardProperty() {
        return carryForward;
    }

    // The getter and setter of "thisMonthBilling"
    public double getThisMonthBilling() {
        return thisMonthBilling.get();
    }

    public void setThisMonthBilling(double thisMonthBilling) {
        this.thisMonthBilling.set(thisMonthBilling);
    }

    public DoubleProperty thisMonthBillingProperty() {
        return thisMonthBilling;
    }

    // The getter and setter of "fixThisMonthBilling"
    public double getFixThisMonthBilling() {
        return fixThisMonthBilling.get();
    }

    public void setFixThisMonthBilling(double fixThisMonthBilling) {
        this.fixThisMonthBilling.set(fixThisMonthBilling);
    }

    public DoubleProperty fixThisMonthBillingProperty() {
        return fixThisMonthBilling;
    }

    // Save new data.
    public boolean saveNewData(SQL sql) throws SQLException {
        // レコードが存在する時、エラーメッセージを表示する。
        if (isExisted(sql)) {
            new WarningAlert(
                    DUPLICATED_ERROR,
                    SUPPLIER_CODE_DUPLICATED,
                    ""
            ).showAndWait();
        } else {
            // Save new data.
            sql.preparedStatement(QueryBuilder.create()
                    .insert(Supplier.TABLE_NAME, DataPairList.create()
                            .add(Supplier.SUPPLIER_CODE, getSupplierCode())
                            .add(Supplier.SUPPLIER_NAME, getSupplierName())
                            .add(Supplier.POSTCODE, getPostcode())
                            .add(Supplier.ADDRESS, getAddress())
                            .add(Supplier.BUILDING_ET_AL, getBuildingEtAl())
                            .add(Supplier.PRINTING_NAME_1, getPrintingName1())
                            .add(Supplier.PRINTING_NAME_2, getPrintingName2())
                            .add(Supplier.TEL_NUMBER, getTelNumber())
                            .add(Supplier.FAX_NUMBER, getFaxNumber())
                            .add(Supplier.INVOICE_CODE, getInvoice().getInvoiceCode())
                            .add(Supplier.BANK_INFORMATION, getBankInformation())
                            .add(Supplier.BANK_TRANSFER_NAME, getBankTransferName())
                            .add(Supplier.REMARKS, getRemarks())
                            .add(Supplier.DELETED, isDeleted()))
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    public boolean saveEditData(SQL sql) throws SQLException {
        // レコードが存在するならば、更新する。
        if (isExisted(sql)) {
            // Save update data.
            sql.preparedStatement(QueryBuilder.create()
                    .update(Supplier.TABLE_NAME,
                            Supplier.SUPPLIER_CODE, getSupplierCode())
                    .addSet(Supplier.SUPPLIER_NAME, getSupplierName())
                    .addSet(Supplier.POSTCODE, getPostcode())
                    .addSet(Supplier.ADDRESS, getAddress())
                    .addSet(Supplier.BUILDING_ET_AL, getBuildingEtAl())
                    .addSet(Supplier.PRINTING_NAME_1, getPrintingName1())
                    .addSet(Supplier.PRINTING_NAME_2, getPrintingName2())
                    .addSet(Supplier.TEL_NUMBER, getTelNumber())
                    .addSet(Supplier.FAX_NUMBER, getFaxNumber())
                    .addSet(Supplier.INVOICE_CODE, getInvoice().getInvoiceCode())
                    .addSet(Supplier.BANK_INFORMATION, getBankInformation())
                    .addSet(Supplier.BANK_TRANSFER_NAME, getBankTransferName())
                    .addSet(Supplier.REMARKS, getRemarks())
                    .addSet(Supplier.DELETED, isDeleted())
                    .where(Supplier.SUPPLIER_CODE).isEqualTo(getSupplierCode())
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    public boolean deleteData(SQL sql) throws SQLException {
        if (isExisted(sql)) {
            // Delete data
            sql.preparedStatement(QueryBuilder.create()
                    .deleteFrom(Supplier.TABLE_NAME)
                    .where(Supplier.SUPPLIER_CODE).isEqualTo(getSupplierCode())
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }


    private boolean isExisted(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Supplier.SUPPLIER_CODE)
                .from(Supplier.TABLE_NAME)
                .where(Supplier.SUPPLIER_CODE).isEqualTo(getSupplierCode())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }

}
