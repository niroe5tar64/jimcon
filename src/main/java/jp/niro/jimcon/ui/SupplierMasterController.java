package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Supplier;
import jp.niro.jimcon.datamodel.SupplierFactory;
import jp.niro.jimcon.datamodel.Suppliers;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/08/03.
 */
public class SupplierMasterController implements MasterController {
    public static final String FXML_NAME = "SupplierMaster.fxml";
    public static final String TITLE_NAME = "仕入先一覧";

    private SupplierFactory supplierFactory = SupplierFactory.getInstance();
    private Suppliers suppliers = new Suppliers();
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Supplier> supplierTable;
    @FXML
    private TableColumn<Supplier, String> supplierCodeColumn;
    @FXML
    private TableColumn<Supplier, String> supplierNameColumn;
    @FXML
    private Label supplierCodeLabel;
    @FXML
    private Label supplierNameLabel;
    @FXML
    private Label postcodeLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label buildingEtAlLabel;
    @FXML
    private Label printingName1Label;
    @FXML
    private Label printingName2Label;
    @FXML
    private Label telNumberLabel;
    @FXML
    private Label faxNumberLabel;
    @FXML
    private Label invoiceLabel;
    @FXML
    private Label bankInformationLabel;
    @FXML
    private Label bankTransferNameLabel;
    @FXML
    private Label remarksLabel;
    @FXML
    private CheckBox deletedCheckBox;
    /*
    @FXML
    private Label previousBillingLabel;
    @FXML
    private Label purchaseAmountLabel;
    @FXML
    private Label consumptionTaxLabel;
    @FXML
    private Label payOutAmountLabel;
    @FXML
    private Label payOutDiscountLabel;
    @FXML
    private Label carryForwardLabel;
    @FXML
    private Label thisMonthBillingLabel;
    @FXML
    private Label fixThisMonthBillingLabel;
    */
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // supplierTableの初期設定
            suppliers.load(sql);
            supplierTable.setItems(suppliers.getData());
            supplierCodeColumn.setCellValueFactory(cellData -> cellData.getValue().supplierCodeProperty());
            supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());

            showSupplierDetails(null);
            supplierTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showSupplierDetails(newValue))
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

    }


    public void setEvent() {
        // 各ダイアログ表示用アクション
        ActionBean showNew = new ActionMaster(ActionType.NEW, this);
        ActionBean showEdit = new ActionMaster(ActionType.EDIT, this);
        ActionBean showDelete = new ActionMaster(ActionType.DELETE, this);
        ActionBean closeDialog = new ActionMaster(ActionType.CLOSE, this);

        // テーブルにフォーカスがある時のキーイベント
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, true, false, true, showNew, true)
                .setOnKeyReleased(KeyCode.ENTER, showEdit, true)
                .setOnKeyReleased(KeyCode.DELETE, showDelete, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(supplierTable);

        // ボタンが押された時
        ActionEventManager.setOnAction(showNew).setEvent(newButton);
        ActionEventManager.setOnAction(showEdit).setEvent(editButton);
        ActionEventManager.setOnAction(showDelete).setEvent(deleteButton);

        // その他にフォーカスがある時
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(pane);
    }


    @Override
    public void handleNew() {
        Supplier tempSupplier = new Supplier();
        boolean isClosableDialog = false;
        SQL sql = null;
        try {
            sql = SQL.create();
            while (!isClosableDialog) {
                boolean okClicked = showSupplierEditDialog(tempSupplier, true);
                if (okClicked) {
                    // DBにデータ登録し、新規か否かの状態を取得する。
                    isClosableDialog = tempSupplier.saveNewData(sql);
                    // データテーブルをリロード
                    suppliers.load(sql);
                } else {
                    isClosableDialog = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showSupplierDetails(supplierTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        SQL sql = null;
        try {
            sql = SQL.create();
            if (selectedSupplier != null) {
                boolean okClicked = showSupplierEditDialog(selectedSupplier, false);
                if (okClicked) {
                    selectedSupplier.saveEditData(sql);
                    suppliers.load(sql);
                }

            } else {
                // Nothing selected.
                new WarningAlert(
                        Supplier.NO_SELECTION_ERROR,
                        Supplier.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showSupplierDetails(supplierTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleDelete() {

    }

    @Override
    public void handleClose() {
        stage.close();
    }

    @Override
    public void handleSearch() {
        System.out.println("未実装");
    }

    private void showSupplierDetails(Supplier supplier) {
        if (supplier != null) {
            supplierCodeLabel.setText(supplier.getSupplierCode());
            supplierNameLabel.setText(supplier.getSupplierName());
            postcodeLabel.setText(supplier.getPostcode());
            addressLabel.setText(supplier.getAddress());
            buildingEtAlLabel.setText(supplier.getBuildingEtAl());
            printingName1Label.setText(supplier.getPrintingName1());
            printingName2Label.setText(supplier.getPrintingName2());
            telNumberLabel.setText(supplier.getTelNumber());
            faxNumberLabel.setText(supplier.getFaxNumber());
            invoiceLabel.setText(supplier.getInvoice().getInvoiceName());
            bankInformationLabel.setText(supplier.getBankInformation());
            bankTransferNameLabel.setText(supplier.getBankTransferName());
            remarksLabel.setText(supplier.getRemarks());
            deletedCheckBox.setSelected(supplier.isDeleted());
        } else {
            supplierCodeLabel.setText("");
            supplierNameLabel.setText("");
            postcodeLabel.setText("");
            addressLabel.setText("");
            buildingEtAlLabel.setText("");
            printingName1Label.setText("");
            printingName2Label.setText("");
            telNumberLabel.setText("");
            faxNumberLabel.setText("");
            invoiceLabel.setText("");
            bankInformationLabel.setText("");
            bankTransferNameLabel.setText("");
            remarksLabel.setText("");
            deletedCheckBox.setSelected(false);
        }
    }

    private boolean showSupplierEditDialog(Supplier supplier, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pup-up dialog.
            URL location = WindowManager.class.getResource(SupplierMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(SupplierMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Supplier into the controller.
            SupplierMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setSupplier(supplier);
            controller.setEvent();

            // 新規の場合、仕入先コードを編集不可にする。
            controller.setEditableForPKField(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
