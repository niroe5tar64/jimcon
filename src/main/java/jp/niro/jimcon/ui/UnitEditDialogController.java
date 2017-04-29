package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
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

        if (Validator.isEmpty(unitCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.User.UNIT_CODE_IS_EMPTY);
        }

        try {
            int unitCode = Integer.parseInt(unitCodeField.getText());
            if (Validator.isNotInRange(unitCode,0,255)){
                errorMessage.append(Constant.ErrorMessages.User.UNIT_CODE_IS_NOT_IN_RANGE);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.User.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(unitNameField.getText())) {
            errorMessage.append(Constant.ErrorMessages.User.UNIT_NAME_IS_EMPTY);
        }

        if (errorMessage.length() == 0){
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle(Constant.ErrorMessages.Title.INVALID_FIELDS);
            alert.setHeaderText(Constant.ErrorMessages.User.PLEASE_INPUT_CORRECT_VALUE);
            alert.setContentText(errorMessage.toString());

            alert.showAndWait();

            return false;
        }
    }

}
