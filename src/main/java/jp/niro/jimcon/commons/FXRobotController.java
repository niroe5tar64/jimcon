package jp.niro.jimcon.commons;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by niro on 2017/07/14.
 */
public class FXRobotController {
    private FXRobot robot;
    private Scene scene;
    private KeyEvent event;
    private List<Class<? extends Node>> enableClassList = new ArrayList<>();


    private FXRobotController(Scene scene, KeyEvent event) {
        this.scene = scene;
        this.event = event;
        robot = FXRobotFactory.createRobot(scene);
    }

    public static FXRobotController create(Scene scene, KeyEvent event) {
        return new FXRobotController(scene, event);
    }

    public void setEnableClassList(List<Class<? extends Node>> enableClassList) {
        this.enableClassList = enableClassList;
    }

    public void addEnableClassList(Class<? extends Node> control) {
        enableClassList.add(control);
    }

    public void keyPress(KeyCode eventKey, KeyCode robotKey) {
        if (isMatchCode(eventKey) && isEnable()) robot.keyPress(robotKey);
    }

    public void keyRelease(KeyCode eventKey, KeyCode robotKey) {
        if (isMatchCode(eventKey) && isEnable()) robot.keyRelease(robotKey);
    }

    private boolean isMatchCode(KeyCode keyCode){
        return event.getCode() == keyCode;
    }

    private Boolean isEnable() {
        for (Class<? extends Node> node : enableClassList) {
            if (Objects.equals(
                    node.getName(),
                    scene.getFocusOwner().getClass().getName())) {
                return true;
            }
        }
        return false;
    }
}