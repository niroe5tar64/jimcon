package jp.niro.jimcon.customcomponents;

import javafx.scene.control.ListCell;
import jp.niro.jimcon.datamodel.Tag;

/**
 * Created by niro on 2017/07/11.
 */
public class ListTagCell extends ListCell<Tag> {
    @Override
    protected void updateItem(Tag tag, boolean empty) {
        super.updateItem(tag, empty);
        if(!empty) {
            setText(tag.getTagName());
        }
    }
}
