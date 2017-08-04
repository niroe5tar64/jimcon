package jp.niro.jimcon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.customcomponents.ListTagCell;
import jp.niro.jimcon.customcomponents.flowlistview.ActionFlowListView;
import jp.niro.jimcon.customcomponents.flowlistview.FlowListView;
import jp.niro.jimcon.datamodel.*;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.dbaccess.SQL;
import jp.niro.jimcon.eventmanager.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by niro on 2017/04/21.
 */
public class ProductMasterController implements MasterController, TagSearchable {
    public static final String FXML_NAME = "ProductMaster.fxml";
    public static final String TITLE_NAME = "商品一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：商品コード";

    private Products products = new Products();
    private TagMapPool tagMapPool = TagMapPool.getInstance();
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    Pane pane;

    @FXML
    private TextField tagSearchField;
    @FXML
    private Button searchTagButton;
    @FXML
    private FlowListView<Tag> tagFlowList;
    @FXML
    private Button searchProductButton;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productCodeColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> sizeColorColumn;
    @FXML
    private TableColumn<Product, String> modelNumberColumn;
    @FXML
    private TableColumn<Product, String> anotherNameColumn;

    @FXML
    private Label productCodeLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label sizeColorLabel;
    @FXML
    private Label modelNumberLabel;
    @FXML
    private Label anotherNameLabel;
    @FXML
    private Label catalogPriceLabel;
    @FXML
    private Label standardUnitPriceLabel;
    @FXML
    private Label stockQuantityLabel;
    @FXML
    private Label unitNameLabel;
    @FXML
    private Label cuttingConstantLabel;
    @FXML
    private Label functionConstantLabel;
    @FXML
    private TextArea memoArea;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private CheckBox processedCheckBox;
    @FXML
    private CheckBox deletedCheckBox;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        SQL sql = null;
        try {
            sql = SQL.create();

            // productTableの初期設定
            products.load(sql);
            productTable.setItems(products.getData());
            productCodeColumn.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
            productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
            sizeColorColumn.setCellValueFactory(cellData -> cellData.getValue().sizeColorProperty());
            modelNumberColumn.setCellValueFactory(cellData -> cellData.getValue().modelNumberProperty());
            anotherNameColumn.setCellValueFactory(cellData -> cellData.getValue().anotherNameProperty());

            showProductDetails(null);
            productTable.getSelectionModel().selectedItemProperty().addListener(
                    ((observable, oldValue, newValue) -> showProductDetails(newValue))
            );

            // tagMapPoolの初期設定
            tagMapPool.loadTagMaps(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    public void setEvent() {
        // 各ダイアログ表示用アクション
        ActionBean showNew = new ActionMaster(ActionType.NEW, this);
        ActionBean showEdit = new ActionMaster(ActionType.EDIT, this);
        ActionBean showDelete = new ActionMaster(ActionType.DELETE, this);
        ActionBean closeDialog = new ActionMaster(ActionType.CLOSE, this);
        // 商品検索用アクション
        ActionBean searchProduct = new ActionMaster(ActionType.SEARCH, this);
        // 検索ダイアログ表示用アクション
        ActionBean showSearchTag = new ActionSearch(SearchType.TAG, this);
        // 選択タグの削除用アクション
        ActionBean removeTag = new ActionFlowListView(tagFlowList);

        // テーブルにフォーカスがある時のキーイベント
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ENTER, true, false, true, showNew, true)
                .setOnKeyReleased(KeyCode.ENTER, showEdit, true)
                .setOnKeyReleased(KeyCode.DELETE, showDelete, true)
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(productTable);

        // ボタンが押された時
        ActionEventManager.setOnAction(showSearchTag).setEvent(searchTagButton);
        ActionEventManager.setOnAction(searchProduct).setEvent(searchProductButton);
        ActionEventManager.setOnAction(showNew).setEvent(newButton);
        ActionEventManager.setOnAction(showEdit).setEvent(editButton);
        ActionEventManager.setOnAction(showDelete).setEvent(deleteButton);

        // タグ検索用テキストボックス選択時のキー操作
        ActionEventManager.setOnAction(showSearchTag).setEvent(tagSearchField);

        // FlowListView<Tag>選択時のキー操作
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.DELETE, removeTag, true)
                .setEvent(tagFlowList);

        // その他にフォーカスがある時
        KeyEventManager.create()
                .setOnKeyReleased(KeyCode.ESCAPE, closeDialog, true)
                .setEvent(pane);
    }

