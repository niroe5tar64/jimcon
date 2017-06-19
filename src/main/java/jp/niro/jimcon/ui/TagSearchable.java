package jp.niro.jimcon.ui;

import jp.niro.jimcon.data.Tag;

/**
 * Created by niro on 2017/05/15.
 */
public interface TagSearchable {
    void updateDisplay(Tag tag);
    String getSearchValue();
}
