package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/12.
 */
public class UnitFactory {
    private Map<Integer, Unit> pool = new HashMap<>();

    private static UnitFactory singleton = new UnitFactory();

    private UnitFactory() {
    }

    public static UnitFactory getInstance() {
        return singleton;
    }

    public synchronized Unit getUnit(int unitCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(unitCode,
                k -> {
                    try {
                        return Unit.create(SQL.create(), unitCode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
