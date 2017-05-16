package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.LoginInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/14.
 */
public class TagMapFactory {
    private Map<Long, TagMap> pool = new HashMap<>();

    private static TagMapFactory singleton = new TagMapFactory();

    private TagMapFactory() {
    }

    public static TagMapFactory getInstance() {
        return singleton;
    }

    public synchronized TagMap getTagMap(LoginInfo login, long tagMapId) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(tagMapId,
                k -> TagMap.create(login, tagMapId));
    }
}
