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
import jp.niro.jimcon.eventmanager.ActionBean;
import jp.niro.jimcon.eventmanager.ActionSearchDetermine;
import jp.niro.jimcon.eventmanager.KeyEventManager;
import jp.niro.jimcon.eventmanager.MouseEventManager;

import java.sql.SQLException;

/**
 * Created by niro on 2017/05/01.
 */
public class TagSearchDialogController implements SingleSearchDialog {
    public static final String FXML_NAME = "TagSearchDialog.fxml";
    public static final String TITLE_NAME = "タグ検索";

    private Tags tags = new Tags();
    private TagSearchable tagSearchable;
    private Stage stage;

    void setTagSearchable(TagSearchable tagSearchable) {
        this.tagSearchable = tagSearchable;
    }

    TagSearchable getTagSearchable() {
        return tagSearchable;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    }

    public void setEvent() {
        // 目的のアイテムを選択した時のアクション
        ActionBean searchDetermine = new ActionSearchDetermine(this);
        // タグ検索用テキストフィールドへフォーカスを移動
        ActionBean focusOnTagSearchField = new ActionBean() {
            @Override
            public void action() {
                tagSearchField.requestFocus();
            }
        };
        // 検索実行
        ActionBean loadTags = new ActionBean() {
            @Override
            public void action() {
                load();
            }
        };
        // ダイアログを閉じるアクション
        ActionBean closeDialog = new ActionBean() {
            @Override
            public void action() {
                stage.close();
            }
        };

        // タグ検索用テキストフィールド選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER,loadTags,true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog,true)
                .setEvent(tagSearchField);
        /*tagSearchField.setOnKeyReleased(
                event -> {
                    // Enterキーを押した時
                    if (event.getCode() == KeyCode.ENTER) {
                        load();
                    }
                    // Escapeキーを押した時
                    else if (event.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    }
                }
        );*/
        // タグテーブル選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, searchDetermine, true)
                .setOnKeyReleased(KeyCode.ESCAPE, focusOnTagSearchField, true)
                .setEvent(tagTable);
        /*tagTable.setOnKeyReleased(
                event -> {
                    // Enterキーを押した時
                    if (event.getCode() == KeyCode.ENTER) {
                        determine();
                        stage.close();
                    }
                    // Escapeキーを押した時
                    else if (event.getCode() == KeyCode.ESCAPE) {
                        tagSearchField.requestFocus();
                    }
                }
        );*/
        // タグテーブル選択時のマウス操作
        MouseEventManager.create()
                .setOnMouseClicked(MouseButton.PRIMARY,Constant.System.CLICK_COUNT_DOUBLE,searchDetermine)
                .setEvent(tagTable);
        /*tagTable.setOnMouseClicked(
                event -> {
                    final boolean primaryButtonClicked = event.getButton().equals(MouseButton.PRIMARY);
                    final int clickCount = event.getClickCount();
                    if (primaryButtonClicked) {
                        // ダブルクリックした時
                        if (clickCount == Constant.System.CLICK_COUNT_DOUBLE) {
                            determine();
                            stage.close();
                        }
                    }
                }
        );*/
    }

    @FXML
    private void handleSearchTag() {
        load();
    }

    @Override
    public void determine() {
        Tag tag = tagTable.getSelectionModel().getSelectedItem();
        if (Validator.isNotNull(tag)) tagSearchable.updateDisplay(tag);
        stage.close();
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
