package jp.niro.jimcon.eventmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by niro on 2017/07/24.
 */
public class ActionEventManager extends BaseEventManager implements EventHandler<ActionEvent> {

    private ActionEventManager(ActionBean actionBean, String methodName) {
        super(actionBean, methodName);
    }

    public static ActionEventManager setOnAction(ActionBean actionBean) {
        return new ActionEventManager(actionBean, "setOnAction");
    }

    @Override
    public void handle(ActionEvent event) {
        actionBean.action();
        KeyState.offPressed();
        KeyState.onActionEvent();
    }

    @Override
    public BaseEventManager setEvent(Node node) {
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
