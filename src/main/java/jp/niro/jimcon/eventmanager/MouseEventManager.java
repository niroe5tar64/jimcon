package jp.niro.jimcon.eventmanager;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collection;

import static javafx.scene.input.MouseEvent.*;

/**
 * Created by niro on 2017/07/24.
 */
public class MouseEventManager extends BaseEventManager implements EventHandler<MouseEvent> {
    private Collection<MouseEventBean> mousePresses = new ArrayList<>();
    private Collection<MouseEventBean> mouseClicks = new ArrayList<>();
    private Collection<MouseEventBean> mouseReleases = new ArrayList<>();

    private MouseEventManager() {
        super(new ActionBean() {
            @Override
            public void action() {

            }
        }, "");
    }

    public static MouseEventManager create() {
        return new MouseEventManager();
    }

    public MouseEventManager setOnMousePressed(MouseEventBean element) {
        mousePresses.add(element);
        return this;
    }

    public MouseEventManager setOnMousePressed(MouseButton mouseButton,
                                               int clickCount,
                                               ActionBean actionBean) {
        mousePresses
                .add(new MouseEventBean(
                        "setOnMousePressed",
                        mouseButton,
                        clickCount,
                        actionBean));
        return this;
    }

    public MouseEventManager setOnMouseClicked(MouseEventBean element) {
        mouseReleases.add(element);
        return this;
    }

    public MouseEventManager setOnMouseClicked(MouseButton mouseButton,
                                               int clickCount,
                                               ActionBean actionBean) {
        mouseReleases
                .add(new MouseEventBean(
                        "setOnMouseClicked",
                        mouseButton,
                        clickCount,
                        actionBean));
        return this;
    }

    public MouseEventManager setOnMouseReleased(MouseEventBean element) {
        mouseReleases.add(element);
        return this;
    }

    public MouseEventManager setOnMouseReleased(MouseButton mouseButton,
                                                int clickCount,
                                                ActionBean actionBean) {
        mouseReleases
                .add(new MouseEventBean(
                        "setOnMouseReleased",
                        mouseButton,
                        clickCount,
                        actionBean));
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        EventType<? extends MouseEvent> eventType = event.getEventType();

        if (MOUSE_PRESSED == eventType) {
            action(event, mousePresses);
        }
        if (MOUSE_CLICKED == eventType) {
            action(event, mouseClicks);
        }
        if (MOUSE_RELEASED == eventType) {
            action(event, mouseReleases);
        }

        KeyState.offPressed();
    }

    private void action(MouseEvent event, Collection<MouseEventBean> beans) {
        beans.forEach(bean -> {
            if (bean.mouseButton == event.getButton()
                    && bean.clickCount == event.getClickCount()) {
                bean.actionBean.action();
            }
        });
    }

    @Override
    public BaseEventManager setEvent(Node node) {
        node.setOnMousePressed(this);
        node.setOnMouseClicked(this);
        node.setOnMouseReleased(this);
        return this;
    }

    @Override
    public void setEvent(Collection<Node> nodes) {
        nodes.forEach(node -> {
            node.setOnMousePressed(this);
            node.setOnMouseClicked(this);
            node.setOnMouseReleased(this);
        });
    }
}
