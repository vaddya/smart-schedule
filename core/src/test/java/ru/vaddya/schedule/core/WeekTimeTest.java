package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Модульное тестирование представления недели
 *
 * @author vaddya
 * @since November 25, 2016
 */
public class WeekTimeTest {

    @Test
    public void testCreateWeekTime() throws Exception {
        WeekTime week = WeekTime.of(Dates.parseShort("21.11.2016"));
        int i = 21; // monday day of month
        for (DayOfWeek day : DayOfWeek.values()) {
            assertEquals(i++, week.getDateOf(day).getDayOfMonth());
        }
    }

    @Test
    public void testHashCode() throws Exception {
        WeekTime week1 = WeekTime.of(Dates.parseShort("21.11.2016"));
        WeekTime week2 = WeekTime.of(Dates.parseShort("25.11.2016"));
        WeekTime week3 = WeekTime.of(Dates.parseShort("28.11.2016"));

        assertEquals(week1, week2);
        assertEquals(week1.hashCode(), week2.hashCode());

        assertNotEquals(week1, week3);
        assertNotEquals(week1.hashCode(), week3.hashCode());
    }
}
