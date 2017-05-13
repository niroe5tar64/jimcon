package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.LoginInfo;

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

    public synchronized Unit getUnit(LoginInfo login, int unitCode) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(unitCode,
                k -> Unit.create(login, unitCode));
    }
}
