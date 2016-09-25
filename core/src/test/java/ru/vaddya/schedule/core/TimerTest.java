package ru.vaddya.schedule.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Vadim on 9/25/2016.
 */
public class TimerTest {

    @Test
    public void parseTest() {
        String time = "8:00";
        Timer timer = new Timer(time);
        assertEquals(8, timer.getHours());
        assertEquals(0, timer.getMinutes());
        assertEquals(time, timer.getTime());

        time = "16:30";
        timer = new Timer(time);
        assertEquals(16, timer.getHours());
        assertEquals(30, timer.getMinutes());
        assertEquals(time, timer.getTime());
    }

    @Test(expected=IllegalArgumentException.class)
    public void illegalArgumentsTest() {
        String time = "ItIsCertainlyNotTheTime";
        Timer timer = new Timer(time);
    }
}