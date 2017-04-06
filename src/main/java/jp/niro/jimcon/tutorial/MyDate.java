package jp.niro.jimcon.tutorial;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;

/**
 * Created by niro on 2017/04/06.
 */
public class MyDate {
    private static final String DATE_PATTERN = "yyyy.MM.dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN);

    private DateTime dateTime;

    public MyDate(int year, int month, int day, int hour, int minute, int second) {
        this.dateTime = new DateTime(year, month, day, hour, minute, second);
    }

    public MyDate(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    @SuppressWarnings("deprecation")
    public MyDate(Date date) {
        this(
                date.getYear(),
                date.getMonth(),
                date.getDate());
    }

    public MyDate(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return dateTime.toString(DATE_TIME_FORMATTER);
    }

    public static MyDate parse(String dateString) {
        return new MyDate(DateTime.parse(dateString, DATE_TIME_FORMATTER));
    }

    public static boolean validDate(String dateString) {
        return MyDate.parse(dateString) != null;
    }

    @SuppressWarnings("deprecation")
    public Date toDate() {
        return new Date(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth());
    }
}
