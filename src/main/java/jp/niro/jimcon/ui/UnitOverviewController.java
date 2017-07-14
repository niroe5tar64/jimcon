package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.datamodel.Units;
import jp.niro.jimcon.dbaccess.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitOverviewController {
    public static final String FXML_NAME = "UnitOverview.fxml";
    public static final String TITLE_NAME = "単位一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：単位コード";
    public static final String DO_NOT_DELETE = "Don't delete";

    private Units units = new Units();
    private Stage ownerStage;

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

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
    private void initialize() {
        units.loadUnits(LoginInfo.getInstance());
        unitTable.setItems(units.getUnits());

        unitCodeColumn.setCellValueFactory(cellData -> cellData.getValue().unitCodeProperty().asObject());
        unitNameColumn.setCellValueFactory(cellData -> cellData.getValue().unitNameProperty());

        showUnitDetails(null);

        unitTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showUnitDetails(newValue))
        );
    }

    @FXML
    private void handleNewUnit() {
        Unit tempUnit = new Unit();
        boolean isClosableDialog = false;
        while (!isClosableDialog) {
            boolean okClicked = showUnitEditDialog(tempUnit, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isClosableDialog = tempUnit.saveNewData(LoginInfo.getInstance());
                // データテーブルをリロード
                units.loadUnits(LoginInfo.getInstance());
            } else {
                isClosableDialog = true;
            }
        }
        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleEditUnit() {
        Unit selectedUnit = unitTable.getSelectionModel().getSelectedItem();
        if (selectedUnit != null) {
            boolean okClicked = showUnitEditDialog(selectedUnit, false);
            if (okClicked) {
                selectedUnit.saveEditedData(LoginInfo.getInstance());
                units.loadUnits(LoginInfo.getInstance());
            }

        } else {
            // Nothing selected.
            new WarningAlert(
                    NO_SELECTION_ERROR,
                    Unit.NO_SELECTION,
                    ""
            ).showAndWait();
        }
        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteUnit() {
        // Don't delete.
        new WarningAlert(
                DO_NOT_DELETE,
                Unit.DO_NOT_DELETE,
                ""
        ).showAndWait();
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
            URL location = WindowManager.class.getResource(UnitEditDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(UnitEditDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Unit into the controller.
            UnitEditDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setUnit(unit);

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
