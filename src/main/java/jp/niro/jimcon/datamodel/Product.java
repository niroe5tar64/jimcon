package jp.niro.jimcon.datamodel;

import javafx.beans.property.*;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.dbaccess.*;

import java.sql.SQLException;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Product {
    public static final String DUPLICATED_ERROR = "Duplicated Error：商品コード";

    public static final String PRODUCT_CODE_DUPLICATED = "この商品コードは既に使われています。\n";
    public static final String PRODUCT_CODE_HAS_NOT_BEEN_REGISTERED = "この商品コードは登録されていません。\n";

    public static final String PRODUCT_CODE_IS_EMPTY = "商品コードが空欄です。\n";
    public static final String PRODUCT_NAME_IS_EMPTY = "商品名が空欄です。\n";
    public static final String SIZE_COLOR_IS_EMPTY = "サイズ・色が空欄です。\n";
    public static final String MODEL_NUMBER_IS_EMPTY = "品番型式が空欄です。\n";
    public static final String ANOTHER_NAME_IS_EMPTY = "別名が空欄です。\n";
    public static final String CATALOG_PRICE_IS_EMPTY = "定価が空欄です。\n";
    public static final String STANDARD_UNIT_PRICE_IS_EMPTY = "標準価格が空欄です。\n";
    public static final String STOCK_QUANTITY_IS_EMPTY = "在庫量が空欄です。\n";
    public static final String CUTTING_CONSTANT_IS_EMPTY = "切断定数が空欄です。\n";
    public static final String FUNCTION_CONSTANT_IS_EMPTY = "働き定数が空欄です。\n";
    public static final String MEMO_IS_EMPTY = "メモ欄が空欄です。\n";

    public static final String PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS = "商品コードが不正な値です。10桁の値を入力して下さい。\n";
    public static final String PRODUCT_CODE_IS_NOT_INTEGER = "商品コードが不正な値です。整数を入力して下さい。\n";
    public static final String CATALOG_PRICE_IS_NOT_DOUBLE = "定価が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
    public static final String STANDARD_UNIT_PRICE_IS_NOT_DOUBLE = "標準価格が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
    public static final String STOCK_QUANTITY_IS_NOT_DOUBLE = "在庫量が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
    public static final String CUTTING_CONSTANT_IS_NOT_DOUBLE = "切断定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";
    public static final String FUNCTION_CONSTANT_IS_NOT_DOUBLE = "働き定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";

    public static final String NO_SELECTION = "商品を選択して下さい。";

    public static final int PRODUCT_CODE_DIGITS = 10;

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
                LoginInfo.getInstance(), 0,
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
                   LoginInfo login, int unitCode,
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
                UnitFactory.getInstance().getUnit(login, unitCode),
                standardUnitPrice,
                stockQuantity,
                cuttingConstant,
                functionConstant,
                memo,
                processed,
                deleted);
    }

    public static Product create(LoginInfo login, String productCodePK) {
        SQL sql = null;
        try {
            sql = new SQL(login.getConnection());

            sql.preparedStatement(QueryBuilder.create()
                    .select(ColumnNameList.create()
                            .add(Product.PRODUCT_CODE)
                            .add(Product.PRODUCT_NAME)
                            .add(Product.SIZE_COLOR)
                            .add(Product.MODEL_NUMBER)
                            .add(Product.ANOTHER_NAME)
                            .add(Product.CATALOG_PRICE)
                            .add(Product.UNIT_CODE)
                            .add(Product.STANDARD_UNIT_PRICE)
                            .add(Product.STOCK_QUANTITY)
                            .add(Product.CUTTING_CONSTANT)
                            .add(Product.FUNCTION_CONSTANT)
                            .add(Product.MEMO)
                            .add(Product.PROCESSED)
                            .add(Product.DELETED))
                    .from(Product.TABLE_NAME)
                    .where(Product.PRODUCT_CODE).isEqualTo(productCodePK)
                    .terminate());
            sql.executeQuery();

            if (sql.next()) {
                Product product = new Product();
                product.productCode.set(productCodePK);
                product.productName.set(sql.getString(Product.PRODUCT_NAME));
                product.sizeColor.set(sql.getString(Product.SIZE_COLOR));
                product.modelNumber.set(sql.getString(Product.MODEL_NUMBER));
                product.anotherName.set(sql.getString(Product.ANOTHER_NAME));
                product.catalogPrice.set(sql.getDouble(Product.CATALOG_PRICE));
                product.unit.set(UnitFactory.getInstance().getUnit(LoginInfo.getInstance(),
                        sql.getInt(Product.UNIT_CODE)));
                product.standardUnitPrice.set(sql.getDouble(Product.STANDARD_UNIT_PRICE));
                product.stockQuantity.set(sql.getDouble(Product.STOCK_QUANTITY));
                product.cuttingConstant.set(sql.getDouble(Product.CUTTING_CONSTANT));
                product.functionConstant.set(sql.getDouble(Product.FUNCTION_CONSTANT));
                product.memo.set(sql.getString(Product.MEMO));
                product.processed.set(sql.getBoolean(Product.PROCESSED));
                product.deleted.set(sql.getBoolean(Product.DELETED));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public void setUnit(int unitCode, String unitName) {
        this.setUnit(new Unit(unitCode, unitName));
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

            // レコードが存在する時、エラーメッセージを表示する。
            if (isExisted(login)) {
                new WarningAlert(
                        DUPLICATED_ERROR,
                        PRODUCT_CODE_DUPLICATED,
                        ""
                ).showAndWait();
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

            // レコードが存在するならば、更新する。
            if (isExisted(login)) {
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

        if (sql != null) {
            sql.close();
        }
    }

    private Boolean isExisted(LoginInfo login) throws SQLException {
        SQL sql = new SQL(login.getConnection());

        sql.preparedStatement(QueryBuilder.create()
                .select(Product.PRODUCT_CODE)
                .from(Product.TABLE_NAME)
                .where(Product.PRODUCT_CODE).isEqualTo(getProductCode())
                .terminate());
        sql.executeQuery();

        return sql.next();
    }
}
