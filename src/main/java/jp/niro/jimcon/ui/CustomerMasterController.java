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
import jp.niro.jimcon.datamodel.Customer;
import jp.niro.jimcon.datamodel.CustomerFactory;
import jp.niro.jimcon.datamodel.Customers;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/08/04.
 */
public class CustomerMasterController implements MasterController {
    public static final String FXML_NAME = "CustomerMaster.fxml";
    public static final String TITLE_NAME = "得意先一覧";

    private CustomerFactory customerFactory = CustomerFactory.getInstance();
    private Customers customers = new Customers();
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
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> customerCodeColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private Label customerCodeLabel;
    @FXML
    private Label customerNameLabel;
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
    private Label president;
    @FXML
    private Label remarksLabel;
    @FXML
    private CheckBox deletedCheckBox;

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

            // customerTableの初期設定
            customers.load(sql);
            customerTable.setItems(customers.getData());
            customerCodeColumn.setCellValueFactory(cellData -> cellData.getValue().customerCodeProperty());
            customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());

            showDetails(null);
            customerTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showDetails(newValue))
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
                .setEvent(customerTable);

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
        Customer tempCustomer = new Customer();
        boolean isClosableDialog = false;
        SQL sql = null;
        try {
            sql = SQL.create();
            while (!isClosableDialog) {
                boolean okClicked = showEditDialog(tempCustomer, true);
                if (okClicked) {
                    // DBにデータ登録し、新規か否かの状態を取得する。
                    isClosableDialog = tempCustomer.saveNewData(sql);
                    // データテーブルをリロード
                    customers.load(sql);
                } else {
                    isClosableDialog = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showDetails(customerTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        SQL sql = null;
        try {
            sql = SQL.create();
            if (selectedCustomer != null) {
                boolean okClicked = showEditDialog(selectedCustomer, false);
                if (okClicked) {
                    selectedCustomer.saveEditData(sql);
                    customers.load(sql);
                }

            } else {
                // Nothing selected.
                new WarningAlert(
                        Customer.NO_SELECTION_ERROR,
                        Customer.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showDetails(customerTable.getSelectionModel().getSelectedItem());
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

    private void showDetails(Customer customer) {
        if (customer != null) {
            customerCodeLabel.setText(customer.getCustomerCode());
            customerNameLabel.setText(customer.getCustomerName());
            postcodeLabel.setText(customer.getPostcode());
            addressLabel.setText(customer.getAddress());
            buildingEtAlLabel.setText(customer.getBuildingEtAl());
            printingName1Label.setText(customer.getPrintingName1());
            printingName2Label.setText(customer.getPrintingName2());
            telNumberLabel.setText(customer.getTelNumber());
            faxNumberLabel.setText(customer.getFaxNumber());
            invoiceLabel.setText(customer.getInvoice().getInvoiceName());
            bankInformationLabel.setText(customer.getBankInformation());
            bankTransferNameLabel.setText(customer.getBankTransferName());
            president.setText(customer.getPresident());
            remarksLabel.setText(customer.getRemarks());
            deletedCheckBox.setSelected(customer.isDeleted());
        } else {
            customerCodeLabel.setText("");
            customerNameLabel.setText("");
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
            president.setText("");
            remarksLabel.setText("");
            deletedCheckBox.setSelected(false);
        }
    }

    private boolean showEditDialog(Customer customer, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pup-up dialog.
            URL location = WindowManager.class.getResource(CustomerMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(CustomerMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Customer into the controller.
            CustomerMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setCustomer(customer);
            controller.setEvent();

            // 新規の場合、得意先コードを編集不可にする。
            controller.setEditableForPKField(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
