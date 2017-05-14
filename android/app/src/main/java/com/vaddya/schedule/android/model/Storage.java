package com.vaddya.schedule.android.model;

import com.android.internal.util.Predicate;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

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
    private static List<Lesson> lessons = new ArrayList<>();


    static {
        // TODO: 5/4/2017 stub
        for (int i = 0; i < 10; i++) {
            Task task = new Task(UUID.randomUUID(),
                    "Subject " + i,
                    LessonType.values()[i % LessonType.values().length],
                    new LocalDate().plusDays(i),
                    "Text " + i,
                    i % 2 == 0);
            tasks.add(task);
        }

        for (int i = 0; i < 3; i++) {
            Lesson lesson = new Lesson(UUID.randomUUID(),
                    "10:00",
                    "11:30",
                    "Subject " + i,
                    LessonType.values()[i % LessonType.values().length],
                    "Place " + i,
                    "Teacher " + i);
            lessons.add(lesson);
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

    public static List<Lesson> getLessons(LocalDate date) {
        return lessons;
    }
}