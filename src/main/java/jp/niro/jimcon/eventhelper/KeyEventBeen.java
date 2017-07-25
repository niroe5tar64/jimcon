package jp.niro.jimcon.eventhelper;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by niro on 2017/07/24.
 */
public class KeyEventBeen extends EventBeen implements EventHandler<KeyEvent> {
    private KeyCode keyCode;
    private boolean controlDown;
    private boolean altDown;
    private boolean shiftDown;

    private KeyEventBeen(KeyCode keyCode,
                         ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
        this.keyCode = keyCode;
        this.controlDown = false;
        this.altDown = false;
        this.shiftDown = false;
    }

    private KeyEventBeen(KeyCode keyCode, boolean controlDown, boolean altDown, boolean shiftDown,
                         ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
        this.keyCode = keyCode;
        this.controlDown = controlDown;
        this.altDown = altDown;
        this.shiftDown = shiftDown;
    }

    public static KeyEventBeen setOnKeyPressed(KeyCode keyCode, ActionBeen actionBeen) {
        return new KeyEventBeen(keyCode, actionBeen, "setOnKeyPressed");
    }

    public static KeyEventBeen setOnKeyReleased(KeyCode keyCode, ActionBeen actionBeen) {
        return new KeyEventBeen(keyCode, actionBeen, "setOnKeyReleased");
    }

    public static KeyEventBeen setOnKeyTyped(KeyCode keyCode, ActionBeen actionBeen) {
        return new KeyEventBeen(keyCode, actionBeen, "setOnKeyTyped");
    }

    public static KeyEventBeen setOnKeyPressed(KeyCode keyCode,
                                               boolean controlDown,
                                               boolean altDown,
                                               boolean shiftDown,
                                               ActionBeen actionBeen) {
        return new KeyEventBeen(keyCode, actionBeen, "setOnKeyPressed");
    }

    public static KeyEventBeen setOnKeyReleased(KeyCode keyCode,
                                                boolean controlDown,
                                                boolean altDown,
                                                boolean shiftDown,
                                                ActionBeen actionBeen) {
        return new KeyEventBeen(keyCode, actionBeen, "setOnKeyReleased");
    }

    @Override
    public void handle(KeyEvent event) {
        if (controlDown != event.isControlDown()) return;
        if (altDown != event.isAltDown()) return;
        if (shiftDown != event.isShiftDown()) return;
        if (keyCode != event.getCode()) return;

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
