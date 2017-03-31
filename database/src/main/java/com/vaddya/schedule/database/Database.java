package com.vaddya.schedule.database;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface Database {

    TaskRepository getTaskRepository();

    LessonRepository getLessonRepository();

    ChangeRepository getChangesRepository();

}
