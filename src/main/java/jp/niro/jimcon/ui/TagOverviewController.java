package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.Tags;
import jp.niro.jimcon.dbaccess.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/15.
 */
public class TagOverviewController {
    public static final String FXML_NAME = "TagOverview.fxml";
    public static final String TITLE_NAME = "タグ一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：タグＩＤ";
    public static final String DO_NOT_DELETE_ERROR = "Don't delete";

    private Tags tags = new Tags();
    private Stage ownerStage;

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private TableView<Tag> tagTable;
    @FXML
    private TableColumn<Tag, Long> tagIdColumn;
    @FXML
    private TableColumn<Tag, String> tagNameColumn;
    @FXML
    private Label tagIdLabel;
    @FXML
    private Label tagNameLabel;

    @FXML
    private void initialize() {
        tags.loadTags(LoginInfo.create());
        tagTable.setItems(tags.getTags());

        tagIdColumn.setCellValueFactory(cellData -> cellData.getValue().tagIdProperty().asObject());
        tagNameColumn.setCellValueFactory(cellData -> cellData.getValue().tagNameProperty());

        showTagDetails(null);

        tagTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showTagDetails(newValue))
        );
    }

    @FXML
    private void handleNewTag() {
        Tag tempTag = new Tag();
        boolean isClosableDialog = false;
        while (!isClosableDialog) {
            boolean okClicked = showTagEditDialog(tempTag, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isClosableDialog = tempTag.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                tags.loadTags(LoginInfo.create());
            } else {
                isClosableDialog = true;
            }
        }
    }

    @FXML
    private void handleEditTag() {
        Tag selectedTag = tagTable.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            boolean okClicked = showTagEditDialog(selectedTag, false);
            if (okClicked) {
                selectedTag.saveEditedData(LoginInfo.create());
                tags.loadTags(LoginInfo.create());
            }

        } else {
            // Nothing selected.
            new WarningAlert(
                    NO_SELECTION_ERROR,
                    Tag.NO_SELECTION,
                    ""
            ).showAndWait();
        }
    }

    @FXML
    private void handleDeleteTag() {
        // Don't delete.
        new WarningAlert(
                DO_NOT_DELETE_ERROR,
                Tag.DO_NOT_DELETE,
                ""
        ).showAndWait();
    }

    private void showTagDetails(Tag tag){
        if (Validator.isNotNull(tag)) {
            tagIdLabel.setText(Long.toString(tag.getTagId()));
            tagNameLabel.setText(tag.getTagName());
        } else {
            tagIdLabel.setText("");
            tagNameLabel.setText("");
        }
    }

    private boolean showTagEditDialog(Tag tag, boolean isNew){
        try {
            // load the fxml file and create a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(TagEditDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagEditDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Tag into the controller.
            TagEditDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setTag(tag);

            // 新規の場合、タグIDを編集不可にする。
            controller.getTagIdField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
