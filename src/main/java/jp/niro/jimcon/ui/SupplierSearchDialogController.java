package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Supplier;
import jp.niro.jimcon.datamodel.Suppliers;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/10.
 */
public class SupplierSearchDialogController implements SingleSearchDialog {
    public static final String FXML_NAME = "SupplierSearchDialog.fxml";
    public static final String TITLE_NAME = "仕入先検索";

    private Suppliers suppliers = new Suppliers();
    private SupplierSearchable supplierSearchable;
    private Stage stage;

    SupplierSearchable getSupplierSearchable() {
        return supplierSearchable;
    }

    void setSupplierSearchable(SupplierSearchable supplierSearchable) {
        this.supplierSearchable = supplierSearchable;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField supplierSearchField;
    @FXML
    private Button supplierSearchButton;
    @FXML
    private TableView<Supplier> supplierTable;
    @FXML
    private TableColumn<Supplier, String> supplierCodeColumn;
    @FXML
    private TableColumn<Supplier, String> supplierNameColumn;

    @FXML
    private void initialize() {
        // テーブルカラムにセット
        supplierCodeColumn.setCellValueFactory(cellData -> cellData.getValue().supplierCodeProperty());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
    }

    public void setEvent() {
        // 目的のアイテムを選択した時のアクション
        ActionBean searchDetermine = new ActionSearchDetermine(this);
        // 検索用テキストフィールドへフォーカスを移動
        ActionBean focusOnSearchField = new ActionBean() {
            @Override
            public void action() {
                supplierSearchField.requestFocus();
            }
        };
        // 検索実行
        ActionBean loadItems = new ActionBean() {
            @Override
            public void action() {
                handleSearch();
            }
        };
        // ダイアログを閉じるアクション
        ActionBean closeDialog = new ActionBean() {
            @Override
            public void action() {
                stage.close();
            }
        };

        // タグ検索用テキストフィールド選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER,loadItems,true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog,true)
                .setEvent(supplierSearchField);
        // 検索ボタン実行時
        ActionEventManager.setOnAction(loadItems).setEvent(supplierSearchButton);

        // 仕入先テーブル選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, searchDetermine, true)
                .setOnKeyReleased(KeyCode.ESCAPE, focusOnSearchField, true)
                .setEvent(supplierTable);

        // 仕入先テーブル選択時のマウス操作
        MouseEventManager.create()
                .setOnMouseClicked(MouseButton.PRIMARY, Constant.System.CLICK_COUNT_DOUBLE,searchDetermine)
                .setEvent(supplierTable);
    }

    @Override
    public void handleSearch() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // supplierTableの初期設定
            suppliers.load(sql, supplierSearchField.getText().trim());
            supplierTable.setItems(suppliers.getData());
            if (Validator.isNotEmpty(suppliers.getData())) {
                supplierTable.requestFocus();
                supplierTable.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    @Override
    public void determine() {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
        if (Validator.isNotNull(supplier)) supplierSearchable.updateDisplay(supplier);
        stage.close();
    }

    public void setSupplierSearchField(String searchValue) {
        supplierSearchField.setText(searchValue);
    }
}
