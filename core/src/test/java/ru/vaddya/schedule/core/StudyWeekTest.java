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
    public void setUp() {
        Lesson lesson1 = new Lesson("12:00", "13:30", "Программирование", "Лабораторные", null, null);
        Lesson lesson2 = new Lesson("12:00", "13:30", "Высшая математика", "Лекции", null, null);
        week.setDay(DaysOfWeek.MONDAY, new StudyDay(Arrays.asList(lesson1)));
        week.setDay(DaysOfWeek.FRIDAY, new StudyDay(Arrays.asList(lesson1, lesson2)));
    }

    @Test
    public void enumMapTest() {
        assertEquals("Программирование", week.getDay(DaysOfWeek.MONDAY).getLesson(1).getSubject());
        assertEquals("Высшая математика", week.getDay(DaysOfWeek.FRIDAY).getLesson(2).getSubject());
    }
}
