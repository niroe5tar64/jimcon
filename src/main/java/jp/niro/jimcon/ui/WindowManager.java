package jp.niro.jimcon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
            System.out.println(WindowManager.class.getResource(RootLayoutController.FXML_FILE));
            URL location = WindowManager.class.getResource(RootLayoutController.FXML_FILE);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
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

    public void setMenu() {
        try {
            // Load menu
            URL location = WindowManager.class.getResource(MenuController.FXML_FILE);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane menu = loader.load();

            // Set unit overview into the center of root layout.
            rootLayout.setCenter(menu);

            // Give the controller set on the primary stage.
            MenuController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            controller.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        primaryStage.show();
    }

}
