package jp.niro.jimcon.ui;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.ActionBeen;
import jp.niro.jimcon.eventmanager.ActionEventManager;
import jp.niro.jimcon.eventmanager.KeyEventManager;
import jp.niro.jimcon.eventmanager.RobotKeyPress;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/07/13.
 */
public class LoginController {
    public static final String FXML_NAME = "Login.fxml";
    public static final String TITLE_NAME = "ログイン画面";
    private static final String ACCESS_DENIED_MESSAGE = "ユーザー名 パスワードが違います。";
    private static final int ACCESS_DENIED = 1045;

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private GridPane pane;

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Text actionTarget;

    public void setEvent() {
        FXRobot robot = FXRobotFactory.createRobot(primaryStage.getScene());

        // フォーカス移動用アクション
        ActionBeen focusNext = new RobotKeyPress(robot, KeyCode.TAB);

        ActionEventManager.setOnAction(focusNext)
                .setEvent(userNameField);

        // ログイン処理実行用アクション
        ActionBeen executeLogin = new ExecuteLogin(this);

        ActionEventManager.setOnAction(executeLogin)
                .setEvent(passwordField)
                .setEvent(loginButton);

        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, executeLogin, true)
                .setEvent(loginButton);
    }

    // ログイン処理実行
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

    // メニュー画面を開く
    private void setMenu() throws IOException {
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
        controller.setEvent();
    }


    private static class ExecuteLogin implements ActionBeen {
        private ExecuteLogin() {
        }

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
