package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    // Reference to the ownerStage.
    private Stage ownerStage;
    private Units units = new Units();

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
        units.loadUnitsData(LoginInfo.create());
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
        boolean isSaved = false;
        while (!isSaved) {
            boolean okClicked = showUnitEditDialog(tempUnit, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isSaved = tempUnit.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                units.loadUnitsData(LoginInfo.create());
            } else {
                isSaved = true;
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
                units.loadUnitsData(LoginInfo.create());
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(ownerStage);
            alert.setTitle(Constant.ErrorMessages.Title.NO_SELECTION_UNIT_CODE);
            alert.setHeaderText(Constant.ErrorMessages.User.NO_SELECTION_UNIT_CODE);

            alert.showAndWait();
        }
        showUnitDetails(unitTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteUnit() {
        // Don't delete.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(ownerStage);
        alert.setTitle("Don't delete.");
        alert.setHeaderText("単位を削除する場合は管理者に問い合わせて下さい。");

        alert.showAndWait();
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
            dialogStage.setTitle(Constant.Dialogs.Title.EDIT_UNIT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Unit into the controller.
            UnitEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUnit(unit);

            // 新規の場合、単位コードを編集不可にする。
            controller.getUnitCodeField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
