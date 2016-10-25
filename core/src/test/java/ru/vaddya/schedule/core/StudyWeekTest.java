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
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

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

        week = new StudyWeek();

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
        week.addLesson(DaysOfWeek.MONDAY, lesson1);
        week.addLesson(DaysOfWeek.FRIDAY, lesson2);

        assertEquals(week.getLessons(DaysOfWeek.MONDAY).size(), 1);
        assertEquals(week.getLessons(DaysOfWeek.TUESDAY).size(), 0);
        assertEquals(week.getLessons(DaysOfWeek.FRIDAY).size(), 1);
        assertEquals("Программирование", week.getLessons(DaysOfWeek.MONDAY).get(0).getSubject());
        assertEquals("Высшая математика", week.getLessons(DaysOfWeek.FRIDAY).get(0).getSubject());
    }

    @Test(expected = NoSuchLessonException.class)
    public void testNoSuchLessonException() throws Exception {
        assertNull(week.findLesson(UUID.randomUUID()));
    }
}
