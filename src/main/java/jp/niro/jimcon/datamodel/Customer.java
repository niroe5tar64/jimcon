package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.DataPairList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/04.
 */
public class Customer {
    public static final String DUPLICATED_ERROR = "Duplicated Error：得意先コード";
    public static final String NO_SELECTION_ERROR = "No Selection Error：得意先コード";
    public static final String DO_NOT_DELETE_ERROR = "Don't delete";

    public static final String CUSTOMER_CODE_DUPLICATED = "この得意先コードは既に使われています。\n";
    public static final String CUSTOMER_CODE_HAS_NOT_BEEN_REGISTERED = "この得意先コードは登録されていません。\n";

    public static final String NO_SELECTION = "仕入先を選択して下さい。";

    public static final int CUSTOMER_CODE_DIGITS = 10;
    public static final int POSTCODE_LENGTH = 7;

    public static final String TABLE_NAME = "m100_customers";
    public static final String CUSTOMER_CODE = "m100_customer_code";
    public static final String CUSTOMER_NAME = "m100_customer_name";
    public static final String POSTCODE = "m100_postcode";
    public static final String ADDRESS = "m100_address";
    public static final String BUILDING_ET_AL = "m100_building_et_al";
    public static final String PRINTING_NAME_1 = "m100_printing_name_1";
    public static final String PRINTING_NAME_2 = "m100_printing_name_2";
    public static final String TEL_NUMBER = "m100_tel_number";
    public static final String FAX_NUMBER = "m100_fax_number";
    public static final String INVOICE_CODE = "m100_invoice_code";
    public static final String BANK_INFORMATION = "m100_bank_information";
    public static final String BANK_TRANSFER_NAME = "m100_bank_transfer_name";
    public static final String PRESIDENT = "m100_president";
    public static final String REMARKS = "m100_remarks";
    public static final String DELETED = "m100_is_deleted";
    public static final String PREVIOUS_BILLING = "m100_previous_billing";
    public static final String PURCHASE_AMOUNT = "m100_sales_proceed";
    public static final String CONSUMPTION_TAX = "m100_consumption_tax";
    public static final String PAY_IN_AMOUNT = "m100_pay_in_amount";
    public static final String PAY_IN_DISCOUNT = "m100_pay_in_discount";
    public static final String CARRY_FORWARD = "m100_carryforward";
    public static final String THIS_MONTH_BILLING = "m100_this_month_billing";
    public static final String FIX_THIS_MONTH_BILLING = "m100_fix_this_month_billing";
    public static final String LAST_UPDATE = "m100_last_update";

    private final StringProperty customerCode;
    private final StringProperty customerName;
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
    private final StringProperty president;
    private final StringProperty remarks;
    private final BooleanProperty deleted;
    private final DoubleProperty previousBilling;
    private final DoubleProperty purchaseAmount;
    private final DoubleProperty consumptionTax;
    private final DoubleProperty payInAmount;
    private final DoubleProperty payInDiscount;
    private final DoubleProperty carryForward;
    private final DoubleProperty thisMonthBilling;
    private final DoubleProperty fixThisMonthBilling;

    public Customer() {
        this("0000000000",
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
                "",
                false,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00,
                0.00);
    }

    public Customer(
            String customerCode,
            String customerName,
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
            String president,
            String remarks,
            Boolean deleted,
            Double previousBilling,
            Double purchaseAmount,
            Double consumptionTax,
            Double payInAmount,
            Double payInDiscount,
            Double carryForward,
            Double thisMonthBilling,
            Double fixThisMonthBilling) {
        this.customerCode = new SimpleStringProperty(customerCode);
        this.customerName = new SimpleStringProperty(customerName);
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
        this.president = new SimpleStringProperty(president);
        this.remarks = new SimpleStringProperty(remarks);
        this.deleted = new SimpleBooleanProperty(deleted);
        this.previousBilling = new SimpleDoubleProperty(previousBilling);
        this.purchaseAmount = new SimpleDoubleProperty(purchaseAmount);
        this.consumptionTax = new SimpleDoubleProperty(consumptionTax);
        this.payInAmount = new SimpleDoubleProperty(payInAmount);
        this.payInDiscount = new SimpleDoubleProperty(payInDiscount);
        this.carryForward = new SimpleDoubleProperty(carryForward);
        this.thisMonthBilling = new SimpleDoubleProperty(thisMonthBilling);
        this.fixThisMonthBilling = new SimpleDoubleProperty(fixThisMonthBilling);
    }

