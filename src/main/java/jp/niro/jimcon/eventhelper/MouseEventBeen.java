package jp.niro.jimcon.eventhelper;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by niro on 2017/07/24.
 */
public class MouseEventBeen extends EventBeen implements EventHandler<MouseEvent> {



    private MouseEventBeen(ActionBeen actionBeen, String methodName) {
        super(actionBeen, methodName);
    }

    public static MouseEventBeen setOnMouseClicked(ActionBeen actionBeen){
        return new MouseEventBeen(actionBeen, "setOnMouseClicked");
    }

    @Override
    public void handle(MouseEvent event) {
        actionBeen.action(node);
    }
}
