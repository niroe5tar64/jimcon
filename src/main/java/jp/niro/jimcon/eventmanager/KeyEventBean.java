package jp.niro.jimcon.eventmanager;

import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/25.
 */
class KeyEventBean {
    String methodName;
    KeyCode keyCode;
    boolean controlDown;
    boolean altDown;
    ActionBean actionBean;
    boolean shiftDown;

    final boolean withPressed;

    KeyEventBean(String methodName,
                 KeyCode keyCode,
                 boolean controlDown,
                 boolean altDown,
                 boolean shiftDown,
                 ActionBean actionBean,
                 boolean withPressed) {
        this.methodName = methodName;
        this.keyCode = keyCode;
        this.controlDown = controlDown;
        this.altDown = altDown;
        this.shiftDown = shiftDown;
        this.actionBean = actionBean;
        this.withPressed = withPressed;
    }
}