    public Customer(
            String customerCode,
            String customerName,
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
            String president,
            String remarks,
            Boolean deleted,
            Double previousBilling,
            Double purchaseAmount,
            Double consumptionTax,
            Double payInAmount,
            Double payInDiscount,
            Double carryForward,
            Double thisMonthBilling,
            Double fixThisMonthBilling) {
        this(
                customerCode,
                customerName,
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
                president,
                remarks,
                deleted,
                previousBilling,
                purchaseAmount,
                consumptionTax,
                payInAmount,
                payInDiscount,
                carryForward,
                thisMonthBilling,
                fixThisMonthBilling
        );
    }

    public static Customer create(SQL sql, String customerCodePK) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Customer.CUSTOMER_NAME)
                        .add(Customer.POSTCODE)
                        .add(Customer.ADDRESS)
                        .add(Customer.BUILDING_ET_AL)
                        .add(Customer.PRINTING_NAME_1)
                        .add(Customer.PRINTING_NAME_2)
                        .add(Customer.TEL_NUMBER)
                        .add(Customer.FAX_NUMBER)
                        .add(Customer.INVOICE_CODE)
                        .add(Customer.BANK_INFORMATION)
                        .add(Customer.BANK_TRANSFER_NAME)
                        .add(Customer.PRESIDENT)
                        .add(Customer.REMARKS)
                        .add(Customer.DELETED)
                        .add(Customer.PREVIOUS_BILLING)
                        .add(Customer.PURCHASE_AMOUNT)
                        .add(Customer.CONSUMPTION_TAX)
                        .add(Customer.PAY_IN_AMOUNT)
                        .add(Customer.PAY_IN_DISCOUNT)
                        .add(Customer.CARRY_FORWARD)
                        .add(Customer.THIS_MONTH_BILLING)
                        .add(Customer.FIX_THIS_MONTH_BILLING))
                .from(Customer.TABLE_NAME)
                .where(Customer.CUSTOMER_CODE).isEqualTo(customerCodePK)
                .terminate());
        sql.executeQuery();

        if (sql.next()) {
            Customer customer = new Customer();
            customer.customerCode.set(customerCodePK);
            customer.customerName.set(sql.getString(Customer.CUSTOMER_NAME));
            customer.postcode.set(sql.getString(Customer.POSTCODE));
            customer.address.set(sql.getString(Customer.ADDRESS));
            customer.buildingEtAl.set(sql.getString(Customer.BUILDING_ET_AL));
            customer.printingName1.set(sql.getString(Customer.PRINTING_NAME_1));
            customer.printingName2.set(sql.getString(Customer.PRINTING_NAME_2));
            customer.telNumber.set(sql.getString(Customer.TEL_NUMBER));
            customer.faxNumber.set(sql.getString(Customer.FAX_NUMBER));
            customer.invoice.set(InvoiceFactory.getInstance()
                    .getInvoice(sql.getInt(Customer.INVOICE_CODE)));
            customer.bankInformation.set(sql.getString(Customer.BANK_INFORMATION));
            customer.bankTransferName.set(sql.getString(Customer.BANK_TRANSFER_NAME));
            customer.president.set(sql.getString(Customer.PRESIDENT));
            customer.remarks.set(sql.getString(Customer.REMARKS));
            customer.deleted.set(sql.getBoolean(Customer.DELETED));
            customer.previousBilling.set(sql.getDouble(Customer.PREVIOUS_BILLING));
            customer.purchaseAmount.set(sql.getDouble(Customer.PURCHASE_AMOUNT));
            customer.consumptionTax.set(sql.getDouble(Customer.CONSUMPTION_TAX));
            customer.payInAmount.set(sql.getDouble(Customer.PAY_IN_AMOUNT));
            customer.payInDiscount.set(sql.getDouble(Customer.PAY_IN_DISCOUNT));
            customer.carryForward.set(sql.getDouble(Customer.CARRY_FORWARD));
            customer.thisMonthBilling.set(sql.getDouble(Customer.THIS_MONTH_BILLING));
            customer.fixThisMonthBilling.set(sql.getDouble(Customer.FIX_THIS_MONTH_BILLING));
            return customer;
        }
        return null;
    }

    // The getter and setter of "customerCode"
    public String getCustomerCode() {
        return customerCode.get();
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode.set(customerCode);
    }

    public StringProperty customerCodeProperty() {
        return customerCode;
    }

    // The getter and setter of "customerName"
    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public StringProperty customerNameProperty() {
        return customerName;
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

    // The getter and setter of "president"
    public String getPresident() {
        return president.get();
    }

    public void setPresident(String president) {
        this.president.set(president);
    }

    public StringProperty presidentProperty() {
        return president;
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

    // The getter and setter of "payInAmount"
    public double getPayInAmount() {
        return payInAmount.get();
    }

    public void setPayInAmount(double payInAmount) {
        this.payInAmount.set(payInAmount);
    }

    public DoubleProperty payInAmountProperty() {
        return payInAmount;
    }

    // The getter and setter of "payInDiscount"
    public double getPayInDiscount() {
        return payInDiscount.get();
    }

    public void setPayInDiscount(double payInDiscount) {
        this.payInDiscount.set(payInDiscount);
    }

    public DoubleProperty payInDiscountProperty() {
        return payInDiscount;
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
                    CUSTOMER_CODE_DUPLICATED,
                    ""
            ).showAndWait();
        } else {
            // Save new data.
            sql.preparedStatement(QueryBuilder.create()
                    .insert(Customer.TABLE_NAME, DataPairList.create()
                            .add(Customer.CUSTOMER_CODE, getCustomerCode())
                            .add(Customer.CUSTOMER_NAME, getCustomerName())
                            .add(Customer.POSTCODE, getPostcode())
                            .add(Customer.ADDRESS, getAddress())
                            .add(Customer.BUILDING_ET_AL, getBuildingEtAl())
                            .add(Customer.PRINTING_NAME_1, getPrintingName1())
                            .add(Customer.PRINTING_NAME_2, getPrintingName2())
                            .add(Customer.TEL_NUMBER, getTelNumber())
                            .add(Customer.FAX_NUMBER, getFaxNumber())
                            .add(Customer.INVOICE_CODE, getInvoice().getInvoiceCode())
                            .add(Customer.BANK_INFORMATION, getBankInformation())
                            .add(Customer.BANK_TRANSFER_NAME, getBankTransferName())
                            .add(Customer.PRESIDENT, getPresident())
                            .add(Customer.REMARKS, getRemarks())
                            .add(Customer.DELETED, isDeleted()))
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
                    .update(Customer.TABLE_NAME,
                            Customer.CUSTOMER_CODE, getCustomerCode())
                    .addSet(Customer.CUSTOMER_NAME, getCustomerName())
                    .addSet(Customer.POSTCODE, getPostcode())
                    .addSet(Customer.ADDRESS, getAddress())
                    .addSet(Customer.BUILDING_ET_AL, getBuildingEtAl())
                    .addSet(Customer.PRINTING_NAME_1, getPrintingName1())
                    .addSet(Customer.PRINTING_NAME_2, getPrintingName2())
                    .addSet(Customer.TEL_NUMBER, getTelNumber())
                    .addSet(Customer.FAX_NUMBER, getFaxNumber())
                    .addSet(Customer.INVOICE_CODE, getInvoice().getInvoiceCode())
                    .addSet(Customer.BANK_INFORMATION, getBankInformation())
                    .addSet(Customer.BANK_TRANSFER_NAME, getBankTransferName())
                    .addSet(Customer.PRESIDENT, getPresident())
                    .addSet(Customer.REMARKS, getRemarks())
                    .addSet(Customer.DELETED, isDeleted())
                    .where(Customer.CUSTOMER_CODE).isEqualTo(getCustomerCode())
                    .terminate());
            sql.executeUpdate();
            return true;
        }
        return false;
    }

    private boolean isExisted(SQL sql) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(Customer.CUSTOMER_CODE)
                .from(Customer.TABLE_NAME)
                .where(Customer.CUSTOMER_CODE).isEqualTo(getCustomerCode())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }
}























