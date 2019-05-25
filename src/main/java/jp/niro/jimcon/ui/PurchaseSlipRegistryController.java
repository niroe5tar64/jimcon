package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.customcomponents.flowlistview.ActionFlowListView;
import jp.niro.jimcon.customcomponents.flowlistview.FlowListView;
import jp.niro.jimcon.datamodel.*;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by niro on 2017/08/05.
 */
public class PurchaseSlipRegistryController implements SupplierSearchable {
    public static final String FXML_NAME = "PurchaseSlipRegistry.fxml";
    public static final String TITLE_NAME = "仕入モニタ";

    private PurchaseSlip purchaseSlip = new PurchaseSlip();
    private TagMapPool tagMapPool = TagMapPool.getInstance();
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    Pane pane;

    @FXML
    private TextField purchaseIssueDateField;
    @FXML
    private TextField slipNumberFild;
    @FXML
    private TextField searchSupplierField;
    @FXML
    private Label supplierNameLabel;
    @FXML
    private Button searchSupplierButton;

    @FXML
    private TextField searchTagField;
    @FXML
    private Button searchTagButton;
    @FXML
    private FlowListView<Tag> tagFlowList;
    @FXML
    private Button searchProductButton;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField sizeColorField;
    @FXML
    private TextField modelNumberField;
    @FXML
    private TextField purchaseAmountField;
    @FXML
    private TextField unitNameField;
    @FXML
    private TextField subtotalField;
    @FXML
    private TextField remarksField;
    @FXML
    private Button addPurchaseDetailsButton;

    @FXML
    private TableView<PurchaseDetail> purchaseDetailTable;
    @FXML
    private TableColumn<PurchaseDetail, String> purchaseNameColumn;
    @FXML
    private TableColumn<PurchaseDetail, String> sizeColorColumn;
    @FXML
    private TableColumn<PurchaseDetail, String> modelNumberColumn;
    @FXML
    private TableColumn<PurchaseDetail, Double> purchaseAmountColumn;
    @FXML
    private TableColumn<PurchaseDetail, String> unitNameColumn;
    @FXML
    private TableColumn<PurchaseDetail, Double> subtotalColumn;
    @FXML
    private TableColumn<PurchaseDetail, String> remarksColumn;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // purchaseDetailTableの初期設定
            purchaseSlip.load(sql, 1);
            purchaseDetailTable.setItems(purchaseSlip.getPurchaseDetails());
            purchaseNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
            sizeColorColumn.setCellValueFactory(cellData -> cellData.getValue().sizeColorProperty());
            modelNumberColumn.setCellValueFactory(cellData -> cellData.getValue().modelNumberProperty());
            purchaseAmountColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseAmountProperty().asObject());
            unitNameColumn.setCellValueFactory(cellData -> cellData.getValue().productProperty().get().getUnit().unitNameProperty());
            subtotalColumn.setCellValueFactory(cellData -> cellData.getValue().subtotalProperty().asObject());
            remarksColumn.setCellValueFactory(cellData -> cellData.getValue().remarksProperty());

            showSlipHeader(purchaseSlip.getPurchase());
            showDetails(null);
            purchaseDetailTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showDetails(newValue))
            );

            // tagMapPoolの初期設定
            tagMapPool.loadTagMaps(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    public void setEvent() {
        FXRobot robot = FXRobotFactory.createRobot(stage.getScene());

        // フォーカス移動用アクション
        ActionBean focusNext = new RobotKeyPress(robot, KeyCode.TAB);
        // 検索ダイアログ表示用アクション
        ActionBean showSearchSupplier = new ActionSearch(SearchType.SUPPLIER,this);
        // 選択タグの削除用アクション
        ActionBean removeTag = new ActionFlowListView(tagFlowList);


        // 画面上の全てのTextFieldを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> textFields = pickUpper.start(pane, TextField.class);

        // キーイベント一括設定：フォーカス移動
        textFields.forEach(textField ->
                ActionEventManager.setOnAction(focusNext).setEvent(textField));


        // 検索ダイアログ設定
        ActionEventManager.setOnAction(showSearchSupplier).setEvent(searchSupplierButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, showSearchSupplier, true).setEvent(searchSupplierButton);

        // FlowListView<Tag>選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.DELETE, removeTag, true)
                .setEvent(tagFlowList);
    }

    private void showSlipHeader(Purchase purchase) {
        if (purchase != null) {
            purchaseIssueDateField.setText(purchase.getIssueDate().toString());
            slipNumberFild.setText("");
            searchSupplierField.setText(purchase.getSupplier().getSupplierCode());
            supplierNameLabel.setText(purchase.getSupplier().getSupplierName());
        } else {
            purchaseIssueDateField.setText("");
            slipNumberFild.setText("");
            searchSupplierField.setText("");
            supplierNameLabel.setText("");
        }
    }

    private void showDetails(PurchaseDetail purchaseDetail) {
        if (purchaseDetail != null) {
            productNameField.setText(purchaseDetail.getProductName());
            sizeColorField.setText(purchaseDetail.getSizeColor());
            modelNumberField.setText(purchaseDetail.getModelNumber());
            purchaseAmountField.setText(Double.toString(purchaseDetail.getPurchaseAmount()));
            unitNameField.setText(purchaseDetail.getProduct().getUnit().getUnitName());
            subtotalField.setText(Double.toString(purchaseDetail.getSubtotal()));
            remarksField.setText(purchaseDetail.getRemarks());
        } else {
            productNameField.setText("");
            sizeColorField.setText("");
            modelNumberField.setText("");
            purchaseAmountField.setText("");
            unitNameField.setText("");
            subtotalField.setText("");
            remarksField.setText("");
        }
    }


    private void showSupplierDialog() {
        try {
            URL location = WindowManager.class.getResource(SupplierSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(SupplierSearchDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            SupplierSearchDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            // SupplierSearchDialogControllerとSupplierEditDialogControllerの紐付け
            controller.setSupplierSearchable(this);
            controller.setSupplierSearchField(getSearchValue());
            controller.setEvent();
            controller.handleSearch();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDisplay(Supplier supplier) {
        if (Validator.isNotNull(supplier)) {
            searchSupplierField.setText(supplier.getSupplierCode());
            supplierNameLabel.setText(supplier.getSupplierName());

        } else {
            updateDisplay(new Supplier());
        }
    }

    @Override
    public String getSearchValue() {
        return searchSupplierField.getText().trim();
    }

    @Override
    public void showSearchDialog(SearchType type) {
        switch (type) {
            case SUPPLIER:
                showSupplierDialog();
                break;
        }
    }
}
