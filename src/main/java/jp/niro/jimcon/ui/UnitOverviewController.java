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
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.data.Unit;
import jp.niro.jimcon.data.Units;
import jp.niro.jimcon.sql.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitOverviewController {
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
        units.loadUnits(LoginInfo.create());
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
                isClosableDialog = tempUnit.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                units.loadUnits(LoginInfo.create());
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
                selectedUnit.saveEditedData(LoginInfo.create());
                units.loadUnits(LoginInfo.create());
            }

        } else {
            // Nothing selected.
            Commons.showWarningAlert(
                    Constant.ErrorMessages.Title.NO_SELECTION_UNIT_CODE,
                    Constant.ErrorMessages.Unit.NO_SELECTION,
                    "",
                    true
            );
        }
        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteUnit() {
        // Don't delete.
        Commons.showWarningAlert(
                Constant.ErrorMessages.Title.DO_NOT_DELETE,
                Constant.ErrorMessages.Unit.DO_NOT_DELETE,
                "",
                true
        );
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
            // load the fxml file and create a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.UNIT_EDIT_DIALOG);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.UNIT_EDIT);
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
