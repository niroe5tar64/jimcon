package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.Tags;
import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/01.
 */
public class TagSearchDialogController {
    public static final String FXML_NAME = "TagSearchDialog.fxml";
    public static final String TITLE_NAME = "タグ検索";

    private Tags tags = new Tags();
    private TagSearchable tagSearchable;
    private Stage ownerStage;

    void setTagSearchable(TagSearchable tagSearchable) {
        this.tagSearchable = tagSearchable;
    }

    TagSearchable getTagSearchable() {
        return tagSearchable;
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private TextField tagSearchField;
    @FXML
    private TableView<Tag> tagTable;
    @FXML
    private TableColumn<Tag, Long> tagIdColumn;
    @FXML
    private TableColumn<Tag, String> tagNameColumn;

    @FXML
    private void initialize() {
        //テーブルカラムにセット
        tagIdColumn.setCellValueFactory(cellData -> cellData.getValue().tagIdProperty().asObject());
        tagNameColumn.setCellValueFactory(cellData -> cellData.getValue().tagNameProperty());
        // タグテーブル選択時のキー操作
        tagTable.setOnKeyReleased(
                event -> {
                    // Enterキーを押した時
                    if (event.getCode() == KeyCode.ENTER) {
                        selection();
                        ownerStage.close();
                    }
                    // Escapeキーを押した時
                    else if (event.getCode() == KeyCode.ESCAPE) {
                        tagSearchField.requestFocus();
                    }
                }
        );
        // タグ検索用テキストボックス選択時のキー操作
        tagSearchField.setOnKeyReleased(
                event -> {
                    // Enterキーを押した時
                    if (event.getCode() == KeyCode.ENTER) {
                        load();
                    }
                    // Escapeキーを押した時
                    else if (event.getCode() == KeyCode.ESCAPE) {
                        ownerStage.close();
                    }
                }
        );

        tagTable.setOnMouseClicked(
                event -> {
                    final boolean primaryButtonClicked = event.getButton().equals(MouseButton.PRIMARY);
                    final int clickCount = event.getClickCount();
                    if (primaryButtonClicked) {
                        // ダブルクリックした時
                        if (clickCount == Constant.System.CLICK_COUNT_DOUBLE) {
                            selection();
                            ownerStage.close();
                        }
                    }
                }
        );
    }

    @FXML
    private void handleSearchTag() {
        load();
    }

    private void selection() {
        Tag tag = tagTable.getSelectionModel().getSelectedItem();
        if (Validator.isNotNull(tag)) tagSearchable.updateDisplay(tag);
    }

    public void load() {
        SQL sql = null;
        try {
            sql = SQL.create();
            // タグロード
            tags.loadTags(sql, tagSearchField.getText().trim());
            tagTable.setItems(tags.getTags());
            if (Validator.isNotEmpty(tags.getTags())) {
                tagTable.requestFocus();
                tagTable.getSelectionModel().selectFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    public void setTagSearchField(String searchValue) {
        tagSearchField.setText(searchValue);
    }
}
