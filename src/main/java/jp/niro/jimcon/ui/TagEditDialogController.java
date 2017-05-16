package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Commons;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.data.Tag;

/**
 * Created by niro on 2017/05/15.
 */
public class TagEditDialogController {
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
            errorMessage.append(Constant.ErrorMessages.Tag.TAG_ID_IS_EMPTY);
        }

        try {
            long tagId = Long.parseLong(tagIdField.getText());

        } catch (NumberFormatException e) {
            errorMessage.append(Constant.ErrorMessages.Unit.UNIT_CODE_IS_NOT_INTEGER);
        }

        if (Validator.isEmpty(tagNameField.getText())) {
            errorMessage.append(Constant.ErrorMessages.Tag.TAG_ID_IS_EMPTY);
        }

        if (Validator.isEmpty(errorMessage.toString())) {
            return true;
        } else {
            Commons.showErrorAlert(
                    Constant.ErrorMessages.Title.INVALID_FIELDS,
                    Constant.ErrorMessages.User.PLEASE_INPUT_CORRECT_VALUE,
                    errorMessage.toString(),
                    true
            );
        }
        return false;
    }
}
