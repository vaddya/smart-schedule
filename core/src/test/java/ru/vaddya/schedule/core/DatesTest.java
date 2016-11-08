package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadim on 10/25/2016.
 */
public class DatesTest {

//    @Test
//    public void testParseShortDate() throws Exception {
//        // Нумерация месяцев в GregorianCalendar начинается с нуля
//        LocalDate date = LocalDate.of(2016, 12, 31);
//        Date actual = Dates.parseShort("31.12.2016");
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFormatBriefDate() throws Exception {
//        Date date = new GregorianCalendar(2016, 11, 31).getTime();
//        String expected = "Сб 31.12";
//        String actual = Dates.formatBrief(date);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFormatShortDate() throws Exception {
//        Date date = new GregorianCalendar(2016, 11, 31).getTime();
//        String expected = "31.12.2016";
//        String actual = Dates.formatShort(date);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFormatExtendDate() throws Exception {
//        Date date = new GregorianCalendar(2016, 11, 31).getTime();
//        String expected = "Сб 31.12.2016";
//        String actual = Dates.formatExtend(date);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testDayOfWeek() throws Exception {
//        Date date = new GregorianCalendar(2016, 11, 31).getTime();
//        String expected = "суббота";
//        String actual = Dates.dayOfWeek(date);
//        assertEquals(expected, actual);
//    }

    @Test
    public void testDaysUntil() throws Exception {
        // Today is always today
        LocalDate date = LocalDate.now();
        assertEquals(0, Dates.daysUntil(date));
    }
}
