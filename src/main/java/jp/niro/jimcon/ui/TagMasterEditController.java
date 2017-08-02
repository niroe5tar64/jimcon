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
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.eventmanager.*;

import java.util.Collection;

/**
 * Created by niro on 2017/05/15.
 */
public class TagMasterEditController implements MasterEditController {
    public static final String FXML_NAME = "TagMasterEdit.fxml";
    public static final String TITLE_NAME = "タグ編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Tag tag;
    private Stage stage;
    private boolean okClicked;

    public void setTag(Tag tag) {
        this.tag = tag;

        tagIdField.setText(Long.toString(tag.getTagId()));
        tagNameField.setText(tag.getTagName());

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

    public TextField getTagIdField() {
        return tagIdField;
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField tagIdField;
    @FXML
    private TextField tagNameField;
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
    public void handleOK() {
        if (isInputValid()){
            tag.setTagId(Long.parseLong(tagIdField.getText()));
            tag.setTagName(tagNameField.getText());

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

        if (Validator.isEmpty(tagIdField.getText())) {
            errorMessage.append(Tag.TAG_ID_IS_EMPTY);
        }

        try {
            long tagId = Long.parseLong(tagIdField.getText());

        } catch (NumberFormatException e) {
            errorMessage.append(Unit.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(tagNameField.getText())) {
            errorMessage.append(Tag.TAG_ID_IS_EMPTY);
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
