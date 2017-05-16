package jp.niro.jimcon.commons;

/**
 * Created by niro on 2017/04/22.
 */
public class Constant {

    public class Dialogs {
        public class Title {
            public static final String DEPARTMENT_OVERVIEW = "部署一覧";
            public static final String PRODUCT_OVERVIEW = "商品一覧";
            public static final String UNIT_OVERVIEW = "単位一覧";
            public static final String TAG_OVERVIEW = "タグ一覧";
            public static final String DEPARTMENT_EDIT = "部署編集";
            public static final String PRODUCT_EDIT = "商品編集";
            public static final String UNIT_EDIT = "単位編集";
            public static final String TAG_EDIT = "タグ編集";
        }
    }

    public class ErrorMessages {
        public class Title {
            public static final String DUPLICATED_DEPARTMENT_CODE = "Duplicated Error：部署コード";
            public static final String DUPLICATED_PRODUCT_CODE = "Duplicated Error：商品コード";
            public static final String DUPLICATED_UNIT_CODE = "Duplicated Error：単位コード";
            public static final String DUPLICATED_TAG_ID = "Duplicated Error：タグＩＤ";

            public static final String INVALID_FIELDS = "Invalid Fields Error";
            public static final String DO_NOT_DELETE = "Don't delete";

            public static final String NO_SELECTION_DEPARTMENT_CODE = "No Selection Error：部署コード";
            public static final String NO_SELECTION_PRODUCT_CODE = "No Selection Error：商品コード";
            public static final String NO_SELECTION_UNIT_CODE = "No Selection Error：単位コード";
            public static final String NO_SELECTION_TAG_ID = "No Selection Error：タグＩＤ";
        }

        public class User {
            public static final String PLEASE_INPUT_CORRECT_VALUE = "適切な値を入力して下さい。";

        }

        public class Department {

            public static final String DEPARTMENT_CODE_DUPLICATED = "この部署コードは既に使われています。\n";
            public static final String DEPARTMENT_CODE_HAS_NOT_BEEN_REGISTERED = "この部署コードは登録されていません。\n";

            public static final String DEPARTMENT_CODE_IS_EMPTY = "部署コードが空欄です。\n";
            public static final String DEPARTMENT_NAME_IS_EMPTY = "部署名が空欄です。\n";
            public static final String POSTCODE_IS_EMPTY = "郵便番号が空欄です。\n";
            public static final String ADDRESS_IS_EMPTY = "住所が空欄です。\n";
            public static final String TEL_NUMBER_IS_EMPTY = "電話番号が空欄です。\n";
            public static final String FAX_NUMBER_IS_EMPTY = "FAX番号が空欄です。\n";

            public static final String DEPARTMENT_CODE_IS_NOT_IN_RANGE = "部署コードが不正な値です。0～255の範囲で入力して下さい。\n";
            public static final String DEPARTMENT_CODE_IS_NOT_INTEGER = "部署コードが不正な値です。整数を入力して下さい。\n";

            public static final String NO_SELECTION = "部署を選択して下さい。";
            public static final String DO_NOT_DELETE = "部署を削除する場合は管理者に問い合わせて下さい";
        }

        public class Product {
            public static final String PRODUCT_CODE_DUPLICATED = "この商品コードは既に使われています。\n";
            public static final String PRODUCT_CODE_HAS_NOT_BEEN_REGISTERED = "この商品コードは登録されていません。\n";

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

            public static final String PRODUCT_CODE_IS_INVALID_NUMBER_OF_DIGITS = "商品コードが不正な値です。10桁の値を入力して下さい。\n";
            public static final String PRODUCT_CODE_IS_NOT_INTEGER = "商品コードが不正な値です。整数を入力して下さい。\n";
            public static final String CATALOG_PRICE_IS_NOT_DOUBLE = "定価が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String STANDARD_UNIT_PRICE_IS_NOT_DOUBLE = "標準価格が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String STOCK_QUANTITY_IS_NOT_DOUBLE = "在庫量が不正な値です。小数点以下2桁までの少数を入力して下さい。\n";
            public static final String CUTTING_CONSTANT_IS_NOT_DOUBLE = "切断定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";
            public static final String FUNCTION_CONSTANT_IS_NOT_DOUBLE = "働き定数が不正な値です。小数点以下3桁までの少数を入力して下さい。\n";

            public static final String NO_SELECTION = "商品を選択して下さい。";
        }

