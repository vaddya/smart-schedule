package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.db.FakeDB;
import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.core.lessons.WeekType;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Модульное тестирование учебной недели
 *
 * @author vaddya
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class StudyWeekTest {

    private StudyWeek week;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        week = new StudyWeek(WeekType.ODD, WeekTime.current());

        lesson1 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Программирование")
                .type(LessonType.LAB)
                .build();
        lesson2 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
                .subject("Высшая математика")
                .type(LessonType.LECTURE)
                .build();
    }

    @Test
    public void testSetAndGet() {
//        week.addLesson(DayOfWeek.MONDAY, lesson1);
//        week.addLesson(DayOfWeek.FRIDAY, lesson2);
//
//        assertEquals(week.getLessons(DayOfWeek.MONDAY).size(), 1);
//        assertEquals(week.getLessons(DayOfWeek.TUESDAY).size(), 0);
//        assertEquals(week.getLessons(DayOfWeek.FRIDAY).size(), 1);
//        assertEquals("Программирование", week.getLessons(DayOfWeek.MONDAY).get(0).getSubject());
//        assertEquals("Высшая математика", week.getLessons(DayOfWeek.FRIDAY).get(0).getSubject());
    }

    @Test(expected = NoSuchLessonException.class)
    public void testNoSuchLessonException() throws Exception {
        assertNull(week.findLesson(UUID.randomUUID()));
    }
}
