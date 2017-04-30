package jp.niro.jimcon.data;

import javafx.beans.property.*;
import javafx.scene.control.Alert;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.sql.DataPairList;
import jp.niro.jimcon.sql.LoginInfo;
import jp.niro.jimcon.sql.QueryBuilder;
import jp.niro.jimcon.sql.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Product {
    static final String TABLE_NAME = "m300_products";
    static final String PRODUCT_CODE = "m300_product_code";
    static final String PRODUCT_NAME = "m300_product_name";
    static final String SIZE_COLOR = "m300_size_color";
    static final String MODEL_NUMBER = "m300_model_number";
    static final String ANOTHER_NAME = "m300_another_name";
    static final String SEARCHER = "m300_searcher";
    static final String CATALOG_PRICE = "m300_catalog_price";
    static final String UNIT_CODE = "m300_unit_code";
    static final String STANDARD_UNIT_PRICE = "m300_standard_unitprice";
    static final String STOCK_QUANTITY = "m300_stock_quantity";
    static final String CUTTING_CONSTANT = "m300_cutting_constant";
    static final String FUNCTION_CONSTANT = "m300_function_constant";
    static final String MEMO = "m300_memo";
    static final String PROCESSED = "m300_is_processed";
    static final String DELETED = "m300_is_deleted";
    static final String LAST_UPDATE = "m300_last_update";

    private final StringProperty productCode;
    private final StringProperty productName;
    private final StringProperty sizeColor;
    private final StringProperty modelNumber;
    private final StringProperty anotherName;
    private final DoubleProperty catalogPrice;
    private final ObjectProperty<Unit> unit;
    private final DoubleProperty standardUnitPrice;
    private final DoubleProperty stockQuantity;
    private final DoubleProperty cuttingConstant;
    private final DoubleProperty functionConstant;
    private final StringProperty memo;
    private final BooleanProperty processed;
    private final BooleanProperty deleted;

    public Product() {
        this(
                "0000000000",
                "",
                "",
                "",
                "",
                0.00,
                0, "",
                0.00,
                0.00,
                0.000,
                0.000,
                "",
                false,
                false);
    }

    public Product(String productCode,
                   String productName,
                   String sizeColor,
                   String modelNumber,
                   String anotherName,
                   double catalogPrice,
                   Unit unit,
                   double standardUnitPrice,
                   double stockQuantity,
                   double cuttingConstant,
                   double functionConstant,
                   String memo,
                   boolean processed,
                   boolean deleted) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.sizeColor = new SimpleStringProperty(sizeColor);
        this.modelNumber = new SimpleStringProperty(modelNumber);
        this.anotherName = new SimpleStringProperty(anotherName);
        this.catalogPrice = new SimpleDoubleProperty(catalogPrice);
        this.unit = new SimpleObjectProperty<Unit>(unit);
        this.standardUnitPrice = new SimpleDoubleProperty(standardUnitPrice);
        this.stockQuantity = new SimpleDoubleProperty(stockQuantity);
        this.cuttingConstant = new SimpleDoubleProperty(cuttingConstant);
        this.functionConstant = new SimpleDoubleProperty(functionConstant);
        this.memo = new SimpleStringProperty(memo);
        this.processed = new SimpleBooleanProperty(processed);
        this.deleted = new SimpleBooleanProperty(deleted);
    }

    public Product(String productCode,
                   String productName,
                   String sizeColor,
                   String modelNumber,
                   String anotherName,
                   double catalogPrice,
                   int unitCode, String unitName,
                   double standardUnitPrice,
                   double stockQuantity,
                   double cuttingConstant,
                   double functionConstant,
                   String memo,
                   boolean processed,
                   boolean deleted) {

        this(
                productCode,
                productName,
                sizeColor,
                modelNumber,
                anotherName,
                catalogPrice,
                new Unit(unitCode, unitName),
                standardUnitPrice,
                stockQuantity,
                cuttingConstant,
                functionConstant,
                memo,
                processed,
                deleted);
    }


    // The getter and setter of "productCode"
    public String getProductCode() {
        return productCode.get();
    }

    public void setProductCode(String productCode) {
        this.productCode.set(productCode);
    }

    public StringProperty productCodeProperty() {
        return productCode;
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

    // The getter and setter of "anotherName"
    public String getAnotherName() {
        return anotherName.get();
    }

    public void setAnotherName(String anotherName) {
        this.anotherName.set(anotherName);
    }

    public StringProperty anotherNameProperty() {
        return anotherName;
    }

    // The getter and setter of "catalogPrice"
    public double getCatalogPrice() {
        return catalogPrice.get();
    }

    public void setCatalogPrice(double catalogPrice) {
        this.catalogPrice.set(catalogPrice);
    }

    public DoubleProperty catalogPriceProperty() {
        return catalogPrice;
    }

    // The getter and setter of "unit"
    public Unit getUnit() {
        return unit.get();
    }

    public void setUnit(Unit unit) {
        this.unit.set(unit);
    }

    public void setUnit(LoginInfo login, int unitCodePK) {
        this.setUnit(Unit.create(login, unitCodePK));
    }
    public void setUnit(int unitCode, String unitName) {
        this.setUnit(new Unit(unitCode,unitName));
    }

    public ObjectProperty unitProperty() {
        return unit;
    }

    // The getter and setter of "standardUnitPrice"
    public double getStandardUnitPrice() {
        return standardUnitPrice.get();
    }

    public void setStandardUnitPrice(double standardUnitPrice) {
        this.standardUnitPrice.set(standardUnitPrice);
    }

    public DoubleProperty standardUnitPriceProperty() {
        return standardUnitPrice;
    }

    // The getter and setter of "stockQuantity"
    public double getStockQuantity() {
        return stockQuantity.get();
    }

    public void setStockQuantity(double stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    public DoubleProperty stockQuantityProperty() {
        return stockQuantity;
    }

    // The getter and setter of "cuttingConstant"
    public double getCuttingConstant() {
        return cuttingConstant.get();
    }

    public void setCuttingConstant(double cuttingConstant) {
        this.cuttingConstant.set(cuttingConstant);
    }

    public DoubleProperty cuttingConstantProperty() {
        return cuttingConstant;
    }

    // The getter and setter of "functionConstant"
    public double getFunctionConstant() {
        return functionConstant.get();
    }

    public void setFunctionConstant(double functionConstant) {
        this.functionConstant.set(functionConstant);
    }

    public DoubleProperty functionConstantProperty() {
        return functionConstant;
    }

    // The getter and setter of "memo"
    public String getMemo() {
        return memo.get();
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    public StringProperty memo() {
        return memo;
    }

    // The getter and setter of "processed"
    public boolean isProcessed() {
        return processed.get();
    }

    public void setProcessed(boolean processed) {
        this.processed.set(processed);
    }

    public BooleanProperty processedProperty() {
        return processed;
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


    // Save new data.
    public boolean saveNewData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Product.PRODUCT_CODE)
                    .from(Product.TABLE_NAME)
                    .where(Product.PRODUCT_CODE).isEqualTo(getProductCode())
                    .terminate());
            sql.executeQuery();

            // レコードが存在する時、エラーメッセージを表示する。
            if (sql.getResultSet().next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Constant.ErrorMessages.Title.DUPLICATED_PRODUCT_CODE);
                alert.setHeaderText(Constant.ErrorMessages.User.PRODUCT_CODE_DUPLICATED);

                alert.showAndWait();
            } else {
                // Save new data.
                sql.preparedStatement(QueryBuilder.create()
                        .insert(Product.TABLE_NAME, DataPairList.create()
                                .add(Product.PRODUCT_CODE, getProductCode())
                                .add(Product.PRODUCT_NAME, getProductName())
                                .add(Product.SIZE_COLOR, getSizeColor())
                                .add(Product.MODEL_NUMBER, getModelNumber())
                                .add(Product.ANOTHER_NAME, getAnotherName())
                                .add(Product.CATALOG_PRICE, getCatalogPrice())
                                .add(Product.UNIT_CODE, getUnit().getUnitCode())
                                .add(Product.STANDARD_UNIT_PRICE, getStandardUnitPrice())
                                .add(Product.STOCK_QUANTITY, getStockQuantity())
                                .add(Product.CUTTING_CONSTANT, getCuttingConstant())
                                .add(Product.FUNCTION_CONSTANT, getFunctionConstant())
                                .add(Product.MEMO, getMemo())
                                .add(Product.PROCESSED, isProcessed())
                                .add(Product.DELETED, isDeleted()))
                        .terminate());
                sql.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void saveEditedData(LoginInfo login) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(Product.PRODUCT_CODE)
                    .from(Product.TABLE_NAME)
                    .where(Product.PRODUCT_CODE).isEqualTo(getProductCode())
                    .terminate());
            sql.executeQuery();

            // レコードが存在するならば、更新する。
            if (sql.getResultSet().next()) {
                // Save update data.
                sql.preparedStatement(QueryBuilder.create()
                        .update(Product.TABLE_NAME,
                                Product.PRODUCT_CODE, getProductCode())
                        .addSet(Product.PRODUCT_NAME, getProductName())
                        .addSet(Product.SIZE_COLOR, getSizeColor())
                        .addSet(Product.MODEL_NUMBER, getModelNumber())
                        .addSet(Product.ANOTHER_NAME, getAnotherName())
                        .addSet(Product.CATALOG_PRICE, getCatalogPrice())
                        .addSet(Product.UNIT_CODE, getUnit().getUnitCode())
                        .addSet(Product.STANDARD_UNIT_PRICE, getStandardUnitPrice())
                        .addSet(Product.STOCK_QUANTITY, getStockQuantity())
                        .addSet(Product.CUTTING_CONSTANT, getCuttingConstant())
                        .addSet(Product.FUNCTION_CONSTANT, getFunctionConstant())
                        .addSet(Product.MEMO, getMemo())
                        .addSet(Product.PROCESSED, isProcessed())
                        .addSet(Product.DELETED, isDeleted())
                        .where(Product.PRODUCT_CODE).isEqualTo(getProductCode())
                        .terminate());
                sql.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
