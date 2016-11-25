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
import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.LessonType;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Функциональное тестирование приложения
 *
 * @author vaddya
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class SmartScheduleTest {

    private SmartSchedule schedule;
    private Lesson lesson;
    private Task task;
    private StudyDay day;
    private StudyTasks tasks;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        schedule = new SmartScheduleImpl();
        lesson = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Programming")
                .type(LessonType.LECTURE)
                .build();
        task = new Task.Builder()
                .subject("Programming")
                .type(LessonType.LAB)
                .deadline(Dates.parseShort("31.12.2016"))
                .textTask("Todo course work")
                .isComplete(false)
                .build();
        day = schedule.getCurrentWeek().getDay(DayOfWeek.MONDAY);
        tasks = schedule.getTasks();
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, day.getNumberOfLessons());
        day.addLesson(lesson);
        assertEquals(1, day.getNumberOfLessons());
        assertEquals("Programming", day.findLesson(0).getSubject());
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
        StudyTasks tasks = schedule.getTasks();
        assertEquals(0, tasks.getActiveTasks().size());
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals("Todo course work", tasks.findTask(0).getTextTask());
        assertEquals("Programming", tasks.findTask(task.getId()).getSubject());
    }

    @Test
    public void editTaskTest() throws Exception {
        StudyTasks tasks = schedule.getTasks();
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
}
