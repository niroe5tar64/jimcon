package jp.niro.jimcon.eventhelper;

import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/25.
 */
class KeyEventBeenElement {
    String methodName;
    KeyCode keyCode;
    boolean controlDown;
    boolean altDown;
    ActionBeen actionBeen;
    boolean shiftDown;

    final boolean withPressed;

    KeyEventBeenElement(String methodName,
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
