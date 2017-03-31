package com.vaddya.schedule.database.stub;

import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.database.stub at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class StubTaskRepository implements TaskRepository {

    @Override
    public Task findById(UUID id) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void insert(Task task) {

    }

    @Override
    public void save(Task task) {

    }

    @Override
    public void delete(Task task) {

    }
}
