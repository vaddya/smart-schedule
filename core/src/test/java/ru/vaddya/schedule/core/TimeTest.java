package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.exceptions.IllegalTimeFormatException;
import ru.vaddya.schedule.core.utils.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Модульное тестирование представления времени в приложении
 *
 * @author vaddya
 */
public class TimeTest {

    @Test
    public void parseTest() {
        String time = "08:00";
        Time timer = Time.of(time);
        assertEquals(8, timer.hours());
        assertEquals(0, timer.minutes());
        assertEquals(time, timer.toString());

        time = "16:30";
        timer = Time.of(time);
        assertEquals(16, timer.hours());
        assertEquals(30, timer.minutes());
        assertEquals(time, timer.toString());
    }

    @Test
    public void testIsAfter() throws Exception {
        Time time1 = Time.of(16, 0);
        Time time2 = Time.of(16, 30);
        Time time3 = Time.of(16, 30);
        assertTrue(time1.compareTo(time2) < 0);
        assertTrue(time2.compareTo(time1) > 0);
        assertTrue(time2.compareTo(time3) == 0);
    }

    @Test
    public void equalsTest() throws Exception {
        String time = "10:00";
        Time time1 = Time.of(time);
        Time time2 = Time.of(10, 0);
        assertEquals(time1, time2);
        assertEquals(time1.hashCode(), time2.hashCode());
    }

    @Test(expected=IllegalTimeFormatException.class)
    public void illegalIllegalTimeFormatTest() {
        String time = "ItIsCertainlyNotTheTime";
        Time.of(time);
    }

    @Test(expected=IllegalTimeFormatException.class)
    public void anotherIllegalTimeFormatTest() {
        String time = "05:61";
        Time.of(time);
    }
}