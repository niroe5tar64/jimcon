package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.data.Product;
import jp.niro.jimcon.data.Unit;
import jp.niro.jimcon.sql.LoginInfo;

/**
 * Created by niro on 2017/04/17.
 */
public class ProductEditDialogController {

    @FXML
    private TextField productCodeField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField sizeColorField;
    @FXML
    private TextField modelNumberField;
    @FXML
    private TextField anotherNameField;
    @FXML
    private TextField catalogPriceField;
    @FXML
    private TextField unitCodeField;
    @FXML
    private Label unitNameLabel;
    @FXML
    private TextField standardUnitPriceField;
    @FXML
    private TextField stockQuantityField;
    @FXML
    private TextField cuttingConstantField;
    @FXML
    private TextField functionConstantField;
    @FXML
    private TextArea textArea;
    @FXML
    private CheckBox processedCheckBox;

    private Stage dialogStage;
    private Product product;
    private boolean okClicked;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProduct(Product product) {
        this.product = product;

        productCodeField.setText(product.getProductCode());
        productNameField.setText(product.getProductName());
        sizeColorField.setText(product.getSizeColor());
        modelNumberField.setText(product.getModelNumber());
        anotherNameField.setText(product.getAnotherName());
        catalogPriceField.setText(String.valueOf(product.getCatalogPrice()));
        unitCodeField.setText(String.valueOf(product.getUnit().getUnitCode()));
        unitNameLabel.setText(product.getUnit().getUnitName());
        standardUnitPriceField.setText(String.valueOf(product.getStandardUnitPrice()));
        stockQuantityField.setText(String.valueOf(product.getStockQuantity()));
        cuttingConstantField.setText(String.valueOf(product.getCuttingConstant()));
        functionConstantField.setText(String.valueOf(product.getFunctionConstant()));
        textArea.setText(String.valueOf(product.getMemo()));
        processedCheckBox.setSelected(product.isProcessed());
    }

    public TextField getProductCodeField() {
        return productCodeField;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void onEvent() {
        try {
            product.setUnit(LoginInfo.create(),
                    Integer.parseInt(unitCodeField.getText()));
            Unit tempUnit = product.getUnit();

            // unitCodeField.getTextがDBに保存されているかどうか
            if (Validator.isNotNull(tempUnit)) {
                unitNameLabel.setText(tempUnit.getUnitName());
            } else {
                System.out.println("null-po");
            }
        } catch (NumberFormatException e) {
            System.out.println("num_error");
        }
    }

    @FXML
    private void handleUnitSearch() {

    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            product.setProductCode(productCodeField.getText());
            product.setProductName(productNameField.getText());
            product.setSizeColor(sizeColorField.getText());
            product.setModelNumber(modelNumberField.getText());
            product.setAnotherName(anotherNameField.getText());
            product.setCatalogPrice(Double.parseDouble(catalogPriceField.getText()));
            product.setUnit(
                    Integer.parseInt(unitCodeField.getText()),
                    unitNameLabel.getText());
            product.setStandardUnitPrice(Double.parseDouble(standardUnitPriceField.getText()));
            product.setStockQuantity(Double.parseDouble(stockQuantityField.getText()));
            product.setCuttingConstant(Double.parseDouble(cuttingConstantField.getText()));
            product.setFunctionConstant(Double.parseDouble(functionConstantField.getText()));
            product.setMemo(textArea.getText());
            product.setProcessed(processedCheckBox.isSelected());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (Validator.isEmpty(productCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.User.PRODUCT_CODE_IS_EMPTY);
        }

        try {
            int productCode = Integer.parseInt(productCodeField.getText());
            if (Validator.isNotEqual(productCodeField.getLength(),Constant.System.PRODUCT_CODE_DIGITS)) {
                errorMessage.append(Constant.ErrorMessages.User.PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.User.PRODUCT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(unitCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.User.UNIT_CODE_IS_EMPTY);
        }

        if (Validator.isEmpty(errorMessage.toString())) {
            return true;
        } else {
            Commons.showErrorAlert(
                    Constant.ErrorMessages.Title.INVALID_FIELDS,
                    Constant.ErrorMessages.User.PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString(),
                    true);
            return false;
        }
    }
}
