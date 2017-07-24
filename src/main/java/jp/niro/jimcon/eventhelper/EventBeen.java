package jp.niro.jimcon.eventhelper;

import javafx.scene.Node;

/**
 * Created by niro on 2017/07/22.
 */
public class EventBeen {

    ActionBeen actionBeen;
    private String methodName;
    protected Node node;

    EventBeen(ActionBeen actionBeen, String methodName) {
        this.actionBeen = actionBeen;
        this.methodName = methodName;
    }

    private EventBeen(ActionBeen actionBeen, String methodName, Node node) {
        this.actionBeen = actionBeen;
        this.methodName = methodName;
        this.node = node;
    }

    EventBeen setThenGet(Node node) {
        this.node = node;
        return this;
    }

    String getMethodNeme() {
        return methodName;
    }
}
