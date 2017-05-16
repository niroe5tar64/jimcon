package jp.niro.jimcon;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.niro.jimcon.ui.WindowManager;


public class Main extends Application {
    private WindowManager win = new WindowManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            win.setPrimaryStage(primaryStage);
            win.getPrimaryStage().setTitle("test menu");

            // Show the root layout.
            win.initRootLayout();
            win.setMenu();

            win.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
