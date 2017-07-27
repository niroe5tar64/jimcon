package jp.niro.jimcon.eventhelper;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Collection;

import static javafx.scene.input.KeyEvent.*;

/**
 * Created by niro on 2017/07/27.
 */
public class KeyEventManager extends EventBeen implements EventHandler<KeyEvent> {
    private Collection<KeyEventBeenElement> keyPresses = new ArrayList<>();
    private Collection<KeyEventBeenElement> keyReleases = new ArrayList<>();
    private Collection<KeyEventBeenElement> keyTypes = new ArrayList<>();


    private KeyEventManager() {
        super(new ActionBeen() {
            @Override
            public void action() {

            }
        }, "");
    }

    public static KeyEventManager create() {
        return new KeyEventManager();
    }

    public KeyEventManager setOnKeyPressed(KeyEventBeenElement element) {
        keyPresses.add(element);
        return this;
    }

    public KeyEventManager setOnKeyPressed(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyPresses
                .add(new KeyEventBeenElement(
                        "setOnKeyPressed",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyPressed(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBeen actionBeen) {
        keyPresses
                .add(new KeyEventBeenElement(
                        "setOnKeyPressed",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(KeyEventBeenElement element) {
        keyReleases.add(element);
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyReleases
                .add(new KeyEventBeenElement(
                        "setOnKeyReleased",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            ActionBeen actionBeen,
            Boolean withPressed) {
        keyReleases
                .add(new KeyEventBeenElement(
                        "setOnKeyReleased",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBeen,
                        withPressed));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBeen actionBeen) {
        keyReleases
                .add(new KeyEventBeenElement(
                        "setOnKeyReleased",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBeen actionBeen,
            Boolean withPressed) {
        keyReleases
                .add(new KeyEventBeenElement(
                        "setOnKeyReleased",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        withPressed));
        return this;
    }

    public KeyEventManager setOnKeyTyped(KeyEventBeenElement element) {
        keyTypes.add(element);
        return this;
    }

    public KeyEventManager setOnKeyTyped(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyTypes
                .add(new KeyEventBeenElement(
                        "setOnKeyTyped",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyTyped(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBeen actionBeen) {
        keyTypes
                .add(new KeyEventBeenElement(
                        "setOnKeyTyped",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        false));
        return this;
    }

    public void clear(){
        keyPresses.clear();
        keyReleases.clear();
        keyTypes.clear();
    }


    @Override
    public void handle(KeyEvent event) {

        EventType<KeyEvent> eventType = event.getEventType();

        if (KEY_PRESSED == eventType) {
            action(event, keyPresses);
            nowPressed = true;
            System.out.println(nowPressed);
        }
        if (KEY_RELEASED == eventType) {
            action(event, keyReleases);
            nowPressed = false;
            System.out.println(nowPressed);
        }
        if (KEY_TYPED == eventType) {
            action(event, keyTypes);
        }
    }

    private void action(KeyEvent event, Collection<KeyEventBeenElement> elements) {
        elements.forEach(element -> {
            if (
                    element.keyCode == event.getCode()
                    && element.controlDown == event.isControlDown()
                    && element.altDown == event.isAltDown()
                    && element.shiftDown == event.isShiftDown()) {

                if (element.withPressed) {
                    if (nowPressed) {
                        element.actionBeen.action();
                    }
                } else {
                    element.actionBeen.action();
                }
            }
        });
    }

    @Override
    public EventBeen setEvent(Node node) {
        node.setOnKeyPressed(this);
        node.setOnKeyReleased(this);
        node.setOnKeyTyped(this);
        return this;
    }

    @Override
    public void setEvent(Collection<Node> nodes){
        nodes.forEach(node -> {
            node.setOnKeyPressed(this);
            node.setOnKeyReleased(this);
            node.setOnKeyTyped(this);
        });
    }
}
