package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Invoice;
import jp.niro.jimcon.datamodel.InvoiceFactory;
import jp.niro.jimcon.datamodel.Supplier;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by niro on 2017/08/03.
 */
public class SupplierMasterEditController implements MasterEditController, InvoiceSearchable {
    public static final String FXML_NAME = "SupplierMasterEdit.fxml";
    public static final String TITLE_NAME = "仕入先編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Supplier supplier;
    private Stage stage;
    private boolean okClicked;

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;

        supplierCodeField.setText(supplier.getSupplierCode());
        supplierNameField.setText(supplier.getSupplierName());
        postcodeField.setText(supplier.getPostcode());
        addressField.setText(supplier.getAddress());
        buildingEtAlField.setText(supplier.getBuildingEtAl());
        printingName1Field.setText(supplier.getPrintingName1());
        printingName2Field.setText(supplier.getPrintingName2());
        telNumberField.setText(supplier.getTelNumber());
        faxNumberField.setText(supplier.getFaxNumber());
        invoiceSearchField.setText(Integer.toString(supplier.getInvoice().getInvoiceCode()));
        invoiceNameLabel.setText(supplier.getInvoice().getInvoiceName());
        cutoffDateLabel.setText(Integer.toString(supplier.getInvoice().getCutoffDate()));
        bankInformationField.setText(supplier.getBankInformation());
        bankTransferNameField.setText(supplier.getBankTransferName());
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

    public TextField getSupplierCodeField() {
        return supplierCodeField;
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField supplierCodeField;
    @FXML
    private TextField supplierNameField;
    @FXML
    private TextField postcodeField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField buildingEtAlField;
    @FXML
    private TextField printingName1Field;
    @FXML
    private TextField printingName2Field;
    @FXML
    private TextField telNumberField;
    @FXML
    private TextField faxNumberField;
    @FXML
    private TextField invoiceSearchField;
    @FXML
    private Button searchInvoiceButton;
    @FXML
    private Label invoiceNameLabel;
    @FXML
    private Label cutoffDateLabel;
    @FXML
    private TextField bankInformationField;
    @FXML
    private TextField bankTransferNameField;
    @FXML
    private TextField remarksField;
    @FXML
    private CheckBox deletedCheckBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // 単位コードフィールドのフォーカス喪失時、
        invoiceSearchField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue) {
                        updateDisplay(getInvoiceWithInputValid());
                    }
                }
        );
    }

    public void setEvent() {
        FXRobot robot = FXRobotFactory.createRobot(stage.getScene());

        // フォーカス移動用アクション
        ActionBean focusNext = new RobotKeyPress(robot, KeyCode.TAB);
        // ダイアログ用アクション
        ActionBean closeDialog = new ActionMasterEdit(ActionType.CLOSE, this);
        // 検索ダイアログ表示用アクション
        ActionBean showSearchInvoice = new ActionSearch(SearchType.INVOICE, this);
        // [OK][Cancel]操作用アクション
        ActionBean executeOK = new ActionMasterEdit(ActionType.OK, this);
        ActionBean executeCancel = new ActionMasterEdit(ActionType.CANCEL, this);

        // 画面上の全てのTextFieldを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> textFields = pickUpper.start(pane, TextField.class);

        // キーイベント一括設定：フォーカス移動
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog)
                .setEvent(textFields);

        textFields.forEach(textField ->
                ActionEventManager.setOnAction(focusNext).setEvent(textField));

        // 検索ダイアログ設定
        ActionEventManager.setOnAction(showSearchInvoice).setEvent(searchInvoiceButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, showSearchInvoice, true).setEvent(searchInvoiceButton);

        // [OK][Cancel]ボタンの設定
        ActionEventManager.setOnAction(executeOK).setEvent(okButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeOK).setEvent(okButton);
        ActionEventManager.setOnAction(executeCancel).setEvent(cancelButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeCancel).setEvent(cancelButton);
    }

    private void showSearchInvoiceDialog() {
        try {
            URL location = WindowManager.class.getResource(InvoiceSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(InvoiceSearchDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            InvoiceSearchDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            // InvoiceSearchDialogControllerとSupplierEditDialogControllerの紐付け
            controller.setInvoiceSearchable(this);
            controller.setEvent();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleOK() {
        if (isInputValid()) {
            supplier.setSupplierCode(supplierCodeField.getText());
            supplier.setSupplierName(supplierNameField.getText());
            supplier.setPostcode(postcodeField.getText());
            supplier.setAddress(addressField.getText());
            supplier.setBuildingEtAl(buildingEtAlField.getText());
            supplier.setPrintingName1(printingName1Field.getText());
            supplier.setPrintingName2(printingName2Field.getText());
            supplier.setTelNumber(telNumberField.getText());
            supplier.setFaxNumber(faxNumberField.getText());
            supplier.setInvoice(
                    Integer.parseInt(invoiceSearchField.getText()),
                    invoiceNameLabel.getText(),
                    Integer.parseInt(cutoffDateLabel.getText()));
            supplier.setBankInformation(bankInformationField.getText());
            supplier.setBankTransferName(bankTransferNameField.getText());
            supplier.setRemarks(remarksField.getText());
            supplier.setDeleted(deletedCheckBox.isSelected());

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

        if (Validator.isEmpty(supplierCodeField.getText())) {
            errorMessage.append(Supplier.SUPPLIER_CODE_IS_EMPTY);
        }

        try {
            long supplierCode = Long.parseLong(supplierCodeField.getText());
            if (Validator.isNotEqual(supplierCodeField.getLength(), Supplier.SUPPLIER_CODE_DIGITS)) {
                errorMessage.append(Supplier.SUPPLIER_CODE_IS_INVALID_NUMBER_OF_DIGITS);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Supplier.SUPPLIER_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(supplierNameField.getText())) {
            errorMessage.append(Supplier.SUPPLIER_NAME_IS_EMPTY);
        }

        try {
            int postcode = Integer.parseInt(addressField.getText());
            if (Validator.isNotEqual(postcodeField.getLength(), Supplier.POSTCODE_LENGTH)) {
                errorMessage.append(Supplier.POSTCODE_IS_INVALID_NUMBER_OF_DIGITS);
            }
        } catch (NumberFormatException e) {
            if (Validator.isNotEmpty(postcodeField.getText())) {
                errorMessage.append(Supplier.POSTCODE_IS_NOT_NUMBER);
            }
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

    private Invoice getInvoiceWithInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        Invoice tempInvoice = null;
        try {
            int invoiceCodePK = Integer.parseInt(invoiceSearchField.getText());
            // invoiceCodeFieldに入力されたデータがDBに保存されているかどうか
            tempInvoice = InvoiceFactory.getInstance().getInvoice(invoiceCodePK);
            if (Validator.isNull(tempInvoice)) {
                errorMessage.append(Invoice.INVOICE_CODE_HAS_NOT_BEEN_REGISTERED);
            }
            if (Validator.isNotInRange(invoiceCodePK, 0, 255)) {
                errorMessage.append(Invoice.INVOICE_CODE_IS_NOT_IN_RANGE);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Invoice.INVOICE_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isNotEmpty(errorMessage.toString())) {
            new ErrorAlert(
                    INVALID_FIELDS,
                    PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString()
            ).showAndWait();
            return InvoiceFactory.getInstance().getInvoice(0);
        }
        return tempInvoice;
    }

    @Override
    public void updateDisplay(Invoice invoice) {
        if (Validator.isNotNull(invoice)) {
            invoiceSearchField.setText(String.valueOf(invoice.getInvoiceCode()));
            invoiceNameLabel.setText(invoice.getInvoiceName());
            cutoffDateLabel.setText(String.valueOf(invoice.getCutoffDate()));
        } else {
            updateDisplay(new Invoice());
        }
    }

    @Override
    public String getSearchValue() {
        return invoiceSearchField.getText().trim();
    }

    @Override
    public void showSearchDialog(SearchType type) {
        switch (type) {
            case INVOICE:
                showSearchInvoiceDialog();
                break;
        }
    }
}
