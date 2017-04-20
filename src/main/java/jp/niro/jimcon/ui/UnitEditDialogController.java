package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.data.Unit;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitEditDialogController {

    @FXML
    private TextField unitCodeField;
    @FXML
    private TextField unitNameField;

    private Stage dialogStage;
    private Unit unit;
    private boolean okClicked;

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitCodeField.setText(Integer.toString(unit.getUnitCode()));
        unitNameField.setText(unit.getUnitName());
    }

    public TextField getUnitCodeField(){
        return unitCodeField;
    }

    public boolean isOkClicked() {return okClicked;}

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            unit.setUnitCode(Integer.parseInt(unitCodeField.getText()));
            unit.setUnitName(unitNameField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (unitCodeField.getText() == null || unitCodeField.getText().length() == 0) {
            errorMessage.append("No valid unit code!\n");
        }

        try {
            int unitCode = Integer.parseInt(unitCodeField.getText());
            if (unitCode < 0){
                errorMessage.append("No valid unit code (must be 0～255)!\n");
            }
            if (unitCode > 255) {
                errorMessage.append("No valid unit code (must be 0～255)!\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("No valid unit code (must be an integer)!\n");
        }

        if (unitNameField.getText() == null || unitNameField.getText().length() == 0) {
            errorMessage.append("No valid unit name!\n");
        }

        if (errorMessage.length() == 0){
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage.toString());

            alert.showAndWait();

            return false;
        }
    }

}
