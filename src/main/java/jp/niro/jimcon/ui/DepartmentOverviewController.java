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
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Department;
import jp.niro.jimcon.datamodel.DepartmentFactory;
import jp.niro.jimcon.datamodel.Departments;
import jp.niro.jimcon.dbaccess.LoginInfo;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/09.
 */
public class DepartmentOverviewController {
    public static final String FXML_NAME = "DepartmentOverview.fxml";
    public static final String TITLE_NAME = "部署一覧";

    private DepartmentFactory departmentFactory = DepartmentFactory.getInstance();
    private Departments departments = new Departments();
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
    private Label postcodeLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label telNumberLabel;
    @FXML
    private Label faxNumberLabel;

    @FXML
    private void initialize() {
        departments.loadDepartments(LoginInfo.create());
        departmentTable.setItems(departments.getObservableList());

        departmentCodeColumn.setCellValueFactory(cellData -> cellData.getValue().departmentCodeProperty().asObject());
        departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().departmentNameProperty());

        showDepartmentDetails(null);

        departmentTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showDepartmentDetails(newValue))
        );
    }

    @FXML
    private void handleNewDepartment() {
        Department tempDepartment = new Department();
        boolean isClosableDialog = false;
        while (!isClosableDialog) {
            boolean okClicked = showDepartmentEditDialog(tempDepartment, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isClosableDialog = tempDepartment.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                departments.loadDepartments(LoginInfo.create());
            } else {
                isClosableDialog = true;
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
            new WarningAlert(
                    Department.NO_SELECTION_ERROR,
                    Department.NO_SELECTION,
                    ""
            ).showAndWait();
        }
        showDepartmentDetails(departmentTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteDepartment() {
        // Don't delete.
        new WarningAlert(
                Department.DO_NOT_DELETE_ERROR,
                Department.DO_NOT_DELETE,
                ""
        ).showAndWait();
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
            // load the fxml file and create a new stage for the pop-up dialog.
            URL location = WindowManager.class.getResource(DepartmentEditDialogController.FXML_FILE);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(DepartmentEditDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Department into the controller.
            DepartmentEditDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setDepartment(department);

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
