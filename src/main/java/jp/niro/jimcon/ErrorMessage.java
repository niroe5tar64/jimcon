package jp.niro.jimcon;

import jp.niro.jimcon.ui.ResourceBundleWithUtf8;

import java.util.ResourceBundle;

/**
 * Created by niro on 2017/04/22.
 */
public class ErrorMessage {

    public static final String unitCodeIsBlank1 = "単位コードが空欄です。\n";
    public static final String unitCodeIsBlank2 = alertBlank(NameConstant.UNIT_CODE);
    public static String unitCodeIsBlank3;
    public static String unitCodeIsBlank4;

    public static String alertBlank(String subject){
        return subject + "が空欄です。\n";
    }

    public static void main(String[] args) {
        // どのパターンが良いでしょうか？
        // パターン１：普通に定数
        System.out.println(unitCodeIsBlank1);

        // パターン２：共通部分に関数を使った定数
        System.out.println(unitCodeIsBlank2);

        // パターン３：共通部分はstatic関数、引数に定数
        unitCodeIsBlank3 = alertBlank(NameConstant.UNIT_CODE);
        System.out.println(unitCodeIsBlank3);
        System.out.println(alertBlank(NameConstant.UNIT_CODE));

        // パターン４：プロパティファイルから読み込み
        ResourceBundle bundle = ResourceBundleWithUtf8.create("Message");
        unitCodeIsBlank4 = bundle.getString("unitCodeIsBlank");
        System.out.println(unitCodeIsBlank4);
    }

}
