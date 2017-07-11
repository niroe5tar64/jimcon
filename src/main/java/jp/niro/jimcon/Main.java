package jp.niro.jimcon;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.niro.jimcon.ui.MenuController;
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
            win.getPrimaryStage().setTitle(MenuController.TITLE_NAME);

            // Show the root layout.
            win.initRootLayout();
            win.setMenu();

            win.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
