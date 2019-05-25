package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by niro on 2017/08/05.
 */
public class Purchase {
    static final String TABLE_NAME = "t200_purchases";
    static final String PURCHASE_ID     = "t200_purchase_id";
    static final String ISSUE_DATE      = "t200_issue_date";
    static final String SUPPLIER_CODE   = "t200_supplier_code";
    static final String PRINTED         = "t200_is_printed";
    static final String LOCKED          = "t200_is_locked";
    static final String DELETED         = "t200_is_deleted";
    static final String LAST_UPDATE     = "t200_last_update";

    private final LongProperty purchaseId;
    private final ObjectProperty<Date> issueDate;
    private final ObjectProperty<Supplier> supplier;
    private final BooleanProperty printed;
    private final BooleanProperty locked;
    private final BooleanProperty deleted;

    public Purchase() {
        this(
                0,
                Date.valueOf(LocalDate.now()),
                "0000000000",
                false,
                false,
                false
        );
    }

    public Purchase(long purchaseId,
                    Date issueDate,
                    Supplier supplier,
                    boolean printed,
                    boolean locked,
                    boolean deleted) {
        this.purchaseId = new SimpleLongProperty(purchaseId);
        this.issueDate = new SimpleObjectProperty<Date>(issueDate);
        this.supplier = new SimpleObjectProperty<Supplier>(supplier);
        this.printed = new SimpleBooleanProperty(printed);
        this.locked = new SimpleBooleanProperty(locked);
        this.deleted = new SimpleBooleanProperty(deleted);
    }

    public Purchase(long purchaseId,
                    Date issueDate,
                    String supplierCode,
                    boolean printed,
                    boolean locked,
                    boolean deleted) {
        this(
                purchaseId,
                issueDate,
                SupplierFactory.getInstance().getSupplier(supplierCode),
                printed,
                locked,
                deleted
        );
    }

    // The getter and setter of "purchaseId"
    public long getPurchaseId() {
        return purchaseId.get();
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId.set(purchaseId);
    }

    public LongProperty purchaseIdProperty() {
        return purchaseId;
    }

    // The getter and setter of "issueDate"
    public Date getIssueDate() {
        return issueDate.get();
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate.set(issueDate);
    }

    public ObjectProperty<Date> issueDateProperty() {
        return issueDate;
    }

    // The getter and setter of "supplier"
    public Supplier getSupplier() {
        return supplier.get();
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    public ObjectProperty<Supplier> supplierProperty() {
        return supplier;
    }

    // The getter and setter of "printed"
    public boolean isPrinted() {
        return printed.get();
    }

    public void setPrinted(boolean printed) {
        this.printed.set(printed);
    }

    public BooleanProperty printedProperty() {
        return printed;
    }

    // The getter and setter of "locked"
    public boolean isLocked() {
        return locked.get();
    }

    public void setLocked(boolean locked) {
        this.locked.set(locked);
    }

    public BooleanProperty lockedProperty() {
        return locked;
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

}
