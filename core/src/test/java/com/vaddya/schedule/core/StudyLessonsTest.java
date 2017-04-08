package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.vaddya.schedule.core.lessons.LessonType.LAB;
import static com.vaddya.schedule.core.lessons.LessonType.LECTURE;
import static com.vaddya.schedule.core.utils.TypeOfWeek.ODD;
import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertEquals;

/**
 * Модульное тестирование расписания
 *
 * @author vaddya
 */
public class StudyLessonsTest {

    private StudyLessons lessons;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        Database database = new MemoryDatabase();
        lessons = new StudyLessons(database.getLessonRepository());
        lesson1 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Programming")
                .type(LAB)
                .build();
        lesson2 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
                .subject("High math")
                .type(LECTURE)
                .build();
    }

    @Test
    public void testSetAndGet() {
        lessons.addLesson(ODD, MONDAY, lesson1);
        lessons.addLesson(ODD, FRIDAY, lesson2);

        assertEquals(1, lessons.findAll(ODD).get(MONDAY).size());
        assertEquals(0, lessons.findAll(ODD).get(TUESDAY).size());
        assertEquals(1, lessons.findAll(ODD).get(FRIDAY).size());

        assertEquals("Programming", lessons.findAll(ODD).get(MONDAY).get(0).getSubject());
        assertEquals("High math", lessons.findAll(ODD).get(FRIDAY).get(0).getSubject());
    }

}