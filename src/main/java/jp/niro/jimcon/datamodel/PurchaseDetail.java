package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;

/**
 * Created by niro on 2017/08/05.
 */
public class PurchaseDetail {
    static final String TABLE_NAME = "t201_purchase_details";
    static final String PURCHASE_DETAIL_ID = "t201_purchase_detail_id";
    static final String PURCHASE_ID = "t201_purchase_id";
    static final String PRODUCT_CODE = "t201_product_code";
    static final String PRODUCT_NAME = "t201_product_name";
    static final String SIZE_COLOR = "t201_size_color";
    static final String MODEL_NUMBER = "t201_model_number";
    static final String ANOTHER_NAME = "t201_another_name";
    static final String SEARCHER = "t201_searcher";
    static final String CATALOG_PRICE = "t201_catalog_price";
    static final String PURCHASE_AMOUNT = "t201_purchase_amount";
    static final String PURCHASE_UNIT_PRICE = "t201_purchase_unit_price";
    static final String SUBTOTAL = "t201_subtotal";
    static final String REMARKS = "t201_remarks";
    static final String PENDING = "t201_is_pending";
    static final String DELETED = "t201_is_deleted";
    static final String LAST_UPDATE = "t201_last_update";

    private final LongProperty purchaseDetailId;
    private final LongProperty purchaseId;
    private final ObjectProperty<Product> product;
    private final StringProperty productName;
    private final StringProperty sizeColor;
    private final StringProperty modelNumber;
    private final DoubleProperty purchaseAmount;
    private final DoubleProperty purchaseUnitPrice;
    private final DoubleProperty subtotal;
    private final StringProperty remarks;
    private final BooleanProperty deleted;

    public PurchaseDetail() {
        this(
                0,
                0,
                ProductFactory.getInstance().getProduct("0000000000"),
                "",
                "",
                "",
                0.00,
                0.00,
                0.00,
                "",
                false
        );
    }

    public PurchaseDetail(long    purchaseDetailId,
                          long    purchaseId,
                          Product product,
                          String  productName,
                          String  sizeColor,
                          String  modelNumber,
                          double  purchaseAmount,
                          double  purchaseUnitPrice,
                          double  subtotal,
                          String remarks,
                          boolean deleted) {
        this.purchaseDetailId = new SimpleLongProperty(purchaseDetailId);
        this.purchaseId = new SimpleLongProperty(purchaseId);
        this.product = new SimpleObjectProperty<Product>(product);
        this.productName = new SimpleStringProperty(productName);
        this.sizeColor = new SimpleStringProperty(sizeColor);
        this.modelNumber = new SimpleStringProperty(modelNumber);
        this.purchaseAmount = new SimpleDoubleProperty(purchaseAmount);
        this.purchaseUnitPrice = new SimpleDoubleProperty(purchaseUnitPrice);
        this.subtotal = new SimpleDoubleProperty(subtotal);
        this.remarks = new SimpleStringProperty(remarks);
        this.deleted = new SimpleBooleanProperty(deleted);
    }

    // The getter and setter of "purchaseDetailId"
    public long getPurchaseDetailId() {
        return purchaseDetailId.get();
    }

    public void setPurchaseDetailId(long purchaseDetailId) {
        this.purchaseDetailId.set(purchaseDetailId);
    }

    public LongProperty purchaseDetailIdProperty() {
        return purchaseDetailId;
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

    // The getter and setter of "product"
    public Product getProduct() {
        return product.get();
    }

    public void setProduct(Product product) {
        this.product.set(product);
    }

    public ObjectProperty<Product> productProperty() {
        return product;
    }

    // The getter and setter of "productName"
    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    // The getter and setter of "sizeColor"
    public String getSizeColor() {
        return sizeColor.get();
    }

    public void setSizeColor(String sizeColor) {
        this.sizeColor.set(sizeColor);
    }

    public StringProperty sizeColorProperty() {
        return sizeColor;
    }

    // The getter and setter of "modelNumber"
    public String getModelNumber() {
        return modelNumber.get();
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber.set(modelNumber);
    }

    public StringProperty modelNumberProperty() {
        return modelNumber;
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

    // The getter and setter of "purchaseUnitPrice"
    public double getPurchaseUnitPrice () {
        return purchaseUnitPrice.get();
    }

    public void setPurchaseUnitPrice(double purchaseUnitPrice){
        this.purchaseUnitPrice.set(purchaseUnitPrice);
    }

    public DoubleProperty purchaseUnitPriceProperty() {
        return purchaseUnitPrice;
    }

    // The getter and setter of "subtotal"
    public double getSubtotal() {
        return subtotal.get();
    }

    public void setSubtotal(double subtotal) {
        this.subtotal.set(subtotal);
    }

    public DoubleProperty subtotalProperty() {
        return subtotal;
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
}