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

        tags.getTags().forEach(tag -> System.out.println(tag.getTagName()));
        tagMaps.getTagMaps().forEach(tagMap -> {
                    System.out.print(tagMap.getTagMapId() + " : ");
                    System.out.print(TagFactory.getInstance().getTag(login, tagMap.getTagId()).getTagId() + " : ");
                    System.out.print(TagFactory.getInstance().getTag(login, tagMap.getTagId()).getTagName());
                    System.out.println();
                }
        );

        tagMaps.getTagMaps().forEach(tagMap -> {
                    System.out.print(tagMap.getProductCode() + " : ");
                    System.out.print(ProductFactory.getInstance().getProduct(login, tagMap.getProductCode()).getProductName() + " : ");
                    System.out.print(tagMap.getTagMapId() + " : ");
                    System.out.print(TagFactory.getInstance().getTag(login, tagMap.getTagId()).getTagId() + " : ");
                    System.out.print(TagFactory.getInstance().getTag(login, tagMap.getTagId()).getTagName());
                    System.out.println();
                }
        );
    }


    private void printSystemKey() {
        Set<Object> systemKeys = System.getProperties().keySet();
        for (Object key : systemKeys) {
            System.out.println("<tr><td>" + key + "</td><td>" + System.getProperties().getProperty((String) key) + "</td></tr>");
        }
    }
}
