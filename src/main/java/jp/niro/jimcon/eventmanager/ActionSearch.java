package jp.niro.jimcon.eventmanager;

/**
 * Created by niro on 2017/07/31.
 */
public class ActionSearch implements ActionBean {
    private SearchType type;
    private Searchable controller;

    public ActionSearch(SearchType type, Searchable controller) {
        this.type = type;
        this.controller = controller;
    }

    @Override
    public void action() {
        controller.showSearchDialog(type);
    }
}
