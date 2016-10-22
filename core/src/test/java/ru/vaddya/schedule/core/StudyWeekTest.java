package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyWeekTest {

    StudyWeek week = new StudyWeek();

    @Before
    public void setUp() {
        Lesson lesson1 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Программирование")
                .lessonType(LessonType.LAB)
                .build();
        Lesson lesson2 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
                .subject("Высшая математика")
                .lessonType(LessonType.LECTURE)
                .build();
        week.get(DaysOfWeek.MONDAY).add(lesson1);
        week.get(DaysOfWeek.FRIDAY).add(lesson2);
    }

    @Test
    public void enumMapTest() {
        assertEquals("Программирование", week.get(DaysOfWeek.MONDAY).get(1).getSubject());
        assertEquals("Высшая математика", week.get(DaysOfWeek.FRIDAY).get(1).getSubject());
    }
}
