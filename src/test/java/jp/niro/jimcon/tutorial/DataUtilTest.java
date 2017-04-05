package jp.niro.jimcon.tutorial;


import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by niro on 2017/04/05.
 */
public class DataUtilTest {
    final int year = 2017;
    final int month = 4;
    final int day = 10;

    Date sqlDate;
    LocalDate localDate;
    DateTime dateTime;

    @Before
    public void setUp() throws Exception {
        sqlDate = DateUtil.toDate(year, month, day);
        localDate = DateUtil.toLocalDate(year, month, day);
        dateTime = DateUtil.toDateTime(year, month, day);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDate() {
        try {
            // Check Date constructor
            assertThat(sqlDate.getYear()).isEqualTo(year);
            assertThat(sqlDate.getMonth()).isEqualTo(month);
            assertThat(sqlDate.getDate()).isEqualTo(day);

            // Check LocalDate to Date
            Date ld2d = DateUtil.toDate(localDate);
            assertThat(ld2d.getYear()).isEqualTo(year);
            assertThat(ld2d.getMonth()).isEqualTo(month);
            assertThat(ld2d.getDate()).isEqualTo(day);

            // Check DateTime to Date
            Date dt2d = DateUtil.toDate(dateTime);
            assertThat(dt2d.getYear()).isEqualTo(year);
            assertThat(dt2d.getMonth()).isEqualTo(month);
            assertThat(dt2d.getDate()).isEqualTo(day);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testLocalDate() {
        try {
            // Check LocalDate constructor
            assertThat(localDate.getYear()).isEqualTo(year);
            assertThat(localDate.getMonthValue()).isEqualTo(month);
            assertThat(localDate.getDayOfMonth()).isEqualTo(day);

            // Check Date to LocalDate
            LocalDate d2ld = DateUtil.toLocalDate(sqlDate);
            assertThat(d2ld.getYear()).isEqualTo(year);
            assertThat(d2ld.getMonthValue()).isEqualTo(month);
            assertThat(d2ld.getDayOfMonth()).isEqualTo(day);

            // Check DateTime to LocalDate
            LocalDate dt2ld = DateUtil.toLocalDate(dateTime);
            assertThat(dt2ld.getYear()).isEqualTo(year);
            assertThat(dt2ld.getMonthValue()).isEqualTo(month);
            assertThat(dt2ld.getDayOfMonth()).isEqualTo(day);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDateTime() {
        try {
            // Check DateTime constructor
            assertThat(dateTime.getYear()).isEqualTo(year);
            assertThat(dateTime.getMonthOfYear()).isEqualTo(month);
            assertThat(dateTime.getDayOfMonth()).isEqualTo(day);

            // Check Date to DateTime
            DateTime d2dt = DateUtil.toDateTime(sqlDate);
            assertThat(d2dt.getYear()).isEqualTo(year);
            assertThat(d2dt.getMonthOfYear()).isEqualTo(month);
            assertThat(d2dt.getDayOfMonth()).isEqualTo(day);

            // Check LocalDate to DateTime
            DateTime ld2dt = DateUtil.toDateTime(localDate);
            assertThat(ld2dt.getYear()).isEqualTo(year);
            assertThat(ld2dt.getMonthOfYear()).isEqualTo(month);
            assertThat(ld2dt.getDayOfMonth()).isEqualTo(day);

        } catch (Exception e) {
            fail();
        }
    }
}
