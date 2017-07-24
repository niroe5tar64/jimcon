package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventhelper.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/07/13.
 */
public class LoginController {
    public static final String FXML_NAME = "Login.fxml";
    public static final String TITLE_NAME = "ログイン画面";
    public static final String ACCESS_DENIED_MESSAGE = "ユーザー名 パスワードが違います。";
    public static final int ACCESS_DENIED = 1045;

    private FXRobot robot;
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEvent() {
        robot = FXRobotFactory.createRobot(primaryStage.getScene());

        EventBeen focusNext = EventBeen.create(new RobotKeyPress(robot, KeyCode.TAB));
        EventBeen executeLogin = EventBeen.create(new ExecuteLogin(this));

        userNameField.setOnAction(focusNext);
        passwordField.setOnAction(executeLogin);
    }

    @FXML
    private GridPane pane;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text actionTarget;

    @FXML
    private void initialize() {
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        this.executeLogin();
    }

    private void executeLogin() {

        LoginInfo loginInfo = LoginInfo.getInstance();
        loginInfo.setLoginInfo(
                LoginInfo.DEFAULT_DRIVER,
                LoginInfo.DEFAULT_URL,
                userNameField.getText().trim(),
                passwordField.getText()
        );
        // ログインチェック
        SQL sql = null;
        try {
            sql = SQL.create();

            // メニュー画面を開く
            this.setMenu();

        } catch (SQLException e) {
            if (e.getErrorCode() == ACCESS_DENIED) {
                actionTarget.setText(ACCESS_DENIED_MESSAGE);
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sql != null) {
            sql.close();
        }
    }

    void setMenu() throws IOException {
        // FXMLファイルをPaneにロードする。
        URL location = WindowManager.class.getResource(MenuController.FXML_NAME);
        FXMLLoader loader = new FXMLLoader(
                location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
        Pane pane = loader.load();

        primaryStage.setTitle(MenuController.TITLE_NAME);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        // コントローラーをセットする。
        MenuController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
    }




    private static class ExecuteLogin implements ActionBeen {
        private ExecuteLogin(){}

        LoginController controller;

        ExecuteLogin(LoginController controller) {
            this.controller = controller;
        }

        @Override
        public void action() {
            controller.executeLogin();
        }
    }
}
