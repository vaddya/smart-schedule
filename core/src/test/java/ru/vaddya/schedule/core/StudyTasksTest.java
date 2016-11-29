package ru.vaddya.schedule.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.db.FakeDB;
import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.tasks.Task;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static ru.vaddya.schedule.core.lessons.LessonType.LAB;
import static ru.vaddya.schedule.core.lessons.LessonType.PRACTICE;
import static ru.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;

/**
 * Модульное тестирование учебных заданий
 *
 * @author vaddya
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class StudyTasksTest {

    private StudyTasks tasks;
    private Task task1;
    private Task task2;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Database.class);
        PowerMockito.when(Database.getConnection()).thenReturn(FakeDB.getConnection());

        tasks = new StudyTasks();
        task1 = new Task.Builder()
                .subject("Programming")
                .type(LAB)
                .deadline(LocalDate.of(2016, 12, 31))
                .textTask("Todo course work")
                .isComplete(false)
                .build();
        task2 = new Task.Builder()
                .subject("High math")
                .type(PRACTICE)
                .deadline(FULL_DATE_FORMAT.parse("31.12.2016"))
                .textTask("№1, №2")
                .isComplete(true)
                .build();
    }

    @Test
    public void testAddAndRemove() throws Exception {
        tasks.addTask(task1);
        tasks.addTask(task2);

        assertEquals(2, tasks.getNumberOfTasks());
        assertEquals("Programming", tasks.findTask(task1.getId()).getSubject());
        assertEquals("№1, №2", tasks.findTask(task2.getId()).getTextTask());

        tasks.removeTask(task1);
        tasks.removeTask(task2);
        assertEquals(0, tasks.getNumberOfTasks());

        tasks.addAllTasks(task1, task2);
        assertEquals(2, tasks.getNumberOfTasks());
    }

    @Test(expected = NoSuchTaskException.class)
    public void testNoSuchTaskException() throws Exception {
        assertNull(tasks.findTask(UUID.randomUUID()));
    }
}

