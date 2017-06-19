package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.data.Tag;
import jp.niro.jimcon.data.Tags;
import jp.niro.jimcon.sql.LoginInfo;

/**
 * Created by niro on 2017/05/01.
 */
public class TagSearchDialogController {
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

        tagTable.setOnKeyReleased(
                event -> {
                    // Enterキーを押した時
                    if (event.getCode() == KeyCode.ENTER) {
                        selection();
                        ownerStage.close();
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
    private void handleOnMouseClicked() {
        System.out.println(tagTable.getSelectionModel().getSelectedItem().getTagName());
    }

    private void selection() {
        tagSearchable.updateDisplay(tagTable.getSelectionModel().getSelectedItem());
    }

    public void load() {
        // タグロード
        tags.loadTags(LoginInfo.create(), tagSearchable.getSearchValue());
        tagTable.setItems(tags.getTags());
    }

}
