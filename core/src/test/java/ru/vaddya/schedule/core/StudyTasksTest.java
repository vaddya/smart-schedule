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
import ru.vaddya.schedule.core.utils.LessonType;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
                .subject("Программирование")
                .type(LessonType.LAB)
                .deadline("31.12.2016")
                .textTask("Доделать курсовой проект")
                .isComplete(false)
                .build();
        task2 = new Task.Builder()
                .subject("Высшая математика")
                .type("PRACTICE")
                .deadline(new Date())
                .textTask("№1, №2")
                .isComplete(true)
                .build();
    }

    @Test
    public void testSetAndGet() throws Exception {
        tasks.addTask(task1);
        tasks.addTask(task2);

        assertEquals(tasks.getSize(), 2);
        assertNotNull(tasks.findTask(task1.getId()));
        assertEquals(tasks.findTask(task1.getId()).getSubject(), "Программирование");
        assertEquals(tasks.findTask(task2.getId()).getTextTask(), "№1, №2");
    }

    @Test(expected = NoSuchTaskException.class)
    public void testNoSuchTaskException() throws Exception {
        assertNull(tasks.findTask(UUID.randomUUID()));
    }
}

