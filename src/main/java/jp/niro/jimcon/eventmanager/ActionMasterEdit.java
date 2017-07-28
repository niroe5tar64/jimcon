package jp.niro.jimcon.eventmanager;

import jp.niro.jimcon.ui.MasterEditController;

/**
 * Created by niro on 2017/07/27.
 */
public class ActionMasterEdit implements ActionBeen {
    ActionType type;
    MasterEditController controller;

    public ActionMasterEdit(ActionType type, MasterEditController controller){
        this.type = type;
        this.controller = controller;
    }

    @Override
    public void action() {
        switch (type) {
            case OK:
                controller.handleOK();
                break;

            case CANCEL:
                controller.handleCancel();
                break;

            case CLOSE:
                controller.handleClose();
                break;
        }
    }
}
