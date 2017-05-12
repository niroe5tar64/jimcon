package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/12.
 */
public class MenuController {
    private BorderPane rootLayout;
    private Stage primaryStage;

    public BorderPane getRootLayout(){
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button departmentMasterButton;
    @FXML
    private Button unitMasterButton;
    @FXML
    private Button productMasterButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleDepartmentMaster() {
        showDepartmentMaster();
    }

    @FXML
    private void handleUnitMaster() {
        showUnitMaster();
    }

    @FXML
    private void handleProductMaster() {
        showProductMaster();
    }

    private void showDepartmentMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.DEPARTMENT_OVERVIEW);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.DEPARTMENT_OVERVIEW);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            DepartmentOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUnitMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.UNIT_OVERVIEW);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.UNIT_OVERVIEW);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            UnitOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProductMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.PRODUCT_OVERVIEW);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Constant.Dialogs.Title.PRODUCT_OVERVIEW);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            ProductOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
