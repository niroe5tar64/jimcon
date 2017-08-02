package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Department;
import jp.niro.jimcon.eventmanager.*;

import java.util.Collection;

/**
 * Created by niro on 2017/05/11.
 */
public class DepartmentMasterEditController implements MasterEditController {
    public static final String FXML_FILE = "DepartmentMasterEdit.fxml";
    public static final String TITLE_NAME = "部署編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Department department;
    private Stage stage;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public TextField getDepartmentCodeField() {
        return departmentCodeField;
    }

    @FXML
    private AnchorPane pane;

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
    private Button okButton;
    @FXML
    private Button cancelButton;

    public void setEvent() {
        FXRobot robot = FXRobotFactory.createRobot(stage.getScene());

        // フォーカス移動用アクション
        ActionBean focusNext = new RobotKeyPress(robot, KeyCode.TAB);
        // ダイアログ用アクション
        ActionBean closeDialog = new ActionMasterEdit(ActionType.CLOSE, this);

        // 画面上の全てのTextFieldを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> textFields = pickUpper.start(pane, TextField.class);

        // KeyEvent
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog)
                .setEvent(textFields);

        textFields.forEach(textField ->
                ActionEventManager.setOnAction(focusNext).setEvent(textField));


        // [OK][Cancel]操作用アクション
        ActionBean executeOK = new ActionMasterEdit(ActionType.OK, this);
        ActionBean executeCancel = new ActionMasterEdit(ActionType.CANCEL, this);

        // [OK][Cancel]ボタンの
        ActionEventManager.setOnAction(executeOK).setEvent(okButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeOK).setEvent(okButton);
        ActionEventManager.setOnAction(executeCancel).setEvent(cancelButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeCancel).setEvent(cancelButton);
    }

    @Override
    public void handleOK() {
        if (isInputValid()) {
            department.setDepartmentCode(Integer.parseInt(departmentCodeField.getText()));
            department.setDepartmentName(departmentNameField.getText());
            department.setPostcode(postcodeField.getText());
            department.setAddress(addressField.getText());
            department.setTelNumber(telNumberField.getText());
            department.setFaxNumber(faxNumberField.getText());

            okClicked = true;
            stage.close();
        }
    }

    @Override
    public void handleCancel() {
        stage.close();
    }

    @Override
    public void handleClose() {
        stage.close();
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (Validator.isEmpty(departmentCodeField.getText())) {
            errorMessage.append(Department.DEPARTMENT_CODE_IS_EMPTY);
        }

        try {
            int departmentCode = Integer.parseInt(departmentCodeField.getText());
            if (Validator.isNotInRange(departmentCode, 0, 255)) {
                errorMessage.append(Department.DEPARTMENT_CODE_IS_NOT_IN_RANGE);
            }
        } catch (NumberFormatException e) {
            errorMessage.append(Department.DEPARTMENT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(departmentNameField.getText())) {
            errorMessage.append(Department.DEPARTMENT_NAME_IS_EMPTY);
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
