package jp.niro.jimcon.eventmanager;

import javafx.scene.input.MouseButton;

/**
 * Created by niro on 2017/07/31.
 */
public class MouseEventBean {
    String methodName;
    MouseButton mouseButton;
    int clickCount;
    ActionBean actionBean;

    MouseEventBean(String methodName,
                   MouseButton mouseButton,
                   int clickCount,
                   ActionBean actionBean) {
        this.methodName = methodName;
        this.mouseButton = mouseButton;
        this.clickCount = clickCount;
        this.actionBean = actionBean;
    }
}
