package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.ErrorAlert;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.Unit;

/**
 * Created by niro on 2017/05/15.
 */
public class TagEditDialogController {
    public static final String FXML_NAME = "TagEditDialog.fxml";
    public static final String TITLE_NAME = "タグ編集";
    public static final String INVALID_FIELDS = "Invalid Fields Error";
    public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

    private Tag tag;
    private Stage ownerStage;
    private boolean okClicked;

    public void setTag(Tag tag) {
        this.tag = tag;

        tagIdField.setText(Long.toString(tag.getTagId()));
        tagNameField.setText(tag.getTagName());

    }

    public Stage getOwnerStage(Stage stage) {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public TextField getTagIdField() {
        return tagIdField;
    }

    @FXML
    private TextField tagIdField;

    @FXML
    private TextField tagNameField;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOK() {
        if (isInputValid()){
            tag.setTagId(Long.parseLong(tagIdField.getText()));
            tag.setTagName(tagNameField.getText());

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
