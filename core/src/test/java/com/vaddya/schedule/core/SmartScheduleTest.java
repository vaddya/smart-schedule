package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.stub.StubDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.vaddya.schedule.core.lessons.LessonType.LAB;
import static com.vaddya.schedule.core.lessons.LessonType.LECTURE;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Функциональное тестирование приложения
 *
 * @author vaddya
 */

//TODO: Были ведь раньше интеграционные тесты тоже? Куда делись?

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(DatabaseDeprecated.class)
public class SmartScheduleTest {

    private SmartSchedule smartSchedule;
    private Lesson lesson;
    private Task task;
    private StudyDay day;
    private StudyTasks tasks;

    @Before
    public void setUp() {
        Database stub = new StubDatabase();
        smartSchedule = new SmartScheduleImpl(stub);
        lesson = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Programming")
                .type(LECTURE)
                .place("Place")
                .teacher("Teacher")
                .build();
        task = new Task.Builder()
                .subject("Programming")
                .type(LAB)
                .deadline(LocalDate.of(2016, 12, 31))
                .textTask("Todo course work")
                .isComplete(false)
                .build();
        day = smartSchedule.getCurrentWeek().getDay(MONDAY);
        tasks = smartSchedule.getTasks();
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, day.getNumberOfLessons());
        day.addLesson(lesson);
        assertEquals(1, day.getNumberOfLessons());
        assertEquals("Programming", day.findLesson(lesson.getId()).getSubject());
    }

    @Test
    public void removeLessonTest() throws Exception {
        day.addLesson(lesson);
        assertEquals(1, day.getNumberOfLessons());
        day.removeLesson(day.findLesson(0));
        assertEquals(0, day.getNumberOfLessons());
    }

    @Test
    public void addTaskTest() throws Exception {
        StudyTasks tasks = smartSchedule.getTasks();
        assertEquals(0, tasks.getActiveTasks().size());
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals("Todo course work", tasks.findTask(0).getTextTask());
        assertEquals("Programming", tasks.findTask(task.getId()).getSubject());
    }

    @Test
    public void editTaskTest() throws Exception {
        StudyTasks tasks = smartSchedule.getTasks();
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals(0, tasks.getCompletedTasks().size());
        task.setComplete(true);
        tasks.updateTask(task);
        assertEquals(0, tasks.getActiveTasks().size());
        assertEquals(1, tasks.getCompletedTasks().size());
        assertEquals("Todo course work", tasks.getCompletedTasks().get(0).getTextTask());
    }

    @Test
    public void removeTaskTest() throws Exception {
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        tasks.removeTask(task);
        assertEquals(0, tasks.getActiveTasks().size());
    }

    @Test
    public void testSwapSchedules() throws Exception {
        WeekType odd = smartSchedule.getSchedule(WeekType.ODD).getWeekType();
        WeekType even = smartSchedule.getSchedule(WeekType.EVEN).getWeekType();
        WeekType current = smartSchedule.getCurrentSchedule().getWeekType();
        assertNotEquals(odd, even);

        smartSchedule.swapSchedules();
        assertEquals(WeekType.ODD, smartSchedule.getSchedule(WeekType.ODD).getWeekType());
        assertEquals(WeekType.EVEN, smartSchedule.getSchedule(WeekType.EVEN).getWeekType());
        assertNotEquals(current, smartSchedule.getCurrentSchedule().getWeekType());

    }
}
