package ru.vaddya.schedule.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vadim on 10/22/2016.
 */
public class StudyTasks {

    private List<Task> tasks;

    public StudyTasks() {
        tasks = new ArrayList<>();
    }

    public StudyTasks(List<Task> lessons) {
        this.tasks = lessons;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getSize() {
        return tasks.size();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(UUID id) {
        return tasks
                .stream()
                .filter((task -> id.equals(task.getId())))
                .findFirst()
                .get();
    }

    public List<Task> getAll() {
        return tasks
                .stream()
                .collect(Collectors.toList());
    }

    public List<Task> getActive() {
        return tasks
                .stream()
                .filter((task) -> !task.isComplete())
                .collect(Collectors.toList());
    }

    public List<Task> getCompleted() {
        return tasks
                .stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    public List<Task> getOverdue() {
        return tasks
                .stream()
                .filter(task -> !task.isComplete())
                .filter(task -> new Date().after(task.getDeadline()))
                .collect(Collectors.toList());
    }

    public void remove(Task task) {
        tasks.remove(task);
    }

    public void update(Task task) {

    }
}
