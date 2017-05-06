package jp.niro.jimcon.commons;

/**
 * Created by niro on 2017/04/22.
 */
public class Constant {

    public class Dialogs {
        public class Title {
            public static final String EDIT_UNIT = "単位編集";
            public static final String EDIT_PRODUCT = "商品編集";
        }
    }

    public class ErrorMessages {
        public class Title {
            public static final String DUPLICATED_UNIT_CODE = "Duplicated Error：単位コード";
            public static final String DUPLICATED_PRODUCT_CODE = "Duplicated Error：商品コード";
            public static final String INVALID_FIELDS = "Invalid Fields Error";
            public static final String NO_SELECTION_UNIT_CODE = "No Selection Error：単位コード";
            public static final String NO_SELECTION_PRODUCT_CODE = "No Selection Error：商品コード";
        }

        public class User {
            public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

            public static final String UNIT_CODE_DUPLICATED = "この単位コードは既に使われています。\n";
            public static final String PRODUCT_CODE_DUPLICATED = "この商品コードは既に使われています。\n";

            public static final String UNIT_CODE_HAS_NOT_BEEN_REGISTERED = "この単位コードは登録されていません。\n";

            public static final String UNIT_CODE_IS_EMPTY = "単位コードが空欄です。\n";
            public static final String UNIT_NAME_IS_EMPTY = "単位名が空欄です。\n";
            public static final String PRODUCT_CODE_IS_EMPTY = "商品コードが空欄です。\n";
            public static final String PRODUCT_NAME_IS_EMPTY = "商品名が空欄です。\n";
            public static final String SIZE_COLOR_IS_EMPTY = "サイズ・色が空欄です。\n";
            public static final String MODEL_NUMBER_IS_EMPTY = "品番型式が空欄です。\n";
            public static final String ANOTHER_NAME_IS_EMPTY = "別名が空欄です。\n";
            public static final String CATALOG_PRICE_IS_EMPTY = "定価が空欄です。\n";
            public static final String STANDARD_UNIT_PRICE_IS_EMPTY = "標準価格が空欄です。\n";
            public static final String STOCK_QUANTITY_IS_EMPTY = "在庫量が空欄です。\n";
            public static final String CUTTING_CONSTANT_IS_EMPTY = "切断定数が空欄です。\n";
            public static final String FUNCTION_CONSTANT_IS_EMPTY = "働き定数が空欄です。\n";
            public static final String MEMO_IS_EMPTY = "メモ欄が空欄です。\n";

            public static final String UNIT_CODE_IS_NOT_IN_RANGE = "単位コードが不正な値です。0～255の範囲で入力して下さい。\n";

            public static final String PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS = "商品コードが不正な値です。10桁の値を入力して下さい。\n";

            public static final String UNIT_CODE_IS_NOT_INTEGER = "単位コードが不正な値です。整数を入力して下さい。\n";
            public static final String PRODUCT_CODE_IS_NOT_INTEGER = "商品コードが不正な値です。整数を入力して下さい。\n";
            public static final String CATALOG_PRICE_IS_NOT_DOUBLE = "定価が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String STANDARD_UNIT_PRICE_IS_NOT_DOUBLE = "標準価格が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String STOCK_QUANTITY_IS_NOT_DOUBLE = "在庫量が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String CUTTING_CONSTANT_IS_NOT_DOUBLE = "切断定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";
            public static final String FUNCTION_CONSTANT_IS_NOT_DOUBLE = "働き定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";

            public static final String NO_SELECTION_UNIT_CODE = "単位を選択して下さい。";
            public static final String NO_SELECTION_PRODUCT_CODE = "商品を選択して下さい。";

            public static final String  DO_NOT_DELETE = "単位を削除する場合は管理者に問い合わせて下さい";
        }
    }

    public class InformationMassages {

    }

    public class Resources {
        public class FXMLFile {
            public static final String ROOT_LAYOUT = "RootLayout.fxml";
            public static final String UNIT_EDIT_DIALOG = "UnitEditDialog.fxml";
            public static final String UNIT_OVERVIEW = "UnitOverview.fxml";
            public static final String UNIT_SEARCH_DIALOG = "UnitSearchDialog.fxml";
            public static final String PRODUCT_EDIT_DIALOG = "ProductEditDialog.fxml";
            public static final String PRODUCT_OVERVIEW = "ProductOverview.fxml";
        }

        public class Properties {
            public static final String TEXT_NAME = "TextName";
        }
    }

    public class System {
        public static final int CLICK_COUNT_SINGLE = 1;
        public static final int CLICK_COUNT_DOUBLE = 2;
        public static final int PRODUCT_CODE_DIGITS = 10;
    }

    public class DateFormat {
        public static final String SHORT = "MM/DD";

    }
}
