package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;
import ru.vaddya.schedule.core.utils.DaysOfWeek;
import ru.vaddya.schedule.core.utils.LessonType;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadim on 10/6/2016.
 */
public class ScheduleTest {

    private ScheduleAPI schedule;
    private Lesson lesson;
    private Task task;

    @Before
    public void setUp() {
        schedule = new Schedule("test");
        lesson = new Lesson.Builder()
                .startTime("10:00")
                .endTime("11:30")
                .subject("Программирование")
                .type(LessonType.LECTURE)
                .build();
        task = new Task.Builder()
                .subject("Программирование")
                .lessonType(LessonType.LAB)
                .deadline("31.12.2016")
                .textTask("Выполнить курсовую работу")
                .isComplete(false)
                .build();
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, schedule.getDay(DaysOfWeek.MONDAY).getNumberOfLessons());
        schedule.addLesson(DaysOfWeek.MONDAY, lesson);
        assertEquals(1, schedule.getDay(DaysOfWeek.MONDAY).getNumberOfLessons());
        assertEquals("Программирование", schedule.getDay(DaysOfWeek.MONDAY).getLesson(1).getSubject());
        assertEquals("Программирование", schedule.getLessonByDay(DaysOfWeek.MONDAY, 1).getSubject());
    }

    @Test
    public void removeLessonTest() throws Exception {
        schedule.addLesson(DaysOfWeek.MONDAY, lesson);
        assertEquals(1, schedule.getDay(DaysOfWeek.MONDAY).getNumberOfLessons());
        schedule.removeLesson(DaysOfWeek.MONDAY, lesson);
        assertEquals(0, schedule.getDay(DaysOfWeek.MONDAY).getNumberOfLessons());
    }

    @Test
    public void addTaskTest() throws Exception {
        assertEquals(0, schedule.getActiveTasks().size());
        schedule.addTask(task);
        assertEquals(1, schedule.getActiveTasks().size());
        assertEquals("Выполнить курсовую работу", schedule.getActiveTasks().get(0).getTextTask());
        assertEquals("Программирование", schedule.getTaskByText("Выполнить курсовую работу").getSubject());
    }

    @Test
    public void completeTaskTest() throws Exception {
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
        schedule.addTask(task);
        assertEquals(1, schedule.getActiveTasks().size());
        schedule.removeTask(task);
        assertEquals(0, schedule.getActiveTasks().size());
    }
}
