package jp.niro.jimcon.ui;

/**
 * Created by niro on 2017/07/27.
 */
public interface MasterEditController {
    void setEditableForPKField(boolean editable);
    void handleOK();
    void handleCancel();
    void handleClose();
}
