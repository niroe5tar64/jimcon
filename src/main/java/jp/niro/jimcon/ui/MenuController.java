package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jp.niro.jimcon.commons.Validator;
import jp.niro.jimcon.eventmanager.ActionBeen;
import jp.niro.jimcon.eventmanager.ActionEventManager;
import jp.niro.jimcon.eventmanager.KeyEventManager;
import jp.niro.jimcon.eventmanager.NodePickUpper;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by niro on 2017/05/12.
 */
public class MenuController {
    public static final String FXML_NAME = "Menu.fxml";
    public static final String TITLE_NAME = "メインメニュー";

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private BorderPane pane;

    @FXML
    private Button departmentMasterButton;
    @FXML
    private Button unitMasterButton;
    @FXML
    private Button productMasterButton;
    @FXML
    private Button tagMasterButton;

    public void setEvent() {
        // 画面上の全てのButtonを取得して一括設定。
        NodePickUpper pickUpper = new NodePickUpper();
        Collection<Node> buttons = pickUpper.start(pane, Button.class);
        buttons.forEach(button -> {
            // ダイアログ表示用アクション
            ActionBeen showDialog = new ShowDialog(this, button);

            // ボタンを押した場合
            ActionEventManager.setOnAction(showDialog)
                    .setEvent(button);
            // ボタンにフォーカスがある時にEnterを押した場合
            KeyEventManager.create()
                    .setOnKeyReleased(KeyCode.ENTER, showDialog, true)
                    .setEvent(button);
        });
    }

    private void showDepartmentMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(DepartmentOverviewController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(DepartmentOverviewController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            DepartmentOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setEvent();
            dialogStage.setResizable(false);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUnitMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(UnitOverviewController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(UnitOverviewController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            UnitOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProductMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(ProductOverviewWithTagController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(ProductOverviewWithTagController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            ProductOverviewWithTagController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTagMaster() {
        try {
            // FXMLファイルをPaneにロードする。
            URL location = WindowManager.class.getResource(TagOverviewController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            Pane pane = loader.load();

            // Stageを新たに生成する。
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagOverviewController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // コントローラーをセットする。
            TagOverviewController controller = loader.getController();
            controller.setOwnerStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ShowDialog implements ActionBeen {
        MenuController controller;
        Node node;

        private ShowDialog() {
        }

        ShowDialog(MenuController controller, Node node) {
            this.node = node;
            this.controller = controller;
        }

        @Override
        public void action() {
            if (Validator.isEqual(node, controller.departmentMasterButton)) {
                controller.showDepartmentMaster();
            } else if (Validator.isEqual(node, controller.unitMasterButton)) {
                controller.showUnitMaster();
            } else if (Validator.isEqual(node, controller.productMasterButton)) {
                controller.showProductMaster();
            } else if (Validator.isEqual(node, controller.tagMasterButton)) {
                controller.showTagMaster();
            }
        }
    }

}
