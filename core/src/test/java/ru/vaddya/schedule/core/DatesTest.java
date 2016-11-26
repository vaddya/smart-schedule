package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertEquals;
import static ru.vaddya.schedule.core.utils.Dates.BRIEF_DATE_FORMAT;
import static ru.vaddya.schedule.core.utils.Dates.SHORT_DATE_FORMAT;

/**
 * Модульного тестирование вспомогательного класса для работы с датами
 *
 * @author vaddya
 */
public class DatesTest {

    @Test
    public void testParseDate() throws Exception {
        LocalDate expected = LocalDate.of(2016, 12, 31);
        LocalDate actual = LocalDate.from(SHORT_DATE_FORMAT.parse("31.12.2016"));
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
        assertEquals(10, Dates.daysUntil(date));
    }
}
