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
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

import static org.junit.Assert.assertEquals;

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
        week.get(DaysOfWeek.MONDAY).add(lesson1);
        week.get(DaysOfWeek.FRIDAY).add(lesson2);

        assertEquals(week.get(DaysOfWeek.MONDAY).getSize(), 1);
        assertEquals(week.get(DaysOfWeek.TUESDAY).getSize(), 0);
        assertEquals(week.get(DaysOfWeek.FRIDAY).getSize(), 1);
        assertEquals("Программирование", week.get(DaysOfWeek.MONDAY).get(1).getSubject());
        assertEquals("Высшая математика", week.get(DaysOfWeek.FRIDAY).get(1).getSubject());
    }
}
