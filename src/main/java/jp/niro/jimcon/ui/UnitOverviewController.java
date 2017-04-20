package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.data.Unit;
import jp.niro.jimcon.data.Units;
import jp.niro.jimcon.sql.LoginInfo;

import java.io.IOException;

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
        units.loadUnitsData(LoginInfo.defoult());
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
            //units.getUnits().add(tempUnit);
            if (okClicked) {
                isSaved = tempUnit.saveNewData(LoginInfo.defoult());
                units.loadUnitsData(LoginInfo.defoult());
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
                //showUnitDetails(selectedUnit);
                selectedUnit.saveEditedData(LoginInfo.defoult());
                units.loadUnitsData(LoginInfo.defoult());
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(ownerStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("単位を選択して下さい。");

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
            // load the fxml file and create a new ownerStage for the pop-up dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowManager.class.getResource("UnitEditDialog.fxml"));
            Pane pane = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Unit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Unit into the controller
            UnitEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUnit(unit);

            controller.getUnitCodeField().editableProperty().set(isNew);

            // Show the dialog and wait the user closes it.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
