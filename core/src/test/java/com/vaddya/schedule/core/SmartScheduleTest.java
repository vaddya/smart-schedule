package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.FakeDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Функциональное тестирование приложения
 *
 * @author vaddya
 */

//TODO: Были ведь раньше интеграционные тесты тоже? Куда делись?

@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class SmartScheduleTest {

    private SmartSchedule model;
    private Lesson lesson;
    private Task task;
    private StudyDay day;
    private StudyTasks tasks;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        model = new SmartScheduleImpl();
        lesson = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Programming")
                .type(LessonType.LECTURE)
                .place("Place")
                .teacher("Teacher")
                .build();
        task = new Task.Builder()
                .subject("Programming")
                .type(LessonType.LAB)
                .deadline(LocalDate.of(2016, 12, 31))
                .textTask("Todo course work")
                .isComplete(false)
                .build();
        day = model.getCurrentWeek().getDay(MONDAY);
        tasks = model.getTasks();
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
        StudyTasks tasks = model.getTasks();
        assertEquals(0, tasks.getActiveTasks().size());
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals("Todo course work", tasks.findTask(0).getTextTask());
        assertEquals("Programming", tasks.findTask(task.getId()).getSubject());
    }

    @Test
    public void editTaskTest() throws Exception {
        StudyTasks tasks = model.getTasks();
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
        WeekType odd = model.getSchedule(WeekType.ODD).getWeekType();
        WeekType even = model.getSchedule(WeekType.EVEN).getWeekType();
        WeekType current = model.getCurrentSchedule().getWeekType();
        assertNotEquals(odd, even);

        model.swapSchedules();
        assertEquals(WeekType.ODD, model.getSchedule(WeekType.ODD).getWeekType());
        assertEquals(WeekType.EVEN, model.getSchedule(WeekType.EVEN).getWeekType());
        assertNotEquals(current, model.getCurrentSchedule().getWeekType());

    }
}
