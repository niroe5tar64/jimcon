package jp.niro.jimcon.commons;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by niro on 2017/07/08.
 */
public class Validator {
    public static boolean isEmpty(String s) {
        return StringUtils.isEmpty(s);
    }

    public static boolean isEmpty(Collection<?> collection) {

        if (collection == null) return true;

        if (collection.size() < 1) return true;

        return false;
    }

    public static boolean isNotEmpty(String s) {
        return StringUtils.isNotEmpty(s);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isNotInRange(int value, int min, int max) {
        return !isInRange(value, min, max);
    }

    public static boolean isEqual(int actual, int expect) {
        return actual == expect;
    }

    public static boolean isNotEqual(int actual, int expect) {
        return !isEqual(actual, expect);
    }

    public static boolean isEqual(String actual, String expect) {
        return Objects.equals(actual, expect);
    }

    public static boolean isNotEqual(String actual, String expect) {
        return !isEqual(actual, expect);
    }

    public static boolean isEqual(Object actual, Object expect) {
        return actual == expect;
    }

    public static boolean isNotEqual(Object actual, Object expect) {
        return !isEqual(actual, expect);
    }


    public static boolean isGreaterThan(int a, int b) {
        return a > b;
    }

    public static boolean isLessThan(int a, int b) {
        return a < b;
    }

    public static boolean isGreaterThanOrEqualTo(int a, int b) {
        return a >= b;
    }

    public static boolean isLessThanOrEqualTo(int a, int b) {
        return a <= b;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }
}