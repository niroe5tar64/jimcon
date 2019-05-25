package jp.niro.jimcon.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.dbaccess.ColumnNameList;
import jp.niro.jimcon.dbaccess.QueryBuilder;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/05.
 */
public class PurchaseSlip {
    private Purchase purchase;
    private ObservableList<PurchaseDetail> purchaseDetails = FXCollections.observableArrayList();

    public PurchaseSlip() {
        this(new Purchase(), new PurchaseDetail());
    }

    private PurchaseSlip(Purchase purchase, PurchaseDetail purchaseDetail) {
        this.purchase = purchase;
        this.purchaseDetails.add(purchaseDetail);
    }

    private PurchaseSlip(Purchase purchase, ObservableList<PurchaseDetail> purchaseDetails) {
        this.purchase = purchase;
        this.purchaseDetails = purchaseDetails;
    }

    public static PurchaseSlip create(SQL sql, long purchaseId) throws SQLException {
        return new PurchaseSlip(
                PurchaseSlip.loadPurchase(sql, purchaseId),
                PurchaseSlip.loadPurchaseDetails(sql, purchaseId));
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public ObservableList<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void load(SQL sql, long purchaseId) throws SQLException {
        this.purchase = PurchaseSlip.loadPurchase(sql, purchaseId);
        this.purchaseDetails = PurchaseSlip.loadPurchaseDetails(sql, purchaseId);
    }

    private static Purchase loadPurchase(SQL sql, long purchaseId) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(Purchase.PURCHASE_ID)
                        .add(Purchase.ISSUE_DATE)
                        .add(Purchase.SUPPLIER_CODE)
                        .add(Purchase.PRINTED)
                        .add(Purchase.LOCKED)
                        .add(Purchase.DELETED))
                .from(Purchase.TABLE_NAME)
                .where(Purchase.PURCHASE_ID).isEqualTo(purchaseId)
                .and(Purchase.DELETED).isFalse()
                .terminate());
        sql.executeQuery();

        Purchase purchase = new Purchase();
        if (sql.next()) {
            purchase.setPurchaseId(sql.getLong(Purchase.PURCHASE_ID));
            purchase.setIssueDate(sql.getDate(Purchase.ISSUE_DATE));
            purchase.setSupplier(SupplierFactory.getInstance()
                    .getSupplier(sql.getString(Purchase.SUPPLIER_CODE)));
            purchase.setPrinted(sql.getBoolean(Purchase.PRINTED));
            purchase.setDeleted(sql.getBoolean(Purchase.DELETED));
        }
        return purchase;
    }

    private static ObservableList<PurchaseDetail> loadPurchaseDetails(SQL sql, long purchaseId) throws SQLException {
        sql.preparedStatement(QueryBuilder.create()
                .select(ColumnNameList.create()
                        .add(PurchaseDetail.PURCHASE_DETAIL_ID)
                        .add(PurchaseDetail.PRODUCT_CODE)
                        .add(PurchaseDetail.PRODUCT_NAME)
                        .add(PurchaseDetail.SIZE_COLOR)
                        .add(PurchaseDetail.MODEL_NUMBER)
                        .add(PurchaseDetail.PURCHASE_AMOUNT)
                        .add(PurchaseDetail.PURCHASE_UNIT_PRICE)
                        .add(PurchaseDetail.SUBTOTAL)
                        .add(PurchaseDetail.REMARKS)
                        .add(PurchaseDetail.PENDING)
                        .add(PurchaseDetail.DELETED))
                .from(PurchaseDetail.TABLE_NAME)
                .where(PurchaseDetail.PURCHASE_ID).isEqualTo(purchaseId)
                .and(PurchaseDetail.DELETED).isFalse()
                .terminate());
        sql.executeQuery();

        ObservableList<PurchaseDetail> purchaseDetails = FXCollections.observableArrayList();

        while (sql.next()) {
            Product product = new Product();
            product = ProductFactory.getInstance()
                    .getProduct(sql.getString(PurchaseDetail.PRODUCT_CODE));

            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setPurchaseDetailId(sql.getLong(PurchaseDetail.PURCHASE_DETAIL_ID));
            purchaseDetail.setPurchaseId(purchaseId);
            purchaseDetail.setProduct(product);
            if (Validator.isNotEmpty(sql.getString(PurchaseDetail.PRODUCT_NAME))) {
                purchaseDetail.setProductName(sql.getString(PurchaseDetail.PRODUCT_NAME));
            } else {
                purchaseDetail.setProductName(product.getProductName());
            }
            if (Validator.isNotEmpty(sql.getString(PurchaseDetail.SIZE_COLOR))) {
                purchaseDetail.setSizeColor(sql.getString(PurchaseDetail.SIZE_COLOR));
            } else {
                purchaseDetail.setSizeColor(product.getSizeColor());
            }
            if (Validator.isNotEmpty(sql.getString(PurchaseDetail.MODEL_NUMBER))) {
                purchaseDetail.setModelNumber(sql.getString(PurchaseDetail.MODEL_NUMBER));
            } else {
                purchaseDetail.setModelNumber(product.getModelNumber());
            }
            purchaseDetail.setPurchaseAmount(sql.getDouble(PurchaseDetail.PURCHASE_AMOUNT));
            purchaseDetail.setPurchaseUnitPrice(sql.getDouble(PurchaseDetail.PURCHASE_UNIT_PRICE));
            purchaseDetail.setDeleted(sql.getBoolean(PurchaseDetail.DELETED));
            purchaseDetails.add(purchaseDetail);
        }
        return purchaseDetails;
    }
}