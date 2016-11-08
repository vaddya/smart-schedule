package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;

import java.time.DayOfWeek;

/**
 * Реализация интерфейса приложения Smart SmartSchedule
 *
 * @author vaddya
 * @see Schedule
 */
public class SmartSchedule implements Schedule {

    private final StudyTasks tasks = new StudyTasks();

    private final StudyWeek week = new StudyWeek();

    private static final Database db = Database.getConnection();

    public static Database db() {
        return db;
    }

    @Override
    public StudyDay getDay(DayOfWeek day) {
        return week.getDay(day);
    }

    @Override
    public StudyWeek getWeek() {
        return week;
    }

    @Override
    public StudyTasks getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return week.toString();
    }
}
