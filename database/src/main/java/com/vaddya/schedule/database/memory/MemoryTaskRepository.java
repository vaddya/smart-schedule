package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.database.memory at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryTaskRepository implements TaskRepository {

    private List<Task> tasks = new ArrayList<>();

    @Override
    public Task findById(UUID id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void insert(Task task) {
        tasks.add(task);
    }

    @Override
    public void save(Task task) {
        Task upd = findById(task.getId());
        tasks.set(tasks.indexOf(upd), task);
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task);
    }

    @Override
    public void deleteAll() {
        tasks.clear();
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public long size() {
        return tasks.size();
    }
}
