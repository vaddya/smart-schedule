package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.lessons.StudyWeeks;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.schedule.StudySchedules;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.Database;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see SmartSchedule
 */
public class SmartScheduleImpl implements SmartSchedule {

    private final Database database;

    private final StudyTasks tasks;

    private final StudySchedules schedules;

    private final StudyWeeks weeks;

    private WeekType currentWeek;

    public SmartScheduleImpl(Database database) {
        this.database = database;
        this.tasks = new StudyTasks(database.getTaskRepository());
        this.schedules = new StudySchedules(database.getLessonRepository());
        this.weeks = new StudyWeeks();
        this.currentWeek = getWeekType(WeekTime.current());
    }

    public void swapSchedules() {
        schedules.swap();
        currentWeek = currentWeek.opposite();
        weeks.swapWeekTypes();
    }

    public StudyWeek getCurrentWeek() {
        return weeks.get(WeekTime.current(), schedules.get(currentWeek), database.getChangesRepository());
    }

    public StudyWeek getWeek(WeekTime weekTime) {
        WeekType weekType = getWeekType(weekTime);
        return weeks.get(weekTime, schedules.get(weekType), database.getChangesRepository());
    }

    public StudySchedule getCurrentSchedule() {
        return schedules.get(currentWeek);
    }

    public StudySchedule getSchedule(WeekType weekType) {
        return schedules.get(weekType);
    }

    public StudyTasks getTasks() {
        return tasks;
    }

    public WeekType getWeekType(WeekTime weekTime) {
        return weekTime.getWeekNumber() % 2 == 1
                ? WeekType.ODD
                : WeekType.EVEN;
    }

    public void updateLessons() {
        weeks.clear();
    }
}
