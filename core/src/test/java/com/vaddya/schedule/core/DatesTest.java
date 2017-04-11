package com.vaddya.schedule.core;

import com.vaddya.schedule.core.utils.Dates;
import org.junit.Test;

import java.time.LocalDate;

import static com.vaddya.schedule.core.utils.Dates.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.*;

/**
 * Модульного тестирование вспомогательного класса для работы с датами
 *
 * @author vaddya
 */
public class DatesTest {

    @Test
    public void testParseDate() throws Exception {
        LocalDate expected = LocalDate.of(2016, 12, 31);
        LocalDate actual = LocalDate.from(FULL_DATE_FORMAT.parse("31.12.2016"));
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDate() throws Exception {
        LocalDate date = LocalDate.of(2016, 12, 31);
        String expected = "Sat 31.12";
        String actual = BRIEF_DATE_FORMAT.format(date);
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysUntil() throws Exception {
        LocalDate date = LocalDate.now().plus(10, DAYS);
        assertEquals(10, daysUntil(date));
    }

    @Test
    public void testIsPast() throws Exception {
        LocalDate past = LocalDate.now().minus(5, DAYS);
        LocalDate future = LocalDate.now().plus(5, DAYS);
        assertTrue(Dates.isPast(past));
        assertFalse(Dates.isPast(future));
    }

}