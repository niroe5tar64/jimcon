package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.datamodel.Units;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/01.
 */
public class UnitSearchDialogController implements SingleSearchDialog {
    public static final String FXML_NAME = "UnitSearchDialog.fxml";
    public static final String TITLE_NAME = "単位検索";

    private Units units = new Units();
    private UnitSearchable unitSearchable;
    private Stage stage;

    void setUnitSearchable(UnitSearchable unitSearchable) {
        this.unitSearchable = unitSearchable;
    }

    UnitSearchable getUnitSearchable() {
        return unitSearchable;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TableView<Unit> unitTable;
    @FXML
    private TableColumn<Unit, Integer> unitCodeColumn;
    @FXML
    private TableColumn<Unit, String> unitNameColumn;

    @FXML
    private void initialize() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // unitTableの初期設定
            units.loadUnits(sql);
            unitTable.setItems(units.getUnits());
            unitCodeColumn.setCellValueFactory(cellData -> cellData.getValue().unitCodeProperty().asObject());
            unitNameColumn.setCellValueFactory(cellData -> cellData.getValue().unitNameProperty());

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

        // 単位テーブル選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, searchDetermine, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(unitTable);

        // 単位テーブル選択時のマウス操作
        MouseEventManager.create()
                .setOnMouseClicked(MouseButton.PRIMARY, Constant.System.CLICK_COUNT_DOUBLE, searchDetermine)
                .setEvent(unitTable);
    }

    @Override
    public void determine() {
        unitSearchable.updateDisplay(unitTable.getSelectionModel().getSelectedItem());
        stage.close();
    }
}
