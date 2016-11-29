package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.db.FakeDB;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.core.schedule.StudySchedule;

import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertEquals;
import static ru.vaddya.schedule.core.utils.WeekType.EVEN;
import static ru.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Модульное тестирование расписания
 *
 * @author vaddya
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class StudyScheduleTest {

    private StudySchedule schedule;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        schedule = new StudySchedule(ODD);
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
