package jp.niro.jimcon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.FXRobotController;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.SQL;

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

    private FXRobotController robotController;
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
    private Text actionTarget;

    @FXML
    private void initialize() {
        /*try {
            Class classInstance = Class.forName(pane.getScene().getFocusOwner().getClass().getName());
            Method method = classInstance.getMethod("setOnAction");
            //method.invoke(classInstance, );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/




        /*pane.setOnKeyPressed(event -> {
            robotController = FXRobotController.create(pane.getScene(), event);
            robotController.addEnableClassList(TextField.class);
            robotController.addEnableClassList(PasswordField.class);
            robotController.keyPress(KeyCode.ENTER ,KeyCode.TAB);
        });*/

        // テスト用
        userNameField.setOnAction(event -> {

                System.out.println("aaa");

        });
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
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
            sql = new SQL(loginInfo.getConnection());

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
    }
}
