package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.customcomponents.ListTagCell;
import jp.niro.jimcon.datamodel.*;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
//todo 登録処理
/**
 * Created by niro on 2017/05/16.
 */
public class ProductMasterEditController implements MasterEditController,UnitSearchable, TagSearchable {
    public static final String FXML_NAME = "ProductMasterEdit.fxml";
    public static final String TITLE_NAME = "商品編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Product product;
    private ObservableList<Tag> tagList;
    private TagMapPool tagMapPool = TagMapPool.getInstance();
    private Stage stage;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    private AnchorPane pane;

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
    private TextField tagSearchField;
    @FXML
    private Button tagSearch;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private CheckBox processedCheckBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // tagListViewの表示にListTagCellを設定する。
        tagListView.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
            @Override
            public ListCell<Tag> call(ListView<Tag> arg0) {
                return new ListTagCell();
            }
        });
        // 単位コードフィールドのフォーカス喪失時、
        unitSearchField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue) {
                        updateDisplay(getUnitWithInputValid());
                    }
                }
        );

    }

    public void setEvent(){
        FXRobot robot = FXRobotFactory.createRobot(stage.getScene());

        // フォーカス移動用アクション
        ActionBeen focusNext = new RobotKeyPress(robot, KeyCode.TAB);
        // ダイアログ用アクション
        ActionBeen closeDialog = new ActionMasterEdit(ActionType.CLOSE, this);

        // 画面上の全てのTextFieldを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> textFields = pickUpper.start(pane, TextField.class);

        // KeyEvent
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog)
                .setEvent(textFields);

        textFields.forEach(textField ->
                ActionEventManager.setOnAction(focusNext).setEvent(textField));


        // [OK][Cancel]操作用アクション
        ActionBeen executeOK = new ActionMasterEdit(ActionType.OK, this);
        ActionBeen executeCancel = new ActionMasterEdit(ActionType.CANCEL, this);

        // [OK][Cancel]ボタンの
        ActionEventManager.setOnAction(executeOK).setEvent(okButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeOK).setEvent(okButton);
        ActionEventManager.setOnAction(executeCancel).setEvent(cancelButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeCancel).setEvent(cancelButton);
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
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            UnitSearchDialogController controller = loader.getController();
            controller.setStage(dialogStage);
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
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            TagSearchDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            // UnitSearchDialogControllerとProductEditDialogControllerの紐付け
            controller.setTagSearchable(this);
            controller.setTagSearchField(getSearchValue());
            controller.load();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleOK() {
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

            okClicked = true;
            stage.close();
        }
    }

    @Override
    public void handleCancel() {
        stage.close();
    }

    @Override
    public void handleClose() {
        stage.close();
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
            tempUnit = UnitFactory.getInstance().getUnit(unitCodePK);
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
        return tagSearchField.getText().trim();
    }
}
