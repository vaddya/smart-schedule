package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.utils.Time;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.vaddya.schedule.core.lessons.LessonType.LAB;
import static com.vaddya.schedule.core.lessons.LessonType.LECTURE;
import static com.vaddya.schedule.core.utils.TypeOfWeek.EVEN;
import static com.vaddya.schedule.core.utils.TypeOfWeek.ODD;
import static java.time.DayOfWeek.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Модульное тестирование расписания
 *
 * @author vaddya
 */
public class StudyLessonsTest {

    private StudyLessons lessons;
    private Lesson lesson1;
    private Lesson lesson2;

    @Before
    public void setUp() {
        Database database = new MemoryDatabase();
        lessons = new StudyLessons(database.getLessonRepository());
        lesson1 = new Lesson.Builder()
                .startTime(Time.from("12:00"))
                .endTime(Time.from("13:30"))
                .subject("Programming")
                .type(LAB)
                .build();
        lesson2 = new Lesson.Builder()
                .startTime(Time.from("14:00"))
                .endTime(Time.from("15:30"))
                .subject("High math")
                .type(LECTURE)
                .build();
    }

    @Test
    public void testAddAndRemove() {
        lessons.addLesson(ODD, MONDAY, lesson1);
        lessons.addLesson(ODD, FRIDAY, lesson2);

        assertEquals(1, lessons.findAll(ODD).get(MONDAY).size());
        assertEquals(0, lessons.findAll(ODD).get(TUESDAY).size());
        assertEquals(1, lessons.findAll(ODD).get(FRIDAY).size());

        assertEquals(lesson1, lessons.findAll(ODD).get(MONDAY).get(0));
        assertEquals(lesson2, lessons.findAll(ODD).get(FRIDAY).get(0));

        assertEquals(2, lessons.findAll().size());
        lessons.removeAllLessons(ODD, MONDAY);
        assertEquals(1, lessons.findAll().size());
        lessons.removeAllLessons(ODD);
        assertEquals(0, lessons.findAll().size());
    }

    @Test
    public void testFindById() throws Exception {
        lessons.addLesson(ODD, MONDAY, lesson1);
        Lesson found = lessons.findById(lesson1.getId());
        assertEquals(lesson1, found);
    }

    @Test
    public void testUpdate() throws Exception {
        lessons.addLesson(ODD, MONDAY, lesson1);
        lesson1.setSubject("Subject");
        assertNotEquals(lesson1, lessons.findById(lesson1.getId()));
        lessons.updateLesson(lesson1);
        assertEquals(lesson1, lessons.findById(lesson1.getId()));
    }

    @Test
    public void testWeekAndDay() throws Exception {
        lessons.addLesson(ODD, MONDAY, lesson1);
        assertEquals(ODD, lessons.getWeekType(lesson1.getId()));
        assertEquals(MONDAY, lessons.getDayOfWeek(lesson1.getId()));

        lessons.changeWeekType(lesson1, EVEN);
        lessons.changeLessonDay(lesson1, FRIDAY);
        assertEquals(EVEN, lessons.getWeekType(lesson1.getId()));
        assertEquals(FRIDAY, lessons.getDayOfWeek(lesson1.getId()));
    }

    @Test
    public void testSwapWeeks() throws Exception {
        lessons.addLesson(ODD, MONDAY, lesson1);
        assertEquals(1, lessons.findAll(ODD).get(MONDAY).size());
        assertEquals(0, lessons.findAll(EVEN).get(MONDAY).size());

        lessons.swapWeekTypes();
        assertEquals(0, lessons.findAll(ODD).get(MONDAY).size());
        assertEquals(1, lessons.findAll(EVEN).get(MONDAY).size());
    }
}