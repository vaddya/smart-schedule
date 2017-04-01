package com.vaddya.schedule.database;

import com.vaddya.schedule.core.tasks.Task;

import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface TaskRepository {

    Task findById(UUID id);

    List<Task> findAll();

    void insert(Task task);

    void save(Task task);

    void delete(Task task);

    void deleteAll();

}