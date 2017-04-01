package com.vaddya.schedule.database.stub;

import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.LessonRepository;
import com.vaddya.schedule.database.TaskRepository;

/**
 * com.vaddya.schedule.database.stub at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class StubDatabase implements Database {

    @Override
    public TaskRepository getTaskRepository() {
        return new StubTaskRepository();
    }

    @Override
    public LessonRepository getLessonRepository() {
        return new StubLessonRepository();
    }

    @Override
    public ChangeRepository getChangeRepository() {
        return new StubChangeRepository();
    }
}
