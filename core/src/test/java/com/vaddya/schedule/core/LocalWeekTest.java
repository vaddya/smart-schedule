package com.vaddya.schedule.core;

import com.vaddya.schedule.core.utils.LocalWeek;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Модульное тестирование представления недели
 *
 * @author vaddya
 */
public class LocalWeekTest {

    @Test
    public void testCreateWeekTime() throws Exception {
        LocalWeek week = LocalWeek.of(LocalDate.of(2016, 11, 21));
        int i = 21; // day of month of MONDAY
        for (DayOfWeek day : DayOfWeek.values()) {
            assertEquals(i++, week.getDateOf(day).getDayOfMonth());
        }
    }

    @Test
    public void testEqualsAndHashCode() throws Exception {
        LocalWeek week1 = LocalWeek.of(LocalDate.of(2016, 11, 21));
        LocalWeek week2 = LocalWeek.of(LocalDate.of(2016, 11, 25));
        LocalWeek week3 = LocalWeek.of(LocalDate.of(2016, 11, 28));

        assertEquals(week1, week2);
        assertEquals(week1.hashCode(), week2.hashCode());

        assertNotEquals(week1, week3);
        assertNotEquals(week1.hashCode(), week3.hashCode());
    }

}