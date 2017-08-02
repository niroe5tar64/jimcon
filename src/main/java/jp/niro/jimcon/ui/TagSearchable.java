package jp.niro.jimcon.ui;

import jp.niro.jimcon.datamodel.Tag;
import jp.niro.jimcon.eventmanager.Searchable;

/**
 * Created by niro on 2017/05/15.
 */
public interface TagSearchable extends Searchable {
    void updateDisplay(Tag tag);
    String getSearchValue();
}
