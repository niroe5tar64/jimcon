package jp.niro.jimcon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by niro on 2017/04/17.
 */
public class WindowManager {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private File dicDir = Paths.get("C:\\Users\\niro\\pleiades\\workspace\\jimcon\\src\\main\\resource\\jp\\niro\\jimcon").toFile();
    private URLClassLoader urlLoader;
    private ResourceBundle resources;

    public WindowManager(){
        try{
            urlLoader = new URLClassLoader(new URL[]{dicDir.toURI().toURL()});
            resources = ResourceBundle.getBundle("TextName", Locale.getDefault(),urlLoader);
            //resources "utf8", UTF8_ENCODING_CONTROL
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

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
            URL location = WindowManager.class.getResource("UnitOverview.fxml");
            FXMLLoader loader = new FXMLLoader(location, resources);
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
