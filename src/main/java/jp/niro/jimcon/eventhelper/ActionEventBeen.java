package jp.niro.jimcon.eventhelper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by niro on 2017/07/24.
 */
public class ActionEventBeen extends EventBeen implements EventHandler<ActionEvent> {

    private ActionEventBeen(ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
    }

    public static ActionEventBeen setOnAction(ActionBeen actionBeen) {
        return new ActionEventBeen(actionBeen, "setOnAction");
    }

    @Override
    public void handle(ActionEvent event) {
        actionBeen.action(node);
    }

}
