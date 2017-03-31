package com.vaddya.schedule.core;

import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.TaskRepository;
import com.vaddya.schedule.database.stub.StubDatabase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static com.vaddya.schedule.core.lessons.LessonType.*;
import static com.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;
import static org.junit.Assert.*;

/**
 * Модульное тестирование учебных заданий
 *
 * @author vaddya
 */
public class StudyTasksTest {

    private StudyTasks tasks;
    private Task task1;
    private Task task2;

    @Before
    public void setUp() throws Exception {
        Database stub = new StubDatabase();
        TaskRepository repository = stub.getTaskRepository();
        tasks = new StudyTasks(repository);
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

        for (Task task : tasks) {
            task.setType(SEMINAR);
            tasks.updateTask(task);
        }
        assertEquals(SEMINAR, tasks.findTask(0).getType());

        for (Task task : tasks.getAllTasks()) {
            tasks.removeTask(task);
        }
        assertEquals(0, tasks.getNumberOfTasks());

        tasks.addAllTasks(task1, task2);
        assertEquals(2, tasks.getNumberOfTasks());

        tasks.removeAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testCompleteTask() throws Exception {
        tasks.addTask(task1);
        assertEquals(0, tasks.getCompletedTasks().size());

        task1.setComplete(true);
        tasks.updateTask(task1);
        assertEquals(1, tasks.getCompletedTasks().size());
    }

    @Test(expected = NoSuchTaskException.class)
    public void testNoSuchTaskException() throws Exception {
        assertNull(tasks.findTask(UUID.randomUUID()));
    }

    @Test(expected = NoSuchTaskException.class)
    public void testAnotherNoSuchTaskException() throws Exception {
        assertNull(tasks.findTask(100));
    }
}

