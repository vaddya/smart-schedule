package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by Vadim on 10/6/2016.
 */
public class ScheduleTest {

    private ScheduleAPI schedule;

    @Before
    public void setUp() {
        schedule = new Schedule("test");
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, schedule.getDay(DaysOfWeek.MONDAY).getLessons().size());
        schedule.addLesson(DaysOfWeek.MONDAY, new Lesson("10:00", "11:30", "Программирование", "Лекции", null, null));
        assertEquals(1, schedule.getDay(DaysOfWeek.MONDAY).getLessons().size());
        assertEquals("Программирование", schedule.getDay(DaysOfWeek.MONDAY).getLessons().get(0).getSubject());
    }

    @Test
    public void removeLessonTest() throws Exception {
        Lesson lesson = new Lesson("10:00", "11:30", "Программирование", "Лекции", null, null);
        schedule.addLesson(DaysOfWeek.MONDAY, lesson);
        assertEquals(1, schedule.getDay(DaysOfWeek.MONDAY).getLessons().size());
        schedule.removeLesson(DaysOfWeek.MONDAY, lesson);
        assertEquals(0, schedule.getDay(DaysOfWeek.MONDAY).getLessons().size());
    }

    @Test
    public void addTaskTest() throws Exception {
        assertEquals(0, schedule.getActiveTasks().size());
        schedule.addTask(new Task("Программирование", "31.12.2016", "Выполнить курсовую работу"));
        assertEquals(1, schedule.getActiveTasks().size());
        assertEquals("Выполнить курсовую работу", schedule.getActiveTasks().get(0).getTextTask());
        assertEquals("Программирование", schedule.getTaskByText("Выполнить курсовую работу").getSubject());
    }

    @Test
    public void completeTaskTest() throws Exception {
        Task task = new Task("Программирование", "31.12.2016", "Выполнить курсовую работу");
        schedule.addTask(task);
        assertEquals(1, schedule.getActiveTasks().size());
        assertEquals(0, schedule.getCompletedTasks().size());
        schedule.completeTask(task);
        assertEquals(0, schedule.getActiveTasks().size());
        assertEquals(1, schedule.getCompletedTasks().size());
        assertEquals("Выполнить курсовую работу", schedule.getCompletedTasks().get(0).getTextTask());
    }

    @Test
    public void removeTaskTest() throws Exception {
        Task task = new Task("Программирование", "31.12.2016", "Выполнить курсовую работу");
        schedule.addTask(task);
        assertEquals(1, schedule.getActiveTasks().size());
        schedule.removeTask(task);
        assertEquals(0, schedule.getActiveTasks().size());
    }
}
