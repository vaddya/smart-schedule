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
import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.schedule.StudySchedule;
import ru.vaddya.schedule.core.utils.LessonType;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static ru.vaddya.schedule.core.utils.WeekType.EVEN;
import static ru.vaddya.schedule.core.utils.WeekType.ODD;

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

        week = new StudyWeek(WeekTime.of("25.11.2016"), new StudySchedule(ODD));
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
        week.getDay(MONDAY).addLesson(lesson1);
        week.getDay(DayOfWeek.FRIDAY).addLesson(lesson2);

        assertEquals(1, week.getDay(MONDAY).getNumberOfLessons());
        assertEquals(0, week.getDay(TUESDAY).getNumberOfLessons());
        assertEquals(1, week.getDay(FRIDAY).getNumberOfLessons());

        assertEquals("Programming", week.getDay(MONDAY).findLesson(0).getSubject());
        assertEquals("High math", week.getDay(FRIDAY).findLesson(lesson2.getId()).getSubject());
    }

    @Test
    public void testWeekType() throws Exception {
        assertEquals(ODD, week.getWeekType());
        week.setWeekType(EVEN);
        assertEquals(EVEN, week.getWeekType());
    }

    @Test
    public void testWeekTime() throws Exception {
        LocalDate mon = LocalDate.of(2016, 11, 21);
        assertEquals(mon, week.getWeekTime().getDateOf(MONDAY));
    }

    @Test
    public void testChangeLessonDay() throws Exception {
        StudyDay wed = week.getDay(WEDNESDAY);
        StudyDay sun = week.getDay(SUNDAY);
        wed.addLesson(lesson1);
        assertEquals(1, wed.getNumberOfLessons());
        assertEquals(0, sun.getNumberOfLessons());

        week.changeLessonDay(WEDNESDAY, SUNDAY, lesson1);
        assertEquals(0, wed.getNumberOfLessons());
        assertEquals(1, sun.getNumberOfLessons());
    }

    @Test(expected = NoSuchLessonException.class)
    public void testNoSuchLessonException() throws Exception {
        assertNull(week.findLesson(UUID.randomUUID()));
    }
}
