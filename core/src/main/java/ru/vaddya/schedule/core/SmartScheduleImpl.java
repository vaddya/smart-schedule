package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.lessons.StudyWeeks;
import ru.vaddya.schedule.core.schedule.StudySchedule;
import ru.vaddya.schedule.core.schedule.StudySchedules;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.utils.WeekTime;
import ru.vaddya.schedule.core.utils.WeekType;

import static ru.vaddya.schedule.core.utils.WeekType.EVEN;
import static ru.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Реализация интерфейса приложения Smart StudySchedule
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

    private WeekType getWeekType(WeekTime weekTime) {
        return weekTime.getWeekNumber() % 2 == 1
                ? ODD
                : EVEN;
    }
}
