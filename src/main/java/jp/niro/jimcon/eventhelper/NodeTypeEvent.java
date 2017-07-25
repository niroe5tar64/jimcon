package jp.niro.jimcon.eventhelper;

import javafx.scene.Node;

/**
 * Created by niro on 2017/07/22.
 */
class NodeTypeEvent {
    private Class<? extends Node> nodeType;
    private EventBeen eventBeen;

    NodeTypeEvent(Class<? extends Node> nodeType, EventBeen eventBeen) {
        this.nodeType = nodeType;
        this.eventBeen = eventBeen;
    }

    EventBeen getEventBeen() {
        return eventBeen;
    }

    Class<? extends Node> getNodeType() {
        return nodeType;
    }

    String getMethodName() {
        return eventBeen.getMethodNeme();
    }
}
