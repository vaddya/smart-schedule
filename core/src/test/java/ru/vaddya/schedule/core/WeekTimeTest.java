package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Модульное тестирование представления недели
 *
 * @author vaddya
 */
public class WeekTimeTest {

    @Test
    public void testCreateWeekTime() throws Exception {
        WeekTime week = WeekTime.of(LocalDate.of(2016, 11, 21));
        int i = 21; // day of month of MONDAY
        for (DayOfWeek day : DayOfWeek.values()) {
            assertEquals(i++, week.getDateOf(day).getDayOfMonth());
        }
    }

    @Test
    public void testEqualsAndHashCode() throws Exception {
        WeekTime week1 = WeekTime.of(LocalDate.of(2016, 11, 21));
        WeekTime week2 = WeekTime.of(LocalDate.of(2016, 11, 25));
        WeekTime week3 = WeekTime.of(LocalDate.of(2016, 11, 28));

        assertEquals(week1, week2);
        assertEquals(week1.hashCode(), week2.hashCode());

        assertNotEquals(week1, week3);
        assertNotEquals(week1.hashCode(), week3.hashCode());
    }
}
