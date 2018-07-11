package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;
import com.vaddya.schedule.database.ChangeRepository;

/**
 * База данных, хранящаяся в памяти
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryDatabase implements Database {

    private final TaskRepository tasks = new MemoryTaskRepository();
    private final LessonRepository lessons = new MemoryLessonRepository();
    private final ChangeRepository changes = new MemoryChangeRepository();

    @Override
    public TaskRepository getTaskRepository() {
        return tasks;
    }

    @Override
    public LessonRepository getLessonRepository() {
        return lessons;
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return changes;
    }

}