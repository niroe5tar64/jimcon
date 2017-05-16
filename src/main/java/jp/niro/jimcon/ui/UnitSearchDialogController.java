package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.data.Unit;
import jp.niro.jimcon.data.Units;
import jp.niro.jimcon.sql.LoginInfo;

/**
 * Created by niro on 2017/05/01.
 */
public class UnitSearchDialogController {
    private Units units = new Units();
    private UnitSearchable unitSearchable;
    private Stage ownerStage;

    void setUnitSearchable(UnitSearchable unitSearchable) {
        this.unitSearchable = unitSearchable;
    }

    UnitSearchable getUnitSearch() {
        return unitSearchable;
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private TableView<Unit> unitTable;
    @FXML
    private TableColumn<Unit, Integer> unitCodeColumn;
    @FXML
    private TableColumn<Unit, String> unitNameColumn;

    @FXML
    private void initialize() {
        // 単位ロード＆テーブルカラムにセット
        units.loadUnits(LoginInfo.create());
        unitTable.setItems(units.getUnits());

        unitCodeColumn.setCellValueFactory(cellData -> cellData.getValue().unitCodeProperty().asObject());
        unitNameColumn.setCellValueFactory(cellData -> cellData.getValue().unitNameProperty());

        unitTable.setOnKeyReleased(
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

        unitTable.setOnMouseClicked(
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
        System.out.println(unitTable.getSelectionModel().getSelectedItem().getUnitName());
    }

    private void selection() {
        unitSearchable.updateDisplay(unitTable.getSelectionModel().getSelectedItem());
    }

}
