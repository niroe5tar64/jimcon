package jp.niro.jimcon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


/**
 * Created by niro on 2017/04/17.
 */
public class WindowManager {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        try {
            // Load login
            URL location = WindowManager.class.getResource(LoginController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane login = loader.load();

            Scene scene = new Scene(login);
            primaryStage.setScene(scene);

            // Give the controller set on the primary stage.
            LoginController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        primaryStage.show();
    }

}
