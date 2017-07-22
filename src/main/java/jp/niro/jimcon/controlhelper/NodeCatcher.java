package jp.niro.jimcon.controlhelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class NodeCatcher {
    private Collection<String> ignoreList = new ArrayList<>();

    public boolean add(String name) {
        return ignoreList.add(name);
    }

    public void start(Pane pane) {
        nextLayer(pane.getChildren());
    }

    private void nextLayer(ObservableList<Node> nodes) {
        nodes.forEach(node -> {
            System.out.println(node.getClass().getName() + " : " + node.getId());
            if (isEnabled(node)) {
                Class<? extends Node> classInstance = node.getClass();

                Method[] methods = classInstance.getMethods();
                for (Method method : methods) {
                    if (isExist(method, "setOnAction")) {
                        try {
                            method.invoke(node, new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println(node.getClass().getName());
                                }
                            });
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }


                    }


                    if (isExist(method, "getChildren")) {
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


    private void doSetOnAction(Method method) {

    }


    private boolean isExist(Method method, String methodName) {
        return Objects.equals(method.getName(), methodName);
    }

    private boolean isEnabled(Node node) {
        boolean containsNodeType = ignoreList.contains(node.getClass().getName());
        boolean containsNodeId = ignoreList.contains(node.getId());

        return !(containsNodeType || containsNodeId);
    }

    private class EnableNode {
        String node;
        boolean enable;

        EnableNode(String node, boolean enable) {
            this.node = node;
            this.enable = enable;
        }
    }
}
