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
    private Collection<KeyEventBean> keyPresses = new ArrayList<>();
    private Collection<KeyEventBean> keyTypes = new ArrayList<>();
    private Collection<KeyEventBean> keyReleases = new ArrayList<>();


    private KeyEventManager() {
        super(new ActionBean() {
            @Override
            public void action() {

            }
        }, "");
    }

    public static KeyEventManager create() {
        return new KeyEventManager();
    }

    public KeyEventManager setOnKeyPressed(KeyEventBean element) {
        keyPresses.add(element);
        return this;
    }

    public KeyEventManager setOnKeyPressed(
            KeyCode keyCode,
            ActionBean actionBean) {
        keyPresses
                .add(new KeyEventBean(
                        "setOnKeyPressed",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyPressed(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBean actionBean) {
        keyPresses
                .add(new KeyEventBean(
                        "setOnKeyPressed",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyTyped(KeyEventBean element) {
        keyTypes.add(element);
        return this;
    }

    public KeyEventManager setOnKeyTyped(
            KeyCode keyCode,
            ActionBean actionBean) {
        keyTypes
                .add(new KeyEventBean(
                        "setOnKeyTyped",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyTyped(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBean actionBean) {
        keyTypes
                .add(new KeyEventBean(
                        "setOnKeyTyped",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(KeyEventBean element) {
        keyReleases.add(element);
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            ActionBean actionBean) {
        keyReleases
                .add(new KeyEventBean(
                        "setOnKeyReleased",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            ActionBean actionBean,
            boolean withPressed) {
        keyReleases
                .add(new KeyEventBean(
                        "setOnKeyReleased",
                        keyCode,
                        false,
                        false,
                        false,
                        actionBean,
                        withPressed));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBean actionBean) {
        keyReleases
                .add(new KeyEventBean(
                        "setOnKeyReleased",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBean,
                        false));
        return this;
    }

    public KeyEventManager setOnKeyReleased(
            KeyCode keyCode,
            boolean controlDown,
            boolean altDown,
            boolean shiftDown,
            ActionBean actionBean,
            boolean withPressed) {
        keyReleases
                .add(new KeyEventBean(
                        "setOnKeyReleased",
                        keyCode,
                        controlDown,
                        altDown,
                        shiftDown,
                        actionBean,
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

    private void action(KeyEvent event, Collection<KeyEventBean> beens) {
        beens.forEach(bean -> {
            if (
                    bean.keyCode == event.getCode()
                            && bean.controlDown == event.isControlDown()
                            && bean.altDown == event.isAltDown()
                            && bean.shiftDown == event.isShiftDown()) {

                if (bean.withPressed) {
                    if (KeyState.isPressed()) {
                        bean.actionBean.action();
                    }
                } else {
                    bean.actionBean.action();
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
