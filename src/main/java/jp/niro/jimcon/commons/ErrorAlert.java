package jp.niro.jimcon.commons;

import javafx.scene.control.Alert;

/**
 * Created by niro on 2017/07/08.
 */
public class ErrorAlert {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    public ErrorAlert(String title, String headerText, String errorMessage) {
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);
    }

    public void show() {
        alert.show();
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
