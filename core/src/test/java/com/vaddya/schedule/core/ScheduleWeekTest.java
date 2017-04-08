package com.vaddya.schedule.core;

import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.vaddya.schedule.core.utils.TypeOfWeek.EVEN;
import static com.vaddya.schedule.core.utils.TypeOfWeek.ODD;
import static java.time.DayOfWeek.*;
import static org.junit.Assert.*;

/**
 * Модульное тестирование учебной недели
 *
 * @author vaddya
 */
public class ScheduleWeekTest {

    private ScheduleWeek week;
    private Lesson lesson;

    @Before
    public void setUp() {
        Database database = new MemoryDatabase();
        StudyLessons lessons = new StudyLessons(database.getLessonRepository());
        StudyChanges changes = new StudyChanges(database.getChangeRepository());
        week = new ScheduleWeek(LocalWeek.of("25.11.2016"), ODD, lessons, changes);
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
        assertEquals("Programming", week.getDay(MONDAY).findLesson(lesson.getId()).getSubject());
        assertEquals("Programming", week.getAllDays().get(MONDAY).findLesson(lesson.getId()).getSubject());

        week.getDay(THURSDAY).removeAllLessons();
        assertTrue(week.getDay(TUESDAY).isEmpty());
        assertEquals(0, week.getDay(TUESDAY).getNumberOfLessons());
    }

    @Test
    public void testWeekType() throws Exception {
        assertEquals(ODD, week.getTypeOfWeek());
        week.setTypeOfWeek(EVEN);
        assertEquals(EVEN, week.getTypeOfWeek());
    }

    @Test
    public void testWeekTime() throws Exception {
        LocalDate mon = LocalDate.of(2016, 11, 21);
        assertEquals(mon, week.getWeek().getDateOf(MONDAY));
    }

    @Test
    public void testChangeLessonDay() throws Exception {
        assertEquals(0, week.getDay(MONDAY).getLessons().size());
        assertEquals(0, week.getDay(FRIDAY).getLessons().size());

        week.getDay(MONDAY).addLesson(lesson);

        assertEquals(1, week.getDay(MONDAY).getLessons().size());
        assertEquals(0, week.getDay(FRIDAY).getLessons().size());

        week.changeLessonDay(MONDAY, FRIDAY, lesson);

        assertEquals(1, week.getDay(FRIDAY).getLessons().size());
        assertEquals(0, week.getDay(MONDAY).getLessons().size());
    }

}