package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

/**
 * База данных, хранящаяся в памяти
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryDatabase implements Database {

    @Override
    public TaskRepository getTaskRepository() {
        return new MemoryTaskRepository();
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new MemoryLessonRepository();
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new MemoryChangeRepository();
    }
}
