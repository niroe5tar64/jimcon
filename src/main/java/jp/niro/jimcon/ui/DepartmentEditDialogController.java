package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.data.Department;

/**
 * Created by niro on 2017/05/11.
 */
public class DepartmentEditDialogController {

    private Department department;
    private Stage ownerStage;
    private boolean okClicked;

    public void setDepartment(Department department) {
        this.department = department;

        departmentCodeField.setText(Integer.toString(department.getDepartmentCode()));
        departmentNameField.setText(department.getDepartmentName());
        postcodeField.setText(department.getPostcode());
        addressField.setText(department.getAddress());
        telNumberField.setText(department.getTelNumber());
        faxNumberField.setText(department.getFaxNumber());
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

    public TextField getDepartmentCodeField() {
        return departmentCodeField;
    }

    @FXML
    private TextField departmentCodeField;
    @FXML
    private TextField departmentNameField;
    @FXML
    private TextField postcodeField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField telNumberField;
    @FXML
    private TextField faxNumberField;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            department.setDepartmentCode(Integer.parseInt(departmentCodeField.getText()));
            department.setDepartmentName(departmentNameField.getText());
            department.setPostcode(postcodeField.getText());
            department.setAddress(addressField.getText());
            department.setTelNumber(telNumberField.getText());
            department.setFaxNumber(faxNumberField.getText());

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

        if (Validator.isEmpty(departmentCodeField.getText())) {
            errorMessage.append(Constant.ErrorMessages.Department.DEPARTMENT_CODE_IS_EMPTY);
        }

        try {
            int departmentCode = Integer.parseInt(departmentCodeField.getText());
            if (Validator.isNotInRange(departmentCode, 0, 255)) {
                errorMessage.append(Constant.ErrorMessages.Department.DEPARTMENT_CODE_IS_NOT_IN_RANGE);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.Department.DEPARTMENT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(departmentNameField.getText())) {
            errorMessage.append(Constant.ErrorMessages.Department.DEPARTMENT_NAME_IS_EMPTY);
        }

        if (Validator.isEmpty(errorMessage.toString())) {
            return true;
        } else {
            Commons.showErrorAlert(
                    Constant.ErrorMessages.Title.INVALID_FIELDS,
                    Constant.ErrorMessages.User.PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString(),
                    true);
        }
        return false;
    }

}