    @Override
    public void handleNew() {
        Product tempProduct = new Product();
        ObservableList<Tag> tempTagList = FXCollections.observableArrayList();
        SQL sql = null;
        try {
            sql = SQL.create();
            boolean isClosableDialog = false;
            boolean successSaveProduct;
            boolean successSaveTagMap;
            while (!isClosableDialog) {
                // 編集ダイアログ表示
                boolean okClicked = showProductEditDialog(tempProduct, tempTagList, true);
                if (okClicked) {
                    // ****** トランザクション開始 ******
                    sql.beginTransaction();
                    // Productが新規か否かの状態を取得し、DBにデータ登録する。
                    successSaveProduct = tempProduct.saveNewData(sql);

                    // TagMapの保存
                    TagMaps tagMaps = new TagMaps();
                    for (Tag tag : tempTagList) {
                        tagMaps.add(TagMapFactory.getInstance().getTagMapWithSave(sql, tag, tempProduct));
                    }
                    successSaveTagMap = tagMaps.save(sql);
                    isClosableDialog = successSaveProduct && successSaveTagMap;
                    // *********** コミット ***********
                    sql.commit();

                    // データテーブルをリロード
                    products.load(sql);
                    tagMapPool.loadTagMaps(sql);
                } else {
                    isClosableDialog = true;
                    // ********** ロールバック **********
                    sql.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ********** ロールバック **********
            sql.rollback();
        }
        if (sql != null) sql.close(); // 接続切断

        showProductDetails(productTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleEdit() {
        // メソッド内において取り扱うProduct及びList<Tag>の対応付け。
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        ObservableList<Tag> selectedTagList = tagListView.getItems();
        // データベース操作
        SQL sql = null;
        try {
            sql = SQL.create();
            ;
            boolean isClosableDialog = false;
            boolean successSaveProduct;
            boolean successSaveTagMap;
            if (selectedProduct != null) {
                // 編集ダイアログ表示
                boolean okClicked = showProductEditDialog(selectedProduct, selectedTagList, false);
                if (okClicked) {
                    // ****** トランザクション開始 ******
                    sql.beginTransaction();
                    // Productの保存
                    successSaveProduct = selectedProduct.saveEditedData(sql);

                    // TagMapの保存
                    TagMaps tagMaps = new TagMaps();
                    for (Tag tag : selectedTagList) {
                        tagMaps.add(TagMapFactory.getInstance().getTagMapWithSave(sql, tag, selectedProduct));
                    }
                    successSaveTagMap = tagMaps.save(sql);

                    isClosableDialog = successSaveProduct && successSaveTagMap;
                    // *********** コミット ***********
                    sql.commit();

                    // データテーブルをリロード
                    products.load(sql);
                    tagMapPool.loadTagMaps(sql);
                }
            } else {
                // Nothing selected.
                new WarningAlert(
                        NO_SELECTION_ERROR,
                        Product.NO_SELECTION,
                        ""
                ).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ********** ロールバック **********
            sql.rollback();
        }
        if (sql != null) sql.close(); // 接続切断

        showProductDetails(productTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleDelete() {
        // メソッド内において取り扱うProduct及びList<Tag>の対応付け。
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        ObservableList<Tag> selectedTagList = tagListView.getItems();
        // データベース操作
        SQL sql = null;
        try {
            sql = SQL.create();
            ;
            boolean isClosableDialog = false;
            boolean successDeleteProduct;
            boolean successDeleteTagMap;
            if (selectedProduct != null) {
                // ****** トランザクション開始 ******
                sql.beginTransaction();
                // Productの削除
                successDeleteProduct = selectedProduct.deleteData(sql);

                // TagMapの保存
                TagMaps tagMaps = new TagMaps();
                for (Tag tag : selectedTagList) {
                    tagMaps.add(TagMapFactory.getInstance().getTagMapWithSave(sql, tag, selectedProduct));
                }
                successDeleteTagMap = tagMaps.delete(sql);

                isClosableDialog = successDeleteProduct && successDeleteTagMap;
                // *********** コミット ***********
                sql.commit();

                // データテーブルをリロード
                products.load(sql);
                tagMapPool.loadTagMaps(sql);
            } else {
                // Nothing selected.
                new WarningAlert(
                        NO_SELECTION_ERROR,
                        Product.NO_SELECTION,
                        ""
                ).showAndWait();
                // ********** ロールバック **********
                sql.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ********** ロールバック **********
            sql.rollback();
        }
        if (sql != null) sql.close(); // 接続切断

        showProductDetails(productTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void handleClose() {
        stage.close();
    }

    @Override
    public void handleSearch() {
        SQL sql = null;
        try {
            sql = SQL.create();
            products.load(sql, tagFlowList.getItems());
            productTable.setItems(products.getData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql != null) sql.close(); // 接続切断
    }

    private void showProductDetails(Product product) {
        if (product != null) {
            productCodeLabel.setText(product.getProductCode());
            productNameLabel.setText(product.getProductName());
            sizeColorLabel.setText(product.getSizeColor());
            modelNumberLabel.setText(product.getModelNumber());
            anotherNameLabel.setText(product.getAnotherName());
            catalogPriceLabel.setText(Double.toString(product.getCatalogPrice()));
            unitNameLabel.setText(product.getUnit().getUnitName());
            standardUnitPriceLabel.setText(Double.toString(product.getStandardUnitPrice()));
            stockQuantityLabel.setText(Double.toString(product.getStockQuantity()));
            cuttingConstantLabel.setText(Double.toString(product.getCuttingConstant()));
            functionConstantLabel.setText(Double.toString(product.getFunctionConstant()));
            memoArea.setText(product.getMemo());
            tagListView.setItems(tagMapPool.getTagsData(LoginInfo.getInstance(), product.getProductCode()));
            tagListView.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
                @Override
                public ListCell<Tag> call(ListView<Tag> arg0) {
                    return new ListTagCell();
                }
            });
            processedCheckBox.setSelected(product.isProcessed());
            deletedCheckBox.setSelected(product.isDeleted());
        } else {
            productCodeLabel.setText("");
            productNameLabel.setText("");
            sizeColorLabel.setText("");
            modelNumberLabel.setText("");
            anotherNameLabel.setText("");
            catalogPriceLabel.setText("");
            unitNameLabel.setText("");
            standardUnitPriceLabel.setText("");
            stockQuantityLabel.setText("");
            cuttingConstantLabel.setText("");
            functionConstantLabel.setText("");
            memoArea.setText("");
            tagListView.setItems(null);
            tagListView.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
                @Override
                public ListCell<Tag> call(ListView<Tag> arg0) {
                    return new ListTagCell();
                }
            });
            processedCheckBox.setSelected(false);
            deletedCheckBox.setSelected(false);
        }
    }

    private boolean showProductEditDialog(Product product, ObservableList<Tag> tagList, boolean isNew) {
        try {
            // load the fxml file and getInstance a new stage for the pup-up dialog.
            URL location = WindowManager.class.getResource(ProductMasterEditController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(ProductMasterEditController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            ProductMasterEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setProduct(product);
            controller.setTagList(tagList);
            controller.setEvent();

            // 新規の場合、商品コードを編集不可にする。
            controller.setEditableForPKField(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void showSearchTagDialog() {
        try {
            URL location = WindowManager.class.getResource(TagSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            BorderPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagSearchDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            TagSearchDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            // TagSearchDialogControllerとProductOverviewControllerの紐付け
            controller.setTagSearchable(this);
            controller.setTagSearchField(getSearchValue());
            controller.setEvent();
            controller.handleSearch();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDisplay(Tag tag) {
        tagFlowList.addNonDuplication(tag);
        tagSearchField.clear();
    }

    @Override
    public String getSearchValue() {
        return tagSearchField.getText().trim();
    }

    @Override
    public void showSearchDialog(SearchType type) {
        switch (type) {
            case TAG:
                showSearchTagDialog();
                break;
        }
    }
}