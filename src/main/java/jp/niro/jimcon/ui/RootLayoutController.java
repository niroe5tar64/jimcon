package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by niro on 2017/04/18.
 */
public class RootLayoutController {
    // Reference to the stage.
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    @FXML
    public void handleNew() {
        System.out.println("click on the menu of New");
    }



}
