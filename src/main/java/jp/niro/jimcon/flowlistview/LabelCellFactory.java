package jp.niro.jimcon.flowlistview;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.util.Callback;

/**
 * Created by niro on 2017/05/17.
 */
public class LabelCellFactory<T> implements Callback<Void, NodeCellPair<T>> {

    private String customStyle = "-fx-border-color: #b2b2b2;";

    @Override
    public NodeCellPair<T> call(Void v) {
        final Label node;

        node = new Label();
        node.setPadding(new Insets(2d));

        if (customStyle != null) {
            node.setStyle(customStyle);
        }

        // the Cellable just sets the string as the label text
        Cellable<T> cell = new Cellable<T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                if (item != null) {
                    node.setText(item.toString());
                } else {
                    node.setText("null");
                }
            }
        };

        return new NodeCellPair<T>(node, cell);
    }

    public String getCustomStyle() {
        return customStyle;
    }

    public void setCustomStyle(String customStyle) {
        assert Platform.isFxApplicationThread();

        this.customStyle = customStyle;
    }

}
