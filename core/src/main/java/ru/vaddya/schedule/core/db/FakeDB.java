package ru.vaddya.schedule.core.db;

import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация класса-заглушки для базы данных
 *
 * @author vaddya
 */
public class FakeDB implements Database {

    private static Database db = new FakeDB();

    private FakeDB() {
    }

    public static Database getConnection() {
        return db;
    }

    @Override
    public List<Lesson> getLessons(DayOfWeek day) {
        return new ArrayList<>();
    }

    @Override
    public boolean addLesson(DayOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean updateLesson(DayOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        return true;
    }

    @Override
    public boolean removeLesson(DayOfWeek day, Lesson lesson) {
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
