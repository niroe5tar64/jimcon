package jp.niro.jimcon.datamodel;

import jp.niro.jimcon.dbaccess.SQL;

import java.sql.SQLException;
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

    public synchronized Tag getTag(long tagId) {
        // poolにインスタンスがなければインスタンス生成してpoolに追加
        return pool.computeIfAbsent(tagId,
                k -> {
                    try {
                        return Tag.create(SQL.create(), tagId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
