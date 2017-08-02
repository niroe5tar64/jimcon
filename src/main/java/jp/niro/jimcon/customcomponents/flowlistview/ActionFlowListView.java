package jp.niro.jimcon.customcomponents.flowlistview;

import jp.niro.jimcon.eventmanager.ActionBean;

/**
 * Created by niro on 2017/07/31.
 */
public class ActionFlowListView implements ActionBean {
    private FlowListView flowListView;

    public ActionFlowListView(FlowListView flowListView){
        this.flowListView = flowListView;
    }

    @Override
    public void action() {
        flowListView.remove();
    }
}
