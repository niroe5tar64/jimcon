package jp.niro.jimcon.data;

import javafx.beans.property.*;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by niro on 2017/04/11.
 */
@Immutable
public class Product {
    private final StringProperty productCode;
    private final StringProperty productName;
    private final StringProperty sizeColor;
    private final StringProperty modelNumber;
    private final StringProperty anotherName;
    private final DoubleProperty catalogPrice;
    private final ObjectProperty<Unit> unit;


    public Product(String productCode,
                   String productName,
                   String sizeColor,
                   String modelNumber,
                   String anotherName,
                   double catalogPrice,
                   Unit unit) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.sizeColor = new SimpleStringProperty(sizeColor);
        this.modelNumber = new SimpleStringProperty(modelNumber);
        this.anotherName = new SimpleStringProperty(anotherName);
        this.catalogPrice = new SimpleDoubleProperty(catalogPrice);
        this.unit = new SimpleObjectProperty<Unit>();
        
    }

    public Product(String productCode,
                   String productName,
                   String sizeColor,
                   String modelNumber,
                   String anotherName,
                   double catalogPrice,
                   int unitCode,
                   String unitName) {

        this(productCode,productName,sizeColor,modelNumber,anotherName,catalogPrice,new Unit(unitCode,unitName));

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
    // The getter and setter of "anotherName"
    // The getter and setter of "catalogPrice"
    // The getter and setter of "unit"
}
