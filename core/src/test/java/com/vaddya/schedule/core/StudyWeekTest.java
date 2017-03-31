package com.vaddya.schedule.core;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.stub.StubDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;
import static java.time.DayOfWeek.*;
import static org.junit.Assert.*;

/**
 * Модульное тестирование учебной недели
 *
 * @author vaddya
 */
public class StudyWeekTest {

    private StudyWeek week;
    private Lesson lesson;

    @Before
    public void setUp() {
        Database stub = new StubDatabase();
        StudySchedule schedule = new StudySchedule(ODD, stub.getLessonRepository());
        week = new StudyWeek(WeekTime.of("25.11.2016"), schedule, stub.getChangesRepository());
        lesson = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Programming")
                .type(LessonType.LAB)
                .place("Place")
                .teacher("Teacher")
                .build();
    }

    @Test
    public void testAddAndGet() {
        week.getDay(MONDAY).addLesson(lesson);

        assertFalse(week.getDay(MONDAY).isEmpty());
        assertEquals(1, week.getDay(MONDAY).getNumberOfLessons());
        assertEquals("Programming", week.findLesson(lesson.getId()).getSubject());
        assertEquals("Programming", week.getDay(MONDAY).findLesson(0).getSubject());
        assertEquals("Programming", week.getAllDays().get(MONDAY).findLesson(0).getSubject());

        assertTrue(week.getDay(TUESDAY).isEmpty());
        assertEquals(0, week.getDay(TUESDAY).getNumberOfLessons());
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
        wed.addLesson(lesson);
        assertEquals(1, wed.getNumberOfLessons());
        assertEquals(0, sun.getNumberOfLessons());

        week.changeLessonDay(WEDNESDAY, SUNDAY, lesson);
        assertEquals(0, wed.getNumberOfLessons());
        assertEquals(1, sun.getNumberOfLessons());
    }

    @Test(expected = NoSuchLessonException.class)
    public void testNoSuchLessonException() throws Exception {
        assertNull(week.findLesson(UUID.randomUUID()));
    }
}
