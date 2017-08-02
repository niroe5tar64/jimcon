package jp.niro.jimcon.ui;

import jp.niro.jimcon.datamodel.Unit;
import jp.niro.jimcon.eventmanager.Searchable;

/**
 * Created by niro on 2017/05/06.
 */
interface UnitSearchable extends Searchable {
    void updateDisplay(Unit unit);
}
