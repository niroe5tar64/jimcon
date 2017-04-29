package jp.niro.jimcon.debug;

import java.util.Set;

/**
 * Created by niro on 2017/04/23.
 */
public class TestMain {

    public static void main(String[] args) {

        Set<Object> systemKeys = System.getProperties().keySet();
        for (Object key : systemKeys) {
            System.out.println("<tr><td>"+ key + "</td><td>" + System.getProperties().getProperty((String)key) + "</td></tr>");
        }
    }

}
