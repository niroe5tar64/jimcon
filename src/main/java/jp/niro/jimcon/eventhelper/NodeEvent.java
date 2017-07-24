package jp.niro.jimcon.eventhelper;

/**
 * Created by niro on 2017/07/22.
 */
class NodeEvent {
    private String nodeName;
    private EventBeen eventBeen;

    NodeEvent(String nodeName, EventBeen eventBeen) {
        this.nodeName = nodeName;
        this.eventBeen = eventBeen;
    }

    String getNodeName() {
        return nodeName;
    }

    EventBeen getEventBeen() {
        return eventBeen;
    }

    String getMethodName() {
        return eventBeen.getMethodNeme();
    }
}
