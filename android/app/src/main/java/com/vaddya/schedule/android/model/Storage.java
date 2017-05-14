package com.vaddya.schedule.android.model;

import com.android.internal.util.Predicate;

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

    public static List<Task> getTasks(TasksType type) {
        switch (type) {
            case ACTIVE:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return !task.isComplete();
                    }
                });
            case COMPLETED:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return task.isComplete();
                    }
                });
            default:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return true;
                    }
                });
        }
    }

    private static List<Task> getTasks(Predicate<Task> predicate) {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (predicate.apply(task)) {
                list.add(task);
            }
        }
        return list;
    }

}