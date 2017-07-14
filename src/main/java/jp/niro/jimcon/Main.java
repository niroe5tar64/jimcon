package jp.niro.jimcon;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.niro.jimcon.ui.LoginController;
import jp.niro.jimcon.ui.WindowManager;

/**
 * Created by niro on 2017/07/08.
 */
public class Main extends Application {
    private WindowManager win = new WindowManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            win.setPrimaryStage(primaryStage);
            win.getPrimaryStage().setTitle(LoginController.TITLE_NAME);

            // Show the root layout.
            win.init();

            win.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
