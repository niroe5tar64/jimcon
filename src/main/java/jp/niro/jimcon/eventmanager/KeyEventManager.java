package jp.niro.jimcon.eventmanager;

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
public class KeyEventManager extends BaseEventManager implements EventHandler<KeyEvent> {
    private Collection<KeyEventBeen> keyPresses = new ArrayList<>();
    private Collection<KeyEventBeen> keyReleases = new ArrayList<>();
    private Collection<KeyEventBeen> keyTypes = new ArrayList<>();


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

    public KeyEventManager setOnKeyPressed(KeyEventBeen element) {
        keyPresses.add(element);
        return this;
    }

    public KeyEventManager setOnKeyPressed(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyPresses
                .add(new KeyEventBeen(
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
                .add(new KeyEventBeen(
                        "setOnKeyPressed",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyTyped(KeyEventBeen element) {
        keyTypes.add(element);
        return this;
    }

    public KeyEventManager setOnKeyTyped(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyTypes
                .add(new KeyEventBeen(
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
                .add(new KeyEventBeen(
                        "setOnKeyTyped",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(KeyEventBeen element) {
        keyReleases.add(element);
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            ActionBeen actionBeen) {
        keyReleases
                .add(new KeyEventBeen(
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
            boolean withPressed) {
        keyReleases
                .add(new KeyEventBeen(
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
                .add(new KeyEventBeen(
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
            boolean withPressed) {
        keyReleases
                .add(new KeyEventBeen(
                        "setOnKeyReleased",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBeen,
                        withPressed));
        return this;
    }

    @Override
    public void handle(KeyEvent event) {

        EventType<KeyEvent> eventType = event.getEventType();

        /*
        Action Event が使用後状態の時はキーイベント中でアクションを発生させない。
        キーイベントの最後に一度だけ「KEY_RELEASED」が呼ばれるので
        「KEY_RELEASED」の時に Action Event 使用後状態を解除する。
        */
        if (KeyState.isActionEvent()) {
            // Action Event 使用後状態を解除
            if (KEY_RELEASED == eventType) KeyState.offActionEvent();
        } else {
            if (KEY_PRESSED == eventType) {
                action(event, keyPresses);
                KeyState.onPressed();

            }
            if (KEY_TYPED == eventType) {
                action(event, keyTypes);
            }
            if (KEY_RELEASED == eventType) {
                action(event, keyReleases);
                KeyState.offPressed();
            }
        }
    }

    private void action(KeyEvent event, Collection<KeyEventBeen> beens) {
        beens.forEach(been -> {
            if (
                    been.keyCode == event.getCode()
                            && been.controlDown == event.isControlDown()
                            && been.altDown == event.isAltDown()
                            && been.shiftDown == event.isShiftDown()) {

                if (been.withPressed) {
                    if (KeyState.isPressed()) {
                        been.actionBeen.action();
                    }
                } else {
                    been.actionBeen.action();
                }
            }
        });
    }

    @Override
    public BaseEventManager setEvent(Node node) {
        node.setOnKeyPressed(this);
        node.setOnKeyReleased(this);
        node.setOnKeyTyped(this);
        return this;
    }

    @Override
    public void setEvent(Collection<Node> nodes) {
        nodes.forEach(node -> {
            node.setOnKeyPressed(this);
            node.setOnKeyReleased(this);
            node.setOnKeyTyped(this);
        });
    }
}
