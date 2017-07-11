package jp.niro.jimcon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jp.niro.jimcon.commons.WarningAlert;
import jp.niro.jimcon.datamodel.Product;
import jp.niro.jimcon.datamodel.Products;
import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.datamodel.TagMaps;
import jp.niro.jimcon.dbaccess.LoginInfo;
import jp.niro.jimcon.flowlistview.FlowListView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by niro on 2017/04/21.
 */
public class ProductOverviewWithTagController implements TagSearchable {
    public static final String FXML_NAME = "ProductOverviewWithTag.fxml";
    public static final String TITLE_NAME = "商品一覧";
    public static final String NO_SELECTION_ERROR = "No Selection Error：商品コード";

    private Products products = new Products();
    private TagMaps tagMaps = new TagMaps();
    private Stage ownerStage;

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private TextField tagSearchField;
    @FXML
    private FlowListView<Tag> tagFlowList;

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
    private ListView<Tag> tagList;
    @FXML
    private CheckBox processedCheckBox;
    @FXML
    private CheckBox deletedCheckBox;

    @FXML
    private void initialize() {
        // productTableの初期設定
        products.loadProducts(LoginInfo.create());
        productTable.setItems(products.getProducts());

        productCodeColumn.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        sizeColorColumn.setCellValueFactory(cellData -> cellData.getValue().sizeColorProperty());
        modelNumberColumn.setCellValueFactory(cellData -> cellData.getValue().modelNumberProperty());
        anotherNameColumn.setCellValueFactory(cellData -> cellData.getValue().anotherNameProperty());

        // tagListの初期設定
        tagMaps.loadTagMaps(LoginInfo.create());

        showProductDetails(null);

        productTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showProductDetails(newValue))
        );
    }

    @FXML
    private void handleTagSearch() {
        try {
            URL location = WindowManager.class.getResource(TagSearchDialogController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(TagSearchDialogController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            TagSearchDialogController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            // TagSearchDialogControllerとProductOverviewControllerの紐付け
            controller.setTagSearchable(this);
            controller.load();

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductSearch(){

    }

    @FXML
    private void handleNewProduct() {
        Product tempProduct = new Product();
        boolean isClosableDialog = false;
        while (!isClosableDialog) {
            boolean okClicked = showProductEditDialog(tempProduct, true);
            if (okClicked) {
                // DBにデータ登録し、新規か否かの状態を取得する。
                isClosableDialog = tempProduct.saveNewData(LoginInfo.create());
                // データテーブルをリロード
                products.loadProducts(LoginInfo.create());
            } else {
                isClosableDialog = true;
            }
        }
        showProductDetails(productTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = showProductEditDialog(selectedProduct, false);
            if (okClicked) {
                selectedProduct.saveEditedData(LoginInfo.create());
                products.loadProducts(LoginInfo.create());
            }

        } else {
            // Nothing selected.
            new WarningAlert(NO_SELECTION_ERROR,
                    Product.NO_SELECTION,
                    ""
            ).showAndWait();
        }
        showProductDetails(productTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteProduct() {
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
            tagList.setItems(tagMaps.getTagsData(LoginInfo.create(), product.getProductCode()));
            tagList.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
                @Override
                public ListCell<Tag> call(ListView<Tag> arg0) {
                    return new TagCell();
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
            processedCheckBox.setSelected(false);
            deletedCheckBox.setSelected(false);
        }
    }

    private boolean showProductEditDialog(Product product, boolean isNew) {
        try {
            // load the fxml file and create a new stage for the pup-up dialog.
            URL location = WindowManager.class.getResource(ProductEditDialogWithTagController.FXML_NAME);
            FXMLLoader loader = new FXMLLoader(
                    location, ResourceBundleWithUtf8.create(ResourceBundleWithUtf8.TEXT_NAME));
            AnchorPane pane = loader.load();

            // Create the dialog stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(ProductEditDialogWithTagController.TITLE_NAME);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the Product into the controller.
            ProductEditDialogWithTagController controller = loader.getController();
            controller.setOwnerStage(dialogStage);
            controller.setProduct(product);

            // 新規の場合、商品コードを編集不可にする。
            controller.getProductCodeField().editableProperty().set(isNew);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateDisplay(Tag tag) {
        tagFlowList.getItems().add(tag);
    }

    @Override
    public String getSearchValue(){
        return tagSearchField.getText().trim();
    }

    private static class TagCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);
            if(!empty) {
                setText(tag.getTagName());
            }
        }
    }
}