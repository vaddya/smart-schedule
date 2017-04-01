package com.vaddya.schedule.core;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.stub.StubDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static java.time.LocalDate.of;
import static org.junit.Assert.*;

/**
 * Модульное тестирование учебного дня
 *
 * @author vaddya
 * @since December 01, 2016
 */
public class StudyDayTest {

    private StudyDay day;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        Database stub = new StubDatabase();
        day = new StudyDay(new ArrayList<>(), of(2016, 12, 1), stub.getChangeRepository());
        lesson1 = new Lesson.Builder()
                .startTime("12:00")
                .endTime("13:30")
                .subject("Programming")
                .type(LessonType.LAB)
                .place("Place")
                .teacher("Teacher")
                .build();
        lesson2 = new Lesson.Builder()
                .startTime("14:00")
                .endTime("15:30")
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
        assertEquals("Programming", day.findLesson(0).getSubject());
        assertEquals("High math", day.findLesson(lesson2.getId()).getSubject());

        for (Lesson lesson : day) {
            lesson.setPlace("Test");
            day.updateLesson(lesson);
        }
        assertEquals("Test", day.findLesson(0).getPlace());

        for (Lesson lesson : day.getLessons()) {
            day.removeLesson(lesson);
        }
        assertEquals(0, day.getNumberOfLessons());
        assertTrue(day.isEmpty());

        day.addAllLessons(lesson1, lesson2);
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

    @Test(expected = NoSuchLessonException.class)
    public void testAnotherNoSuchLessonException() throws Exception {
        assertNull(day.findLesson(100));
    }
}
