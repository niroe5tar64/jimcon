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
    private Collection<String> ignoreList = new ArrayList<>();
    private Collection<NodeEvent> nodeEventList = new ArrayList<>();

    public boolean addIgnore(String name) {
        return ignoreList.add(name);
    }

    public boolean addNodeEvent(String nodeType, EventBeen eventBeen) {
        return nodeEventList.add(new NodeEvent(nodeType, eventBeen));
    }

    public boolean addNodeEvent(Node node, EventBeen eventBeen) {
        return nodeEventList.add(new NodeEvent(node.getId(), eventBeen));
    }

    public void start(Pane pane) {
        nextLayer(pane.getChildren());
    }

    private void nextLayer(ObservableList<Node> nodes) {
        nodes.forEach(node -> {
            // ignoreListに無い場合
            if (isNotIgnore(node)) {

                // メソッド配列を取得してループ
                for (Method method : node.getClass().getMethods()) {

                    // ノードイベントリストをループして、メソッドが存在する場合
                    nodeEventList.forEach(nodeEvent -> {
                        //
                        if (isEnabled(nodeEvent, method, node)) {
                            try {
                                method.invoke(node, nodeEvent.getEventBeen());
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
        boolean containsNodeType = ignoreList.contains(node.getClass().getName());
        boolean containsNodeId = ignoreList.contains(node.getId());

        return !(containsNodeType || containsNodeId);
    }

    private boolean isEnabled(NodeEvent nodeEvent, Method method, Node node) {
        // メソッド名が一致する場合で、
        if (Objects.equals(nodeEvent.getMethodName(), method.getName())) {
            // ノードタイプが一致するか、ノードIDが一致する
            if (Objects.equals(nodeEvent.getNodeName(), node.getClass().getName())) {
                return true;
            } else if (Objects.equals(nodeEvent.getNodeName(), node.getId())) {
                return true;
            }
        }
        return false;
    }


    private boolean isExistedChildren(Method method) {
        return Objects.equals(method.getName(), "getChildren");
    }
}
