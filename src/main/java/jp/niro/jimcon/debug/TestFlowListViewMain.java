package jp.niro.jimcon.debug;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Constant;
import jp.niro.jimcon.ui.ResourceBundleWithUtf8;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/05/26.
 */
public class TestFlowListViewMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            URL location = getClass().getResource("TestWithFlowListView.fxml");
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(Constant.Resources.Properties.TEXT_NAME)
            );
            Parent pane = loader.load();

            //TestWithFlowListViewController controller = loader.getController();

            Scene scene = new Scene(pane);

            stage.setTitle("Flow List View Demo");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
