package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.data.Department;
import jp.niro.jimcon.data.DepartmentFX;
import jp.niro.jimcon.data.Departments;
import jp.niro.jimcon.data.DepartmentsFX;
import jp.niro.jimcon.sql.LoginInfo;

/**
 * Created by niro on 2017/05/09.
 */
public class DepartmentOverviewController {
    private Departments departments = new DepartmentsFX();
    private Stage ownerStage;

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

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
    private void initialize() {
        departments.loadDepartments(LoginInfo.create());
        departmentTable.setItems(departments.getDepartmentObservableList());

        departmentCodeColumn.setCellValueFactory(cellData -> cellData.getValue().departmentCodeProperty().asObject());
        departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().departmentNameProperty());

        showDepartmentDetails(null);

        departmentTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showDepartmentDetails(newValue))
        );
    }

    @FXML
    private void handleNewDepartment() {
        Department tempDepartment = new DepartmentFX();
        boolean isSaved = false;
        while (!isSaved) {
            boolean okClicked = showDepartmentEditDialog(tempDepartment, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isSaved = tempDepartment.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                departments.loadDepartments(LoginInfo.create());
            } else {
                isSaved = true;
            }
        }
        showDepartmentDetails(departmentTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleEditDepartment() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            boolean okClicked = showDepartmentEditDialog(selectedDepartment, false);
            if (okClicked) {
                selectedDepartment.saveEditedData(LoginInfo.create());
                departments.loadDepartments(LoginInfo.create());
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(ownerStage);
            alert.setTitle(Constant.ErrorMessages.Title.NO_SELECTION_UNIT_CODE);
            alert.setHeaderText(Constant.ErrorMessages.User.NO_SELECTION_UNIT_CODE);

            alert.showAndWait();
        }
        showDepartmentDetails(departmentTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteDepartment() {
        // Don't delete.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(ownerStage);
        alert.setTitle("Don't delete.");
        alert.setHeaderText("単位を削除する場合は管理者に問い合わせて下さい。");

        alert.showAndWait();
    }

    private void showDepartmentDetails(Department department) {
        if (department != null) {
            departmentCodeLabel.setText(Integer.toString(department.getDepartmentCode()));
            departmentNameLabel.setText(department.getDepartmentName());
        } else {
            departmentCodeLabel.setText("");
            departmentNameLabel.setText("");
        }
    }

    private boolean showDepartmentEditDialog(Department department, boolean isNew) {
        /*try {
            // load the fxml file and create a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.UNIT_EDIT_DIALOG);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.EDIT_UNIT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Unit into the controller.
            UnitEditDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setUnit(unit);

            // 新規の場合、単位コードを編集不可にする。
            controller.getUnitCodeField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/
        return false;
    }

}
