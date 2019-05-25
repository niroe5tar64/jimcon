package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.Tags;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/05/15.
 */
public class TagMasterController implements MasterController {
    public static final String FXML_NAME = "TagMaster.fxml";
    public static final String TITLE_NAME = "タグ一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：タグＩＤ";
    public static final String DO_NOT_DELETE_ERROR = "Don't delete";

    private Tags tags = new Tags();
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane pane;

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
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // tagTableの初期設定
            tags.load(sql);
            tagTable.setItems(tags.getData());
            tagIdColumn.setCellValueFactory(cellData -> cellData.getValue().tagIdProperty().asObject());
            tagNameColumn.setCellValueFactory(cellData -> cellData.getValue().tagNameProperty());

            showDetails(null);
            tagTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showDetails(newValue))
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

    }

    public void setEvent() {
        // 各ダイアログ表示用アクション
        ActionBean showNew = new ActionMaster(ActionType.NEW, this);
        ActionBean showEdit = new ActionMaster(ActionType.EDIT,this);
        ActionBean showDelete = new ActionMaster(ActionType.DELETE, this);
        ActionBean closeDialog = new ActionMaster(ActionType.CLOSE, this);

        // テーブルにフォーカスがある時のキーイベント
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, true, false, true, showNew, true)
                .setOnKeyReleased(KeyCode.ENTER, showEdit, true)
                .setOnKeyReleased(KeyCode.DELETE, showDelete, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(tagTable);

        // ボタンが押された時
        ActionEventManager.setOnAction(showNew).setEvent(newButton);
        ActionEventManager.setOnAction(showEdit).setEvent(editButton);
        ActionEventManager.setOnAction(showDelete).setEvent(deleteButton);

        // その他にフォーカスがある時
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(pane);
    }

    @Override
    public void handleNew() {
        Tag tempTag = new Tag();
        boolean isClosableDialog = false;
        SQL sql = null;
        try {
            sql = SQL.create();
            while (!isClosableDialog) {
                // 編集ダイアログ表示
                boolean okClicked = showEditDialog(tempTag, true);
                if (okClicked) {
                    // DBにデータ登録し、新規か否かの状態を取得する。
                    isClosableDialog = tempTag.saveNewData(sql);
                    // データテーブルをリロード
                    tags.load(sql);
                } else {
                    isClosableDialog = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showDetails(tagTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        Tag selectedTag = tagTable.getSelectionModel().getSelectedItem();
        SQL sql = null;
        try {
            sql = SQL.create();
            if (selectedTag != null) {
                boolean okClicked = showEditDialog(selectedTag, false);
                if (okClicked) {
                    selectedTag.saveEditedData(sql);
                    tags.load(sql);
                }

            } else {
                // Nothing selected.
                new WarningAlert(
                        NO_SELECTION_ERROR,
                        Tag.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    @Override
    public void handleDelete() {
        // Don't delete.
        new WarningAlert(
                DO_NOT_DELETE_ERROR,
                Tag.DO_NOT_DELETE,
                ""
        ).showAndWait();
    }

    @Override
    public void handleClose() {
        stage.close();
    }

    @Override
    public void handleSearch() {
        System.out.println("未実装");
    }

    private void showDetails(Tag tag) {
        if (Validator.isNotNull(tag)) {
            tagIdLabel.setText(Long.toString(tag.getTagId()));
            tagNameLabel.setText(tag.getTagName());
        } else {
            tagIdLabel.setText("");
            tagNameLabel.setText("");
        }
    }

    private boolean showEditDialog(Tag tag, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(TagMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Tag into the controller.
            TagMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setTag(tag);
            controller.setEvent();

            // 新規の場合、タグIDを編集不可にする。
            controller.setEditableForPKField(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
