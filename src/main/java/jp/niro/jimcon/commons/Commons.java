package jp.niro.jimcon.commons;

import javafx.scene.control.Alert;

/**
 * Created by niro on 2017/04/30.
 */
public class Commons {
    public static void showErrorAlert(String title, String headerText, String errorMessage, boolean wait) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        if (wait){
            alert.showAndWait();
        } else {
            alert.show();
        }
    }
    public static void showWarningAlert(String title, String headerText, String errorMessage, boolean wait) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        if (wait){
            alert.showAndWait();
        } else {
            alert.show();
        }
    }
}
