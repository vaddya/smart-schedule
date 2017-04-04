package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.vaddya.schedule.core.lessons.LessonType.LAB;
import static com.vaddya.schedule.core.lessons.LessonType.LECTURE;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Функциональное тестирование приложения
 *
 * @author vaddya
 */

public class SmartScheduleTest {

    private SmartSchedule schedule;
    private Lesson lesson;
    private Task task;
    private StudyDay day;
    private StudyTasks tasks;

    @Before
    public void setUp() {
        Database database = new MemoryDatabase();
        schedule = new SmartScheduleImpl(database);
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
                .deadline(of(2016, 12, 31))
                .textTask("Todo course work")
                .isComplete(false)
                .build();
        day = schedule.getCurrentWeek().getDay(MONDAY);
        tasks = schedule.getTasks();
    }

    @Test
    public void addLessonTest() throws Exception {
        assertEquals(0, day.getNumberOfLessons());
        day.addLesson(lesson);
        assertEquals(1, day.getNumberOfLessons());
        assertEquals("Programming", day.findLesson(lesson.getId()).getSubject());
        day.removeLesson(lesson);
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

    @Test
    public void testSwapSchedules() throws Exception {
        WeekType odd = schedule.getSchedule(WeekType.ODD).getWeekType();
        WeekType even = schedule.getSchedule(WeekType.EVEN).getWeekType();
        WeekType current = schedule.getCurrentSchedule().getWeekType();
        assertNotEquals(odd, even);

        schedule.swapSchedules();
        assertEquals(WeekType.ODD, schedule.getSchedule(WeekType.ODD).getWeekType());
        assertEquals(WeekType.EVEN, schedule.getSchedule(WeekType.EVEN).getWeekType());
        assertNotEquals(current, schedule.getCurrentSchedule().getWeekType());
    }

}