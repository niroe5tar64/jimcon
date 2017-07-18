package jp.niro.jimcon.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.*;
import jp.niro.jimcon.dbaccess.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/16.
 */
public class ProductEditDialogWithTagController implements UnitSearchable, TagSearchable {
    public static final String FXML_NAME = "ProductEditDialogWithTag.fxml";
    public static final String TITLE_NAME = "商品編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Product product;
    private ObservableList<Tag> tagList;
    private TagMaps tagMaps = TagMaps.getInstance();
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
        unitSearchField.setText(String.valueOf(product.getUnit().getUnitCode()));
        unitNameLabel.setText(product.getUnit().getUnitName());
        standardUnitPriceField.setText(String.valueOf(product.getStandardUnitPrice()));
        stockQuantityField.setText(String.valueOf(product.getStockQuantity()));
        cuttingConstantField.setText(String.valueOf(product.getCuttingConstant()));
        functionConstantField.setText(String.valueOf(product.getFunctionConstant()));
        textArea.setText(product.getMemo());
        processedCheckBox.setSelected(product.isProcessed());
    }

    public void setTagList(ObservableList<Tag> tagList){
        this.tagList = tagList;

        tagListView.setItems(tagList);
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
            unitSearchField.setText(String.valueOf(unit.getUnitCode()));
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
    private TextField unitSearchField;
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
    private TextField tagField;
    @FXML
    private Button tagSearch;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private CheckBox processedCheckBox;

    @FXML
    private void initialize() {
        // 単位コードフィールドのフォーカス喪失時、
        unitSearchField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue) {
                        updateDisplay(getUnitWithInputValid());
                    }
                }
        );


    }

    @FXML
    private void handleSearchUnit() {
        try {
            URL location = WindowManager.class.getResource(UnitSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(UnitSearchDialogController.TITLE_NAME);
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
    private void handleSearchTag() {
        try {
            URL location = WindowManager.class.getResource(TagSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            BorderPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagSearchDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            TagSearchDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            // UnitSearchDialogControllerとProductEditDialogControllerの紐付け
            controller.setTagSearchable(this);

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
                    Integer.parseInt(unitSearchField.getText()),
                    unitNameLabel.getText());
            product.setStandardUnitPrice(Double.parseDouble(standardUnitPriceField.getText()));
            product.setStockQuantity(Double.parseDouble(stockQuantityField.getText()));
            product.setCuttingConstant(Double.parseDouble(cuttingConstantField.getText()));
            product.setFunctionConstant(Double.parseDouble(functionConstantField.getText()));
            product.setMemo(textArea.getText());
            product.setProcessed(processedCheckBox.isSelected());

            ObservableList<Tag> tagsData = tagMaps.getTagsData(LoginInfo.getInstance(), product.getProductCode());

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
            errorMessage.append(Product.PRODUCT_CODE_IS_EMPTY);
        }

        try {
            int productCode = Integer.parseInt(productCodeField.getText());
            if (Validator.isNotEqual(productCodeField.getLength(), Product.PRODUCT_CODE_DIGITS)) {
                errorMessage.append(Product.PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Product.PRODUCT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(unitSearchField.getText())) {
            errorMessage.append(Unit.UNIT_CODE_IS_EMPTY);
        }

        if (Validator.isEmpty(errorMessage.toString())) {
            return true;
        } else {
            new ErrorAlert(
                    INVALID_FIELDS,
                    PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString()
            ).showAndWait();
            return false;
        }
    }

    private Unit getUnitWithInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        Unit tempUnit = null;
        try {
            int unitCodePK = Integer.parseInt(unitSearchField.getText());
            // unitCodeFieldに入力されたデータがDBに保存されているかどうか
            tempUnit = UnitFactory.getInstance().getUnit(LoginInfo.getInstance(), unitCodePK);
            if (Validator.isNull(tempUnit)) {
                errorMessage.append(Unit.UNIT_CODE_HAS_NOT_BEEN_REGISTERED);
            }
            if (Validator.isNotInRange(unitCodePK, 0, 255)) {
                errorMessage.append(Unit.UNIT_CODE_IS_NOT_IN_RANGE);
            }

        } catch (NumberFormatException e) {
            errorMessage.append(Unit.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isNotEmpty(errorMessage.toString())) {
            new ErrorAlert(
                    INVALID_FIELDS,
                    PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString()
            ).showAndWait();
        }
        return tempUnit;
    }

    @Override
    public void updateDisplay(Tag tag) {
        // TODO tagListView.addNonDuplication(E items)
        // addNonDuplication(Tag tag)
        if (tagListView.getItems().contains(tag)) {
            System.out.println(tag.getLabelName() + "は既に選択済みです。");
        } else {
            tagListView.getItems().add(tag);
        }
    }

    @Override
    public String getSearchValue() {
        return null;
    }
}
