package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadim on 10/25/2016.
 */
public class DatesTest {

    @Test
    public void testParseShortDate() throws Exception {
        LocalDate expected = LocalDate.of(2016, 12, 31);
        LocalDate actual = Dates.parseShort("31.12.2016");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatBriefDate() throws Exception {
        LocalDate date = LocalDate.of(2016, 12, 31);
        String expected = "Sat 31.12";
        String actual = Dates.formatBrief(date);
        assertEquals(expected, actual);
    }

    @Test
    public void testDaysUntil() throws Exception {
        LocalDate date = LocalDate.now().plus(10, ChronoUnit.DAYS);
        assertEquals(10, Dates.daysUntil(date));
    }
}
