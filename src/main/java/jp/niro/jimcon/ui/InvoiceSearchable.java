package jp.niro.jimcon.ui;

import jp.niro.jimcon.datamodel.Invoice;
import jp.niro.jimcon.eventmanager.Searchable;

/**
 * Created by niro on 2017/08/03.
 */
public interface InvoiceSearchable extends Searchable {
    void updateDisplay(Invoice invoice);
    String getSearchValue();
}
