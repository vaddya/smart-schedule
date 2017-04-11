package com.vaddya.schedule.core;

import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.schedule.ScheduleDay;
import com.vaddya.schedule.core.utils.Time;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static com.vaddya.schedule.core.utils.TypeOfWeek.ODD;
import static java.time.LocalDate.of;
import static org.junit.Assert.*;

/**
 * Модульное тестирование учебного дня
 *
 * @author vaddya
 * @since December 01, 2016
 */
public class ScheduleDateTest {

    private ScheduleDay day;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        Database database = new MemoryDatabase();
        StudyLessons lessons = new StudyLessons(database.getLessonRepository());
        StudyChanges changes = new StudyChanges(database.getChangeRepository());
        day = new ScheduleDay(of(2016, 12, 1), ODD, changes, lessons);
        lesson1 = new Lesson.Builder()
                .startTime(Time.from("12:00"))
                .endTime(Time.from("13:30"))
                .subject("Programming")
                .type(LessonType.LAB)
                .place("Place")
                .teacher("Teacher")
                .build();
        lesson2 = new Lesson.Builder()
                .startTime(Time.from("14:00"))
                .endTime(Time.from("15:30"))
                .subject("High math")
                .type(LessonType.LECTURE)
                .place("Place")
                .teacher("Teacher")
                .build();
    }

    @Test
    public void testAddAndRemove() {
        day.addLesson(lesson1);
        day.addLesson(lesson2);

        assertEquals(2, day.getNumberOfLessons());
        assertEquals("Programming", day.findLesson(lesson1.getId()).getSubject());
        assertEquals("High math", day.findLesson(lesson2.getId()).getSubject());

        for (Lesson lesson : day) {
            lesson.setPlace("Test");
            day.updateLesson(lesson);
        }
        assertEquals("Test", day.findLesson(lesson1.getId()).getPlace());

        for (Lesson lesson : day.getLessons()) {
            day.removeLesson(lesson);
        }
        assertEquals(0, day.getNumberOfLessons());
        assertTrue(day.isEmpty());

        day.addAllLessons(Arrays.asList(lesson1, lesson2));
        assertEquals(2, day.getNumberOfLessons());
    }

    @Test
    public void testDate() throws Exception {
        LocalDate mon = of(2016, 12, 1);
        assertEquals(mon, day.getDate());
    }

    @Test(expected = NoSuchLessonException.class)
    public void testNoSuchLessonException() throws Exception {
        assertNull(day.findLesson(UUID.randomUUID()));
    }

}