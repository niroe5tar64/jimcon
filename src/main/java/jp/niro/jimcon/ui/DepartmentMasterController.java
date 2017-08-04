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
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Department;
import jp.niro.jimcon.datamodel.DepartmentFactory;
import jp.niro.jimcon.datamodel.Departments;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/05/09.
 */
public class DepartmentMasterController implements MasterController {
    public static final String FXML_NAME = "DepartmentMaster.fxml";
    public static final String TITLE_NAME = "部署一覧";

    private DepartmentFactory departmentFactory = DepartmentFactory.getInstance();
    private Departments departments = new Departments();
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
    private TableView<Department> departmentTable;
    @FXML
    private TableColumn<Department, Integer> departmentCodeColumn;
    @FXML
    private TableColumn<Department, String> departmentNameColumn;
    @FXML
    private Label departmentCodeLabel;
    @FXML
    private Label departmentNameLabel;
    @FXML
    private Label postcodeLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label telNumberLabel;
    @FXML
    private Label faxNumberLabel;
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

            // departmentTableの初期設定
            departments.loadDepartments(sql);
            departmentTable.setItems(departments.getDepartments());
            departmentCodeColumn.setCellValueFactory(cellData -> cellData.getValue().departmentCodeProperty().asObject());
            departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().departmentNameProperty());

            showDepartmentDetails(null);
            departmentTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showDepartmentDetails(newValue))
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
                .setEvent(departmentTable);

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
        Department tempDepartment = new Department();
        SQL sql = null;
        try {
            sql = SQL.create();
            boolean isClosableDialog = false;
            while (!isClosableDialog) {
                boolean okClicked = showDepartmentEditDialog(tempDepartment, true);
                if (okClicked) {
                    // DBにデータ登録し、新規か否かの状態を取得する。
                    isClosableDialog = tempDepartment.saveNewData(sql);
                    // データテーブルをリロード
                    departments.loadDepartments(sql);
                } else {
                    isClosableDialog = true;
                }
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showDepartmentDetails(departmentTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        SQL sql = null;
        try {
            sql = SQL.create();
            if (selectedDepartment != null) {
                boolean okClicked = showDepartmentEditDialog(selectedDepartment, false);
                if (okClicked) {
                    selectedDepartment.saveEditedData(sql);
                    departments.loadDepartments(sql);
                }

            } else {
                // Nothing selected.
                new WarningAlert(
                        Department.NO_SELECTION_ERROR,
                        Department.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断

        showDepartmentDetails(departmentTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleDelete() {
        // Don't delete.
        new WarningAlert(
                Department.DO_NOT_DELETE_ERROR,
                Department.DO_NOT_DELETE,
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

    private void showDepartmentDetails(Department department) {
        if (department != null) {
            departmentCodeLabel.setText(Integer.toString(department.getDepartmentCode()));
            departmentNameLabel.setText(department.getDepartmentName());
            postcodeLabel.setText(department.getPostcode());
            addressLabel.setText(department.getAddress());
            telNumberLabel.setText(department.getTelNumber());
            faxNumberLabel.setText(department.getFaxNumber());
        } else {
            departmentCodeLabel.setText("");
            departmentNameLabel.setText("");
            postcodeLabel.setText("");
            addressLabel.setText("");
            telNumberLabel.setText("");
            faxNumberLabel.setText("");
        }
    }

    private boolean showDepartmentEditDialog(Department department, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(DepartmentMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(DepartmentMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Department into the controller.
            DepartmentMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setDepartment(department);
            controller.setEvent();

            // 新規の場合、単位コードを編集不可にする。
            controller.getDepartmentCodeField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
