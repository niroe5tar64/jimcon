package jp.niro.jimcon.eventmanager;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by niro on 2017/07/24.
 */
public class MouseEventManager extends BaseEventManager implements EventHandler<MouseEvent> {



    private MouseEventManager(ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
    }

    public static MouseEventManager setOnMouseClicked(ActionBeen actionBeen){
        return new MouseEventManager(actionBeen, "setOnMouseClicked");
    }

    @Override
    public void handle(MouseEvent event) {
        actionBeen.action();
        KeyState.offPressed();
    }

    @Override
    public BaseEventManager setEvent(Node node) {
        // メソッド配列を取得してループ
        try {
            Method method = node.getClass().getMethod(methodName, EventHandler.class);
            method.invoke(node, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignored) {

        }
        return this;
    }

    @Override
    public void setEvent(Collection<Node> nodes) {
        // メソッド配列を取得してループ
        nodes.forEach(node -> {
            try {
                Method method = node.getClass().getMethod(methodName);
                method.invoke(node, this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) {

            }
        });
    }
}
