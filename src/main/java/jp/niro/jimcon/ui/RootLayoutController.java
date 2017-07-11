package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by niro on 2017/04/18.
 */
public class RootLayoutController {
    public static final String FXML_FILE = "RootLayout.fxml";

    // Reference to the stage.
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    @FXML
    private void handleNew() {
        System.out.println("click on the menu of New");
    }



}
