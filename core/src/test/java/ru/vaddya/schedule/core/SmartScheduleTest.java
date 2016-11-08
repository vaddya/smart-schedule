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

    private Schedule schedule;
    private Lesson lesson;
    private Task task;
    private StudyDay day;
    private StudyTasks tasks;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        schedule = new SmartSchedule();
        lesson = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Программирование")
                .type(LessonType.LECTURE)
                .build();
        task = new Task.Builder()
                .subject("Программирование")
                .type(LessonType.LAB)
                .deadline(LocalDate.parse("31.12.2016"))
                .textTask("Выполнить курсовую работу")
                .isComplete(false)
                .build();

        day = schedule.getDay(DayOfWeek.MONDAY);
        tasks = schedule.getTasks();
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, day.getLessons().size());
        day.addLesson(lesson);
        assertEquals(1, day.getLessons().size());
        assertEquals("Программирование", day.getLesson(1).getSubject());
        assertEquals("Программирование", day.findLesson(lesson.getId()).getSubject());
    }

    @Test
    public void removeLessonTest() throws Exception {
        day.addLesson(lesson);
        assertEquals(1, day.getLessons().size());
        day.removeLesson(day.findLesson(1));
        assertEquals(0, day.getLessons().size());
    }

    @Test
    public void addTaskTest() throws Exception {
        StudyTasks tasks = schedule.getTasks();
        assertEquals(0, tasks.getActiveTasks().size());
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals("Выполнить курсовую работу", tasks.get(1).getTextTask());
        assertEquals("Программирование", tasks.findTask(task.getId()).getSubject());
    }

    @Test
    public void completeTaskTest() throws Exception {
        StudyTasks tasks = schedule.getTasks();
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        assertEquals(0, tasks.getCompletedTasks().size());
        task.setComplete(true);
        assertEquals(0, tasks.getActiveTasks().size());
        assertEquals(1, tasks.getCompletedTasks().size());
        assertEquals("Выполнить курсовую работу", tasks.getCompletedTasks().get(1).getTextTask());
    }

    @Test
    public void removeTaskTest() throws Exception {
        tasks.addTask(task);
        assertEquals(1, tasks.getActiveTasks().size());
        tasks.removeTask(task);
        assertEquals(0, tasks.getActiveTasks().size());
    }
}
