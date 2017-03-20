package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.lessons.StudyWeeks;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.schedule.StudySchedules;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.core.utils.WeekType;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see SmartSchedule
 */
public class SmartScheduleImpl implements SmartSchedule {

    private final StudyTasks tasks = new StudyTasks();

    private final StudyWeeks weeks = new StudyWeeks();

    private final StudySchedules schedules = new StudySchedules();

    private WeekType currentWeek;

    public SmartScheduleImpl() {
        currentWeek = getWeekType(WeekTime.current());
    }

    public void swapSchedules() {
        schedules.swap();
        currentWeek = currentWeek.opposite();
        weeks.swapWeekTypes();
    }

    public StudyWeek getCurrentWeek() {
        return weeks.get(WeekTime.current(), schedules.get(currentWeek));
    }

    public StudyWeek getWeek(WeekTime weekTime) {
        WeekType weekType = getWeekType(weekTime);
        return weeks.get(weekTime, schedules.get(weekType));
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