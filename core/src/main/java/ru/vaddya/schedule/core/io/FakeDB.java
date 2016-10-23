package ru.vaddya.schedule.core.io;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация класса-заглушки для базы данных
 *
 * @author vaddya
 */
public class FakeDB implements Database {

    private FakeDB() {
    }

    public static Database getConnection() {
        return new FakeDB();
    }

    @Override
    public List<Lesson> getLessons(DaysOfWeek day) {
        return new ArrayList<>();
    }

    @Override
    public boolean addLesson(DaysOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean updateLesson(DaysOfWeek day, Lesson lesson) {
        return true;
    }

    @Override
    public boolean changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {
        return true;
    }

    @Override
    public boolean removeLesson(DaysOfWeek day, Lesson lesson) {
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