        public class Unit {
            public static final String UNIT_CODE_DUPLICATED = "この単位コードは既に使われています。\n";
            public static final String UNIT_CODE_HAS_NOT_BEEN_REGISTERED = "この単位コードは登録されていません。\n";

            public static final String UNIT_CODE_IS_EMPTY = "単位コードが空欄です。\n";
            public static final String UNIT_NAME_IS_EMPTY = "単位名が空欄です。\n";

            public static final String UNIT_CODE_IS_NOT_IN_RANGE = "単位コードが不正な値です。0～255の範囲で入力して下さい。\n";
            public static final String UNIT_CODE_IS_NOT_INTEGER = "単位コードが不正な値です。整数を入力して下さい。\n";
            public static final String UNIT_NAME_IS_TOO_LONG = "単位名が長過ぎます。7文字以内で入力して下さい。\n";

            public static final String NO_SELECTION = "単位を選択して下さい。";
            public static final String DO_NOT_DELETE = "単位を削除する場合は管理者に問い合わせて下さい";
        }

        public class Tag {
            public static final String TAG_ID_DUPLICATED = "このタグＩＤは既に使われています。\n";
            public static final String TAG_ID_HAS_NOT_BEEN_REGISTERED = "このタグＩＤは登録されていません。\n";

            public static final String  TAG_ID_IS_EMPTY = "タグＩＤが空欄です。\n";
            public static final String TAG_NAME_IS_EMPTY = "タグ名が空欄です。\n";

            public static final String NO_SELECTION = "タグを選択して下さい。";
            public static final String DO_NOT_DELETE = "タグを削除する場合は管理者に問い合わせて下さい";
        }
    }

    public class InformationMassages {

    }

    public class Resources {
        public class FXMLFile {
            public static final String ROOT_LAYOUT = "RootLayout.fxml";
            public static final String MENU = "Menu.fxml";
            public static final String DEPARTMENT_OVERVIEW = "DepartmentOverview.fxml";
            public static final String DEPARTMENT_EDIT_DIALOG = "DepartmentEditDialog.fxml";
            public static final String PRODUCT_EDIT_DIALOG = "ProductEditDialog.fxml";
            public static final String PRODUCT_OVERVIEW = "ProductOverview.fxml";
            public static final String UNIT_EDIT_DIALOG = "UnitEditDialog.fxml";
            public static final String UNIT_OVERVIEW = "UnitOverview.fxml";
            public static final String UNIT_SEARCH_DIALOG = "UnitSearchDialog.fxml";
            public static final String TAG_EDIT_DIALOG = "TagEditDialog.fxml";
            public static final String TAG_OVERVIEW = "TagOverview.fxml";

            public static final String PRODUCT_EDIT_DIALOG_WITH_TAG = "ProductEditDialogWithTag.fxml";
            public static final String PRODUCT_OVERVIEW_WITH_TAG = "ProductOverviewWithTag.fxml";
        }

        public class Properties {
            public static final String TEXT_NAME = "TextName";
        }
    }

    public class System {
        public static final int CLICK_COUNT_SINGLE = 1;
        public static final int CLICK_COUNT_DOUBLE = 2;
        public static final int PRODUCT_CODE_DIGITS = 10;
        public static final int UNIT_NAME_LENGTH = 7;
    }

    public class JP_Name {
        public static final String PRODUCT = "商品";
        public static final String PRODUCT_CODE = "商品コード";
        public static final String PRODUCT_NAME = "商品名";
        public static final String SIZE_COLOR = "サイズ・色";
        public static final String MODEL_NUMBER = "品番型式";
        public static final String ANOTHER_NAME = "別名";
        public static final String CATALOG_PRICE = "定価";
        public static final String STANDARD_UNIT_PRICE = "標準価格";
        public static final String STOCK_QUANTITY = "在庫量";
        public static final String CUTTING_CONSTANT = "切断定数";
        public static final String FUNCTION = "働き定数";
        public static final String MEMO = "メモ欄";
        public static final String IS_PROCESSED = "";
        public static final String IS_DELETED = "";
        public static final String LAST_UPDATE = "";

        public static final String UNIT = "単位";
        public static final String UNIT_CODE = "単位コード";
        public static final String UNIT_NAME = "単位名";
    }

    public class DateFormat {
        public static final String SHORT = "MM/DD";

    }
}
