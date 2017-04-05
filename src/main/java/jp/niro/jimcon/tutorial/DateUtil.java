package jp.niro.jimcon.tutorial;

import org.joda.time.DateTime;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by niro on 2017/03/31.
 */
public class DateUtil {
    private static final String DATE_PATTERN = "yyyy.MM.dd";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }


    @SuppressWarnings("deprecation")
    public static Date toDate(int year, int month, int day) {
        return new Date(year, month, day);
    }

    public static Date toDate(LocalDate d) {
        return toDate(
                d.getYear(),
                d.getMonthValue(),
                d.getDayOfMonth());
    }

    public static Date toDate(DateTime d) {
        return toDate(
                d.getYear(),
                d.getMonthOfYear(),
                d.getDayOfMonth());
    }

    public static LocalDate toLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    @SuppressWarnings("deprecation")
    public static LocalDate toLocalDate(Date d) {
        return toLocalDate(
                d.getYear(),
                d.getMonth(),
                d.getDate());
    }

    public static LocalDate toLocalDate(DateTime d) {
        return toLocalDate(
                d.getYear(),
                d.getMonthOfYear(),
                d.getDayOfMonth());
    }

    public static DateTime toDateTime(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0, 0);
    }

    @SuppressWarnings("deprecation")
    public static DateTime toDateTime(Date d) {
        return toDateTime(
                d.getYear(),
                d.getMonth(),
                d.getDate());
    }

    public static DateTime toDateTime(LocalDate d) {
        return toDateTime(
                d.getYear(),
                d.getMonthValue(),
                d.getDayOfMonth());
    }


}
