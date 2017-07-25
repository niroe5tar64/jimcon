package jp.niro.jimcon.eventhelper;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by niro on 2017/07/22.
 */
public class NodeEventHelper {
    private Collection<Node> ignoreList = new ArrayList<>();
    private Collection<NodeTypeEvent> nodeTypeEventList = new ArrayList<>();

    public boolean addIgnore(Node node) {
        return ignoreList.add(node);
    }

    public boolean addNodeEvent(Class<? extends Node> nodeType, EventBeen eventBeen) {
        return nodeTypeEventList.add(new NodeTypeEvent(nodeType, eventBeen));
    }

    public void start(Pane pane) {
        nextLayer(pane.getChildren());
    }

    @SuppressWarnings("unchecked")
    private void nextLayer(ObservableList<Node> nodes) {
        nodes.forEach(node -> {
            // ignoreListに無い場合
            if (isNotIgnore(node)) {

                // メソッド配列を取得してループ
                for (Method method : node.getClass().getMethods()) {

                    // ノードイベントリストをループして、メソッドが存在する場合
                    nodeTypeEventList.forEach(nodeTypeEvent -> {
                        //
                        if (isEnabled(nodeTypeEvent, method, node)) {
                            try {
                                method.invoke(node, nodeTypeEvent.getEventBeen());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    if (isExistedChildren(method)) {
                        try {
                            nextLayer((ObservableList<Node>) method.invoke(node));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });
    }


    private boolean isNotIgnore(Node node) {
        return !ignoreList.contains(node);
    }

    private boolean isEnabled(NodeTypeEvent nodeTypeEvent, Method method, Node node) {
        // メソッド名が一致する場合で、
        if (Objects.equals(nodeTypeEvent.getMethodName(), method.getName())) {
            // ノードタイプが一致するか、ノードIDが一致する
            if (Objects.equals(nodeTypeEvent.getNodeType(), node.getClass())) {
                return true;
            }
        }
        return false;
    }


    private boolean isExistedChildren(Method method) {
        return Objects.equals(method.getName(), "getChildren");
    }

}
