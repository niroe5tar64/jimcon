package jp.niro.jimcon.tutorial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;

/**
 * Created by niro on 2017/03/31.
 */
public class DateUtil {
    private static final String DATE_PATTERN = "yyyy.MM.dd";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date){
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static LocalDate parse(String dateString){
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        }catch (DateTimeParseException e){
            return null;
        }
    }

    public static boolean validDate(String dateString){
        return DateUtil.parse(dateString) != null;
    }

    @SuppressWarnings("deprecation")
	public static Date toDate(LocalDate d){
    	
    	return new Date(
	    	d.getYear() - 1900,
	        d.getDayOfMonth() - 1,
	        d.getDayOfMonth());
    }

}