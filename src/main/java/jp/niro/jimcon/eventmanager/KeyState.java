package jp.niro.jimcon.eventmanager;

/**
 * Created by niro on 2017/07/28.
 */
class KeyState {

    private static boolean nowPressed = false;
    private static boolean nowActionEvent = false;

    private KeyState(){}

    private static KeyState singleton = new KeyState();

    KeyState getInstance() {
        return singleton;
    }

    static void onPressed() {
        nowPressed = true;
    }

    static void offPressed() {
        nowPressed = false;
    }

    static boolean isPressed() {
        return nowPressed;
    }

    static void onActionEvent() {
        nowActionEvent = true;
    }

    static void offActionEvent() {
        nowActionEvent = false;
    }

    static boolean isActionEvent() {
        return nowActionEvent;
    }

}
