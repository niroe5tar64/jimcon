package jp.niro.jimcon.flowlistview;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

import java.io.IOException;

public class FlowListView<E> extends ScrollPane {
    @FXML
    private FlowPane flowPane;

    private final ObservableList<E> items;

    private ItemModel<E> itemConverter;

    public FlowListView() {
        // FlowListView.fxmlに関連付け
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("FlowListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // インスタンスの初期化
        items = FXCollections.observableArrayList();
        itemConverter = new ItemModel<E>();

         /*本家FlowListViewではinitializeメソッドに記述されていたが、
         参考にしたカスタムコントロール作成例の方法では
         initializeメソッドが読み込まれない為以下に記述
         参考URL
         https://github.com/andytill/flow-list-view
         https://docs.oracle.com/javase/jp/8/javafx/fxml-tutorial/custom_control.htm*/
        // minus this so that the flow pane is smaller than the scroll pane
        // by default, if it is the same size the bars will always show
        DoubleBinding contentWidth = widthProperty().add(-2);
        flowPane.prefWidthProperty().bind(contentWidth);
        flowPane.setStyle("");
        Bindings.bindContentBidirectional(flowPane.getChildren(), itemConverter.getFlowNodes());

        items.addListener(itemConverter);
    }

    public ObservableList<E> getItems() {
        return items;
    }

    public Callback<Void, NodeCellPair<E>> getCellFactory() {
        return itemConverter.getCellFactory();
    }

    public void setCellFactory(Callback<Void, NodeCellPair<E>> cellFactory) {
        assert Platform.isFxApplicationThread();

        itemConverter.setCellFactory(cellFactory);
    }

    public ObjectProperty<E> selectedItemProperty() {
        return itemConverter.selectedItemProperty();
    }

}
