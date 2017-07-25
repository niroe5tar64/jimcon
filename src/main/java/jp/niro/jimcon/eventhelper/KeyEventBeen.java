package jp.niro.jimcon.eventhelper;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by niro on 2017/07/24.
 */
public class KeyEventBeen extends EventBeen implements EventHandler<KeyEvent> {
    private Collection<KeyEventBeenElement> elements = new ArrayList<>();

    public KeyEventBeen add(KeyEventBeenElement element) {
        elements.add(element);
        return this;
    }

    public KeyEventBeen add(KeyCode keyCode, ActionBeen actionBeen) {
        elements.add(new KeyEventBeenElement(keyCode, actionBeen));
        return this;
    }

    public KeyEventBeen add(KeyCode keyCode,
                            boolean controlDown,
                            boolean altDown,
                            boolean shiftDown,
                            ActionBeen actionBeen) {
        elements.add(
                new KeyEventBeenElement(
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen));
        return this;
    }

    private KeyEventBeen(String methodName) {
        super(new UndefinedActionBeen(), methodName);
    }

    public static KeyEventBeen setOnKeyPressed() {
        return new KeyEventBeen("setOnKeyPressed");
    }

    public static KeyEventBeen setOnKeyReleased() {
        return new KeyEventBeen("setOnKeyReleased");
    }

    public static KeyEventBeen setOnKeyTyped() {
        return new KeyEventBeen("setOnKeyTyped");
    }

    @Override
    public void handle(KeyEvent event) {
        elements.forEach(element -> {
            if (element.keyCode == event.getCode()
                    && element.controlDown == event.isControlDown()
                    && element.altDown == event.isAltDown()
                    && element.shiftDown == event.isShiftDown()) {
                element.actionBeen.action();
            }
        });
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

    static class UndefinedActionBeen implements ActionBeen {

        @Override
        public void action() {

        }
    }
}
