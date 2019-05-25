package jp.niro.jimcon.ui;

import jp.niro.jimcon.datamodel.Supplier;
import jp.niro.jimcon.eventmanager.Searchable;

/**
 * Created by niro on 2017/08/10.
 */
public interface SupplierSearchable extends Searchable {
    void updateDisplay(Supplier supplier);
    String getSearchValue();
}
