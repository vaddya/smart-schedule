package com.vaddya.schedule.database;

import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация класса-заглушки для базы данных
 *
 * @author vaddya
 */

public class FakeDB implements DatabaseDeprecated {

    private static final DatabaseDeprecated db = new FakeDB();

    private FakeDB() {
    }

    public static DatabaseDeprecated getConnection() {
        return db;
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getLessons(WeekType week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, new ArrayList<>());
        }
        return map;
    }

    @Override
    public boolean addLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean updateLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson) {
        return true;
    }

    @Override
    public boolean removeLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        return true;
    }


    @Override
    public List<ChangedLesson> getChanges(LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public boolean addChange(ChangedLesson lesson) {
        return true;
    }

    @Override
    public boolean removeChange(ChangedLesson lesson) {
        return true;
    }

    @Override
    public boolean removeAllChanges() {
        return true;
    }


    @Override
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    @Override
    public boolean addTask(Task task) {
        return true;
    }

    @Override
    public boolean updateTask(Task task) {
        return true;
    }

    @Override
    public boolean removeTask(Task task) {
        return true;
    }
}
