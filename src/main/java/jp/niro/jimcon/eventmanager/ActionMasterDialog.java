package jp.niro.jimcon.eventmanager;

import jp.niro.jimcon.ui.MasterController;

/**
 * Created by niro on 2017/07/27.
 */
public class ActionMasterDialog implements ActionBeen {
    ActionType type;
    MasterController controller;

    public ActionMasterDialog(ActionType type, MasterController controller) {
        this.type = type;
        this.controller = controller;
    }

    @Override
    public void action() {
        switch (type) {
            case NEW:
                controller.handleNew();
                break;

            case EDIT:
                controller.handleEdit();
                break;

            case DELETE:
                controller.handleDelete();
                break;

            case CLOSE:
                controller.handleClose();
                break;
        }
    }
}
