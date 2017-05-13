package com.vaddya.schedule.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
public class Storage {

    private static List<Task> tasks = new ArrayList<>();

    static {
        // TODO: 5/4/2017 stub
        for (int i = 0; i < 10; i++) {
            Task task = new Task(UUID.randomUUID(),
                    "Subject " + i,
                    LessonType.values()[i % LessonType.values().length],
                    new Date(),
                    "Text " + i,
                    i % 2 == 0);
            tasks.add(task);
        }
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static void addTasks(List<Task> newTasks) {
        tasks.addAll(newTasks);
    }

    public static List<Task> getTasks(TasksType type) {
        switch (type) {
            case ACTIVE:
                return getActiveTasks();
            case COMPLETED:
                return getCompletedTasks();
            default:
                return tasks;
        }
    }

    private static List<Task> getActiveTasks() {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isComplete()) {
                list.add(task);
            }
        }
        return list;
    }

    private static List<Task> getCompletedTasks() {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isComplete()) {
                list.add(task);
            }
        }
        return list;
    }

}