package jp.niro.jimcon.eventhelper;

import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by niro on 2017/07/22.
 */
public class EventBeen {

    ActionBeen actionBeen;
    String methodName;

    EventBeen(ActionBeen actionBeen, String methodName) {
        this.actionBeen = actionBeen;
        this.methodName = methodName;
    }

    String getMethodNeme() {
        return methodName;
    }


    public void setEvent(Node node) {
        // メソッド配列を取得してループ
        try {
            Method method = node.getClass().getMethod(methodName);
            method.invoke(node, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ignored) {

        }
    }
}
