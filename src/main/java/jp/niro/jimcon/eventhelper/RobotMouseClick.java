package jp.niro.jimcon.eventhelper;

import com.sun.javafx.robot.FXRobot;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

/**
 * Created by niro on 2017/07/24.
 */
public class RobotMouseClick implements ActionBeen {

    private FXRobot robot;
    private MouseButton mouseButton;
    private int clickCount;

    public RobotMouseClick(FXRobot robot) {
        this.robot = robot;
        this.mouseButton = MouseButton.PRIMARY;
        this.clickCount = 1;
    }

    public RobotMouseClick(FXRobot robot, MouseButton mouseButton) {
        this.robot = robot;
        this.mouseButton = mouseButton;
        this.clickCount = 1;
    }

    public RobotMouseClick(FXRobot robot, int clickCount) {
        this.robot = robot;
        this.mouseButton = MouseButton.PRIMARY;
        this.clickCount = clickCount;
    }

    public RobotMouseClick(FXRobot robot, MouseButton mouseButton, int clickCount) {
        this.robot = robot;
        this.mouseButton = mouseButton;
        this.clickCount = clickCount;
    }

    @Override
    public void action(Node node) {
        if (robot == null) {
            System.out.println("robot is null");
        } else {
            robot.mouseClick(mouseButton,clickCount);
        }
    }
}
