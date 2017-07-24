package jp.niro.jimcon.eventhelper;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Created by niro on 2017/07/22.
 */
public class EventBeen<T extends Event> implements EventHandler<T> {

    private ActionBeen actionBeen;

    private String methodName;

    private EventBeen(ActionBeen actionBeen, String methodName) {
        this.actionBeen = actionBeen;
        this.methodName = methodName;
    }

    public static EventBeen create(ActionBeen actionBeen) {
        return new EventBeen(actionBeen, "this method is unimplemented.");
    }

    public static EventBeen setOnAction(ActionBeen actionBeen) {
        return new EventBeen(actionBeen, "setOnAction");
    }

    String getMethodNeme() {
        return methodName;
    }

    @Override
    public void handle(T event) {
        actionBeen.action();
    }
}
