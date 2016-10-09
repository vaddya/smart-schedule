package ru.vaddya.schedule.core;

import org.junit.Test;
import ru.vaddya.schedule.core.utils.Timer;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadim on 9/25/2016.
 */
public class TimerTest {

    @Test
    public void parseTest() {
        String time = "8:00";
        Timer timer = Timer.of(time);
        assertEquals(8, timer.getHours());
        assertEquals(0, timer.getMinutes());
        assertEquals(time, timer.toString());

        time = "16:30";
        timer = Timer.of(time);
        assertEquals(16, timer.getHours());
        assertEquals(30, timer.getMinutes());
        assertEquals(time, timer.toString());
    }

    @Test(expected=IllegalArgumentException.class)
    public void illegalArgumentsTest() {
        String time = "ItIsCertainlyNotTheTime";
        Timer timer = Timer.of(time);
    }

    @Test
    public void equalsTest() throws Exception {
        String time = "10:00";
        Timer timer1 = Timer.of(time);
        Timer timer2 = Timer.of(10, 0);
        assertEquals(timer1, timer2);
        assertEquals(timer1.hashCode(), timer2.hashCode());
    }
}