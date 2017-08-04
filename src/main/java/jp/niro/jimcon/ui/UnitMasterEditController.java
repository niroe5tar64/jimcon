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
import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.eventmanager.*;

import java.util.Collection;

/**
 * Created by niro on 2017/04/17.
 */
public class UnitMasterEditController implements MasterEditController{
    public static final String FXML_NAME = "UnitMasterEdit.fxml";
    public static final String TITLE_NAME = "単位編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Unit unit;
    private Stage stage;
    private boolean okClicked;

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitCodeField.setText(Integer.toString(unit.getUnitCode()));
        unitNameField.setText(unit.getUnitName());
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

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField unitCodeField;
    @FXML
    private TextField unitNameField;
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
        // [OK][Cancel]操作用アクション
        ActionBean executeOK = new ActionMasterEdit(ActionType.OK, this);
        ActionBean executeCancel = new ActionMasterEdit(ActionType.CANCEL, this);

        // 画面上の全てのTextFieldを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> textFields = pickUpper.start(pane, TextField.class);

        // キーイベント一括設定：フォーカス移動
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog)
                .setEvent(textFields);

        textFields.forEach(textField ->
                ActionEventManager.setOnAction(focusNext).setEvent(textField));

        // [OK][Cancel]ボタンの設定
        ActionEventManager.setOnAction(executeOK).setEvent(okButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeOK).setEvent(okButton);
        ActionEventManager.setOnAction(executeCancel).setEvent(cancelButton);
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeCancel).setEvent(cancelButton);
    }

    @Override
    public void setEditableForPKField(boolean editable) {
        unitCodeField.editableProperty().set(editable);
    }

    @Override
    public void handleOK() {
        if (isInputValid()) {
            unit.setUnitCode(Integer.parseInt(unitCodeField.getText()));
            unit.setUnitName(unitNameField.getText());

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
