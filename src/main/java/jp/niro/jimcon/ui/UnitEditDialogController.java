package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Unit;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitEditDialogController {
    public static final String FXML_NAME = "UnitEditDialog.fxml";
    public static final String TITLE_NAME = "単位編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Unit unit;
    private Stage ownerStage;
    private boolean okClicked;

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitCodeField.setText(Integer.toString(unit.getUnitCode()));
        unitNameField.setText(unit.getUnitName());
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public TextField getUnitCodeField() {
        return unitCodeField;
    }

    @FXML
    private TextField unitCodeField;

    @FXML
    private TextField unitNameField;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            unit.setUnitCode(Integer.parseInt(unitCodeField.getText()));
            unit.setUnitName(unitNameField.getText());

            okClicked = true;
            ownerStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        ownerStage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (Validator.isEmpty(unitCodeField.getText())) {
            errorMessage.append(Unit.UNIT_CODE_IS_EMPTY);
        }

        try {
            int unitCode = Integer.parseInt(unitCodeField.getText());
            if (Validator.isNotInRange(unitCode, 0, 255)) {
                errorMessage.append(Unit.UNIT_CODE_IS_NOT_IN_RANGE);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Unit.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(unitNameField.getText())) {
            errorMessage.append(Unit.UNIT_NAME_IS_EMPTY);
        }

        if (Validator.isGreaterThan(unitNameField.getLength(), Unit.UNIT_NAME_LENGTH)) {
            errorMessage.append(Unit.UNIT_NAME_IS_TOO_LONG);
        }

        if (Validator.isEmpty(errorMessage.toString())) {
            return true;
        } else {
            new ErrorAlert(
                    INVALID_FIELDS,
                    PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString()
            ).showAndWait();
        }
        return false;
    }

}
