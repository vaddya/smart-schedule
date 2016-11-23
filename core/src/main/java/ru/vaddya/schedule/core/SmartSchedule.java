package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.StudyDay;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.lessons.StudyWeekType;
import ru.vaddya.schedule.core.tasks.StudyTasks;

import java.time.DayOfWeek;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see Schedule
 */
public class SmartSchedule implements Schedule {

    private final StudyTasks tasks = new StudyTasks();

    private final StudyWeek week = new StudyWeek(StudyWeekType.ODD);

    @Override
    public StudyDay getDay(DayOfWeek day) {
        return week.getDay(day);
    }

    @Override
    public StudyWeek getCurrentWeek() {
        return week;
    }

    @Override
    public StudyTasks getTasks() {
        return tasks;
    }
}
