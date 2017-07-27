package jp.niro.jimcon.eventmanager;

import javafx.event.EventHandler;
import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by niro on 2017/07/22.
 */
public class BaseEventManager {
    static boolean nowPressed = false;

    ActionBeen actionBeen;
    String methodName;

    BaseEventManager(ActionBeen actionBeen, String methodName) {
        this.actionBeen = actionBeen;
        this.methodName = methodName;
    }

    String getMethodNeme() {
        return methodName;
    }


    public BaseEventManager setEvent(Node node) {
        try {
            Method method = node.getClass().getMethod(methodName, EventHandler.class);
            method.invoke(node, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignored) {

        }
        return this;
    }

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
