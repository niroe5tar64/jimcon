package jp.niro.jimcon.debug;

import jp.niro.jimcon.data.*;
import jp.niro.jimcon.sql.LoginInfo;

import java.util.Set;

/**
 * Created by niro on 2017/04/23.
 */
public class TestMain {

    public static void main(String[] args) {
        Tags tags = new Tags();
        TagMaps tagMaps = new TagMaps();
        LoginInfo login = LoginInfo.create();

        tags.loadTags(login);
        tagMaps.loadTagMaps(login);
    }


    private void printSystemKey() {
        Set<Object> systemKeys = System.getProperties().keySet();
        for (Object key : systemKeys) {
            System.out.println("<tr><td>" + key + "</td><td>" + System.getProperties().getProperty((String) key) + "</td></tr>");
        }
    }

    
}
