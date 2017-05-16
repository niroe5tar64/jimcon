package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.data.Product;
import jp.niro.jimcon.data.Unit;
import jp.niro.jimcon.data.UnitFactory;
import jp.niro.jimcon.sql.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/16.
 */
public class ProductEditDialogWithTagController implements UnitSearchable {

    private Product product;
    private Stage ownerStage;
    private boolean okClicked;

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
        textArea.setText(product.getMemo());
        processedCheckBox.setSelected(product.isProcessed());
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public TextField getProductCodeField() {
        return productCodeField;
    }

    @Override
    public void updateDisplay(Unit unit) {
        if (Validator.isNotNull(unit)) {
            unitCodeField.setText(String.valueOf(unit.getUnitCode()));
            unitNameLabel.setText(unit.getUnitName());
        } else {
            updateDisplay(new Unit());
        }
    }

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
    private Button unitSearch;
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

    @FXML
    private void initialize() {
        // 単位コードフィールドのフォーカス喪失時、
        unitCodeField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue) {
                        focusOutFromUnitCodeField();
                    }
                });
    }

    @FXML
    // TODO rename method name and add javadoc
    private void focusOutFromUnitCodeField() {
        updateDisplay(getUnitWithInputValid());
    }

    @FXML
    private void handleUnitSearch() {
        try {
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.UNIT_SEARCH_DIALOG);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.PRODUCT_EDIT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            UnitSearchDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            // UnitSearchDialogControllerとProductEditDialogControllerの紐付け
            controller.setUnitSearchable(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            ownerStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        ownerStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (Validator.isEmpty(productCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.Product.PRODUCT_CODE_IS_EMPTY);
        }

        try {
            int productCode = Integer.parseInt(productCodeField.getText());
            if (Validator.isNotEqual(productCodeField.getLength(), Constant.System.PRODUCT_CODE_DIGITS)) {
                errorMessage.append(Constant.ErrorMessages.Product.PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.Product.PRODUCT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(unitCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.Unit.UNIT_CODE_IS_EMPTY);
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

    private Unit getUnitWithInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        Unit tempUnit = null;
        try {
            int unitCodePK = Integer.parseInt(unitCodeField.getText());
            // unitCodeFieldに入力されたデータがDBに保存されているかどうか
            tempUnit = UnitFactory.getInstance().getUnit(LoginInfo.create(), unitCodePK);
            if (Validator.isNull(tempUnit)) {
                errorMessage.append(Constant.ErrorMessages.Unit.UNIT_CODE_HAS_NOT_BEEN_REGISTERED);
            }
            if (Validator.isNotInRange(unitCodePK, 0, 255)) {
                errorMessage.append(Constant.ErrorMessages.Unit.UNIT_CODE_IS_NOT_IN_RANGE);
            }

        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.Unit.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isNotEmpty(errorMessage.toString())) {
            Commons.showErrorAlert(
                    Constant.ErrorMessages.Title.INVALID_FIELDS,
                    Constant.ErrorMessages.User.PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString(),
                    true);
        }
        return tempUnit;
    }
}
