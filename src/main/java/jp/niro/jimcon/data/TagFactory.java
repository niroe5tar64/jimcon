package jp.niro.jimcon.data;

import jp.niro.jimcon.sql.LoginInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niro on 2017/05/13.
 */
public class TagFactory {
    private Map<Long, Tag> pool = new HashMap<>();

    private static TagFactory singleton = new TagFactory();

    private TagFactory() {
    }

    public static TagFactory getInstance() {
        return singleton;
    }

    public synchronized Tag getTag(LoginInfo login, long tagId) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(tagId,
                k -> Tag.create(login, tagId));
    }
}
