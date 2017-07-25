package jp.niro.jimcon.eventhelper;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by niro on 2017/07/24.
 */
public class MouseEventBeen extends EventBeen implements EventHandler<MouseEvent> {



    private MouseEventBeen(ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
    }

    public static MouseEventBeen setOnMouseClicked(ActionBeen actionBeen){
        return new MouseEventBeen(actionBeen, "setOnMouseClicked");
    }

    @Override
    public void handle(MouseEvent event) {
        actionBeen.action();
    }

    @Override
    public void setEvent(Node node) {
        // メソッド配列を取得してループ
        try {
            Method method = node.getClass().getMethod(methodName, EventHandler.class);
            method.invoke(node, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignored) {

        }
    }
}
