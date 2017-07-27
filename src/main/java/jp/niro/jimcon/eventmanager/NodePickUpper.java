package jp.niro.jimcon.eventmanager;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by niro on 2017/07/25.
 */
public class NodePickUpper {
    private Collection<Node> ignoreList = new ArrayList<>();
    private Collection<Node> nodeList = new ArrayList<>();

    public boolean addIgnore(Node node) {
        return ignoreList.add(node);
    }

    public Collection<Node> start(Pane pane, Class<? extends Node> nodeType) {
        nextLayer(pane.getChildren(), nodeType);
        return nodeList;
    }

    @SuppressWarnings("unchecked")
    private void nextLayer(ObservableList<Node> nodes, Class<? extends Node> nodeType) {

        nodes.forEach(node -> {
            // ignoreListに無い場合
            if (isNotIgnore(node)) {

                if (isEnabled(node, nodeType)) {
                    nodeList.add(node);
                }

                try {
                    Method method = node.getClass().getMethod("getChildren");
                    nextLayer((ObservableList<Node>) method.invoke(node), nodeType);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException ignored) {

                }
            }

        });
    }

    private boolean isNotIgnore(Node node) {
        return !ignoreList.contains(node);
    }

    private boolean isEnabled(Node node, Class<? extends Node> nodeType) {
        // ノードタイプが一致するか、ノードIDが一致する
        return Objects.equals(nodeType, node.getClass());
    }
}
