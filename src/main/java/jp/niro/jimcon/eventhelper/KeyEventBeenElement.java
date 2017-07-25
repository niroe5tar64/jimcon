package jp.niro.jimcon.eventhelper;

import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/25.
 */
class KeyEventBeenElement {
    KeyCode keyCode;
    boolean controlDown;
    boolean altDown;
    boolean shiftDown;
    ActionBeen actionBeen;


    KeyEventBeenElement(KeyCode keyCode,
                        ActionBeen actionBeen) {
        this.keyCode = keyCode;
        this.controlDown = false;
        this.altDown = false;
        this.shiftDown = false;
        this.actionBeen = actionBeen;
    }

    KeyEventBeenElement(KeyCode keyCode,
                        boolean controlDown,
                        boolean altDown,
                        boolean shiftDown,
                        ActionBeen actionBeen) {
        this.keyCode = keyCode;
        this.controlDown = controlDown;
        this.altDown = altDown;
        this.shiftDown = shiftDown;
        this.actionBeen = actionBeen;
    }
}
