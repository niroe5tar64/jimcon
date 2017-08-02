package jp.niro.jimcon.eventmanager;

import jp.niro.jimcon.ui.SingleSearchDialog;

/**
 * Created by niro on 2017/07/31.
 */
public class ActionSearchDetermine implements ActionBean {

    private SingleSearchDialog controller;

    public ActionSearchDetermine(SingleSearchDialog controller) {
        this.controller = controller;
    }

    @Override
    public void action() {
        controller.determine();
    }
}
