package jp.niro.jimcon.eventhelper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

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
        actionBeen.action();
        nowPressed = false;
    }

    @Override
    public EventBeen setEvent(Node node) {
        try {
            Method method = node.getClass().getMethod(methodName, EventHandler.class);
            method.invoke(node,this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignored) {

        }
        return this;
    }

    @Override
    public void setEvent(Collection<Node> nodes) {
        nodes.forEach(node -> {
            try {
                Method method = node.getClass().getMethod(methodName, EventHandler.class);
                method.invoke(node, this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) {

            }
        });
    }
}
