package jp.niro.jimcon.eventhelper;

import com.sun.javafx.robot.FXRobot;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/22.
 */
public class RobotKeyPress implements ActionBeen {

    private FXRobot robot;
    private KeyCode keyCode;

    public RobotKeyPress(FXRobot robot, KeyCode keyCode) {
        this.robot = robot;
        this.keyCode = keyCode;
    }

    @Override
    public void action(Node node) {
        if (robot == null) {
            System.out.println("robot is null");
        } else {
            System.out.println("a");
            robot.keyPress(keyCode);
        }
    }
}
