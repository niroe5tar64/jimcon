package jp.niro.jimcon.eventhelper;

import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by niro on 2017/07/25.
 */
public class SameOnAction implements ActionBeen {

    @Override
    public void action(Node node) {
        try {
            Method method = node.getClass().getMethod("getOnAction");
            if (method == null) {
                System.out.println("this node doesn't have the method of getOnAction");
            } else {
                System.out.println(node.getId());
                method.invoke(node);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

