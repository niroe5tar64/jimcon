package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.datamodel.Units;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitMasterController implements MasterController {
    public static final String FXML_NAME = "UnitMaster.fxml";
    public static final String TITLE_NAME = "単位一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：単位コード";
    public static final String DO_NOT_DELETE = "Don't delete";

    private Units units = new Units();
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
    private TableView<Unit> unitTable;
    @FXML
    private TableColumn<Unit, Integer> unitCodeColumn;
    @FXML
    private TableColumn<Unit, String> unitNameColumn;
    @FXML
    private Label unitCodeLabel;
    @FXML
    private Label unitNameLabel;
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

            units.loadUnits(sql);
            unitTable.setItems(units.getUnits());
            unitCodeColumn.setCellValueFactory(cellData -> cellData.getValue().unitCodeProperty().asObject());
            unitNameColumn.setCellValueFactory(cellData -> cellData.getValue().unitNameProperty());

            showUnitDetails(null);
            unitTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showUnitDetails(newValue))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    public void setEvent() {
        // 各ダイアログ表示用アクション
        ActionBeen showNew = new ActionMasterDialog(ActionType.NEW, this);
        ActionBeen showEdit = new ActionMasterDialog(ActionType.EDIT,this);
        ActionBeen showDelete = new ActionMasterDialog(ActionType.DELETE, this);
        ActionBeen closeDialog = new ActionMasterDialog(ActionType.CLOSE, this);

        // テーブルにフォーカスがある時のキーイベント
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, true, false, true, showNew, true)
                .setOnKeyReleased(KeyCode.ENTER, showEdit, true)
                .setOnKeyReleased(KeyCode.DELETE, showDelete, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(unitTable);

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
        Unit tempUnit = new Unit();
        boolean isClosableDialog = false;
        SQL sql = null;
        try {
            sql = SQL.create();
            while (!isClosableDialog) {
                boolean okClicked = showUnitEditDialog(tempUnit, true);
                if (okClicked) {
                    // DBにデータ登録し、新規か否かの状態を取得する。
                    isClosableDialog = tempUnit.saveNewData(sql);
                    // データテーブルをリロード
                    units.loadUnits(sql);
                } else {
                    isClosableDialog = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        Unit selectedUnit = unitTable.getSelectionModel().getSelectedItem();
        SQL sql = null;
        try {
            sql = SQL.create();
            if (selectedUnit != null) {
                boolean okClicked = showUnitEditDialog(selectedUnit, false);
                if (okClicked) {
                    selectedUnit.saveEditedData(sql);
                    units.loadUnits(sql);
                }

            } else {
                // Nothing selected.
                new WarningAlert(
                        NO_SELECTION_ERROR,
                        Unit.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleDelete() {
        // Don't delete.
        new WarningAlert(
                DO_NOT_DELETE,
                Unit.DO_NOT_DELETE,
                ""
        ).showAndWait();
    }

    @Override
    public void handleClose() {
        stage.close();
    }

    private void showUnitDetails(Unit unit) {
        if (unit != null) {
            unitCodeLabel.setText(Integer.toString(unit.getUnitCode()));
            unitNameLabel.setText(unit.getUnitName());
        } else {
            unitCodeLabel.setText("");
            unitNameLabel.setText("");
        }
    }

    private boolean showUnitEditDialog(Unit unit, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(UnitMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(UnitMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Unit into the controller.
            UnitMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setUnit(unit);
            controller.setEvent();

            // 新規の場合、単位コードを編集不可にする。
            controller.getUnitCodeField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
