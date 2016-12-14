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
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.vaddya.schedule.core.lessons.LessonType.LAB;
import static ru.vaddya.schedule.core.lessons.LessonType.LECTURE;
import static ru.vaddya.schedule.core.utils.WeekType.EVEN;
import static ru.vaddya.schedule.core.utils.WeekType.ODD;

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
        WeekType odd = model.getSchedule(ODD).getWeekType();
        WeekType even = model.getSchedule(EVEN).getWeekType();
        WeekType current = model.getCurrentSchedule().getWeekType();
        assertNotEquals(odd, even);

        model.swapSchedules();
        assertEquals(ODD, model.getSchedule(ODD).getWeekType());
        assertEquals(EVEN, model.getSchedule(EVEN).getWeekType());
        assertNotEquals(current, model.getCurrentSchedule().getWeekType());

    }
}
