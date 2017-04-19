package jp.niro.jimcon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowManager.class.getResource("RootLayout.fxml"));
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

    public void showUnitOverview() {
        try {
            // Load person overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowManager.class.getResource("UnitOverview.fxml"));
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




}
