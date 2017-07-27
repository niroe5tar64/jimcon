package jp.niro.jimcon.eventmanager;

import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/25.
 */
class KeyEventBeen {
    String methodName;
    KeyCode keyCode;
    boolean controlDown;
    boolean altDown;
    ActionBeen actionBeen;
    boolean shiftDown;

    final boolean withPressed;

    KeyEventBeen(String methodName,
                 KeyCode keyCode,
                 boolean controlDown,
                 boolean altDown,
                 boolean shiftDown,
                 ActionBeen actionBeen,
                 boolean withPressed) {
        this.methodName = methodName;
        this.keyCode = keyCode;
        this.controlDown = controlDown;
        this.altDown = altDown;
        this.shiftDown = shiftDown;
        this.actionBeen = actionBeen;
        this.withPressed = withPressed;
    }
}
