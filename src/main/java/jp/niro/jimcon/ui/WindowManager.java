package jp.niro.jimcon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;

import java.io.IOException;
import java.net.URL;


/**
 * Created by niro on 2017/04/17.
 */
public class WindowManager {
    private Stage primaryStage;
    private BorderPane rootLayout;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.ROOT_LAYOUT);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller set on the primary stage.
            RootLayoutController controller = loader.getController();
            controller.setStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUnitOverview() {
        try {
            // Load unit overview
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.UNIT_OVERVIEW);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane unitOverview = loader.load();

            // Set unit overview into the center of root layout.
            rootLayout.setCenter(unitOverview);

            // Give the controller set on the primary stage.
            UnitOverviewController controller = loader.getController();
            controller.setOwnerStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProductOverview() {
        try {
            // load product overview
            URL location = WindowManager.class.getResource(Constant.Resources.FXMLFile.PRODUCT_OVERVIEW);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME));
            AnchorPane productOverview = loader.load();

            // Set product overview into the center of root layout.
            rootLayout.setCenter(productOverview);

            // Give the controller set on the primary stage.
            ProductOverviewController controller = loader.getController();
            controller.setOwnerStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        primaryStage.show();
    }

    public void setResizable(boolean resizable){
        primaryStage.setResizable(resizable);
    }


}
