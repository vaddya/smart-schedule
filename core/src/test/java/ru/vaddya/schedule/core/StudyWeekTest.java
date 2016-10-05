package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyWeekTest {

    StudyWeek week = new StudyWeek();

    @Before
    public void init() {
        Lesson lesson1 = new Lesson(null, null, "Программирование", null, null, null);
        Lesson lesson2 = new Lesson(null, null, "Высшая математика", null, null, null);
        week.setDay(DaysOfWeek.MONDAY, new StudyDay(Arrays.asList(lesson1)));
        week.setDay(DaysOfWeek.FRIDAY, new StudyDay(Arrays.asList(lesson1, lesson2)));
    }

    @Test
    public void enumMapTest() {
        assertEquals(week.getDay(DaysOfWeek.MONDAY).getLessons().get(0).getSubject(), "Программирование");
        assertEquals(week.getDay(DaysOfWeek.FRIDAY).getLessons().get(1).getSubject(), "Высшая математика");
        assertNull(week.getDay(DaysOfWeek.SUNDAY));
    }
}
