package ru.vaddya.schedule.core.db;

import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация класса-заглушки для базы данных
 *
 * @author vaddya
 */
public class FakeDB implements Database {

    private static final Database db = new FakeDB();

    private FakeDB() {
    }

    public static Database getConnection() {
        return db;
    }


    @Override
    public List<Lesson> getLessons(WeekType week, DayOfWeek day) {
        return new ArrayList<>();
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
    public boolean addLesson(ChangedLesson lesson) {
        return true;
    }

    @Override
    public boolean updateLesson(ChangedLesson lesson) {
        return true;
    }

    @Override
    public boolean changeLessonDay(LocalDate from, LocalDate to, Lesson lesson) {
        return true;
    }

    @Override
    public boolean removeLesson(ChangedLesson lesson) {
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
