package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.datamodel.Invoice;
import jp.niro.jimcon.datamodel.Invoices;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.ActionBean;
import jp.niro.jimcon.eventmanager.ActionSearchDetermine;
import jp.niro.jimcon.eventmanager.KeyEventManager;
import jp.niro.jimcon.eventmanager.MouseEventManager;

import java.sql.SQLException;

/**
 * Created by niro on 2017/08/03.
 */
public class InvoiceSearchDialogController implements SingleSearchDialog {
    public static final String FXML_NAME = "InvoiceSearchDialog.fxml";
    public static final String TITLE_NAME = "請求書分類検索";

    private Invoices invoices = new Invoices();
    private InvoiceSearchable invoiceSearchable;
    private Stage stage;

    void setInvoiceSearchable(InvoiceSearchable invoiceSearchable) {
        this.invoiceSearchable = invoiceSearchable;
    }

    InvoiceSearchable getInvoiceSearchable() {
        return invoiceSearchable;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TableView<Invoice> invoiceTable;
    @FXML
    private TableColumn<Invoice, Integer> invoiceCodeColumn;
    @FXML
    private TableColumn<Invoice, String> invoiceNameColumn;

    @FXML
    private void initialize() {
        SQL sql = null;

        try {
            sql = SQL.create();

            // invoiceTableの初期設定
            invoices.loadInvoices(sql);
            invoiceTable.setItems(invoices.getInvoices());
            invoiceCodeColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceCodeProperty().asObject());
            invoiceNameColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceNameProperty());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    public void setEvent() {
        // 目的のアイテムを選択した時のアクション
        ActionBean searchDetermine = new ActionSearchDetermine(this);
        // ダイアログを閉じるアクション
        ActionBean closeDialog = new ActionBean() {
            @Override
            public void action() {
                stage.close();
            }
        };

        // 請求書分類テーブル選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, searchDetermine, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(invoiceTable);

        // 請求書分類テーブル選択時のマウス操作
        MouseEventManager.create()
                .setOnMouseClicked(MouseButton.PRIMARY, Constant.System.CLICK_COUNT_DOUBLE, searchDetermine)
                .setEvent(invoiceTable);
    }

    @Override
    public void handleSearch() {
        System.out.println("未実装");
    }

    @Override
    public void determine() {
        invoiceSearchable.updateDisplay(invoiceTable.getSelectionModel().getSelectedItem());
        stage.close();
    }
}
