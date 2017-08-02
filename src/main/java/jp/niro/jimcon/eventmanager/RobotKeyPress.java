package jp.niro.jimcon.eventmanager;

import com.sun.javafx.robot.FXRobot;
import javafx.scene.input.KeyCode;

/**
 * Created by niro on 2017/07/22.
 */
public class RobotKeyPress implements ActionBean {

    private FXRobot robot;
    private KeyCode keyCode;

    public RobotKeyPress(FXRobot robot, KeyCode keyCode) {
        this.robot = robot;
        this.keyCode = keyCode;
    }

    @Override
    public void action() {
        if (robot == null) {
            System.out.println("robot is null");
        } else {
            robot.keyPress(keyCode);
        }
    }
}
