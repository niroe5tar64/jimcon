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
            win.getPrimaryStage().setTitle("テストテスト");

            // Show the root layout.
            win.initRootLayout();

            win.setMenu();

            // Show the unit overview.
            //win.setUnitOverview();
            //win.setProductOverview();
            //win.setDepartmentOverview();

            win.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
