package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.stub.StubDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;
import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertEquals;

/**
 * Модульное тестирование расписания
 *
 * @author vaddya
 */
public class StudyWeekScheduleTest {

    private StudySchedule schedule;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        Database stub = new StubDatabase();
        schedule = new StudySchedule(ODD, stub.getLessonRepository());
        lesson1 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Programming")
                .type(LessonType.LAB)
                .build();
        lesson2 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
                .subject("High math")
                .type(LessonType.LECTURE)
                .build();
    }

    @Test
    public void testSetAndGet() {
        schedule.addLesson(MONDAY, lesson1);
        schedule.addLesson(FRIDAY, lesson2);

        assertEquals(1, schedule.getLessons(MONDAY).size());
        assertEquals(0, schedule.getLessons(TUESDAY).size());
        assertEquals(1, schedule.getLessons(FRIDAY).size());

        assertEquals("Programming", schedule.getLessons(MONDAY).get(0).getSubject());
        assertEquals("High math", schedule.getLessons(FRIDAY).get(0).getSubject());
    }

    @Test
    public void testWeekType() throws Exception {
        assertEquals(ODD, schedule.getWeekType());
        schedule.setWeekType(EVEN);
        assertEquals(EVEN, schedule.getWeekType());
    }

}
