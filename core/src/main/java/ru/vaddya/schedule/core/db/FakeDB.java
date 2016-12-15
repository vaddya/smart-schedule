package ru.vaddya.schedule.core.db;

import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.WeekType;

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

// FakeDB это ведь для тестов? Лучше этот класс переместить к тестам
public class FakeDB implements Database {

    private static final Database db = new FakeDB();

    private FakeDB() {
    }

    public static Database getConnection() {
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
