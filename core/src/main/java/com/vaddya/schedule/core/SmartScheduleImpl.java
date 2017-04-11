package com.vaddya.schedule.core;

import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.schedule.ScheduleDay;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.Database;

import java.time.LocalDate;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see SmartSchedule
 */
public class SmartScheduleImpl implements SmartSchedule {

    private final StudyTasks tasks;
    private final StudyLessons lessons;
    private final StudyChanges changes;
    private TypeOfWeek currentTypeOfWeek;

    public SmartScheduleImpl(Database database) {
        this.tasks = new StudyTasks(database.getTaskRepository());
        this.lessons = new StudyLessons(database.getLessonRepository());
        this.changes = new StudyChanges(database.getChangeRepository());
        this.currentTypeOfWeek = LocalWeek.current().getWeekNumber() % 2 != 0
                ? TypeOfWeek.ODD
                : TypeOfWeek.EVEN;
    }

    @Override
    public StudyTasks getTasks() {
        return tasks;
    }

    @Override
    public StudyLessons getLessons() {
        return lessons;
    }

    @Override
    public void swapTypesOfWeeks() {
        currentTypeOfWeek = currentTypeOfWeek.opposite();
        lessons.swapWeekTypes();
    }

    @Override
    public ScheduleWeek getCurrentWeek() {
        return getWeek(LocalWeek.current());
    }

    @Override
    public ScheduleWeek getWeek(LocalWeek week) {
        TypeOfWeek typeOfWeek = getTypeOfWeek(week);
        return new ScheduleWeek(week, typeOfWeek, lessons, changes);
    }

    @Override
    public ScheduleDay getDay(LocalDate date) {
        ScheduleWeek week = getWeek(LocalWeek.from(date));
        return week.getDay(date.getDayOfWeek());
    }

    @Override
    public StudyChanges getChanges() {
        return changes;
    }

    @Override
    public TypeOfWeek getCurrentTypeOfWeek() {
        return currentTypeOfWeek;
    }

    @Override
    public TypeOfWeek getTypeOfWeek(LocalWeek week) {
        return LocalWeek.between(LocalWeek.current(), week) % 2 != 0
                ? currentTypeOfWeek.opposite()
                : currentTypeOfWeek;
    }

}