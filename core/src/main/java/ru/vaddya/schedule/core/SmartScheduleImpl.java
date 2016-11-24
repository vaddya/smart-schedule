package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.lessons.Schedule;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.lessons.StudyWeeks;
import ru.vaddya.schedule.core.lessons.WeekType;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.util.EnumMap;
import java.util.Map;

import static ru.vaddya.schedule.core.lessons.WeekType.EVEN;
import static ru.vaddya.schedule.core.lessons.WeekType.ODD;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see SmartSchedule
 */
public class SmartScheduleImpl implements SmartSchedule {

    private final StudyTasks tasks = new StudyTasks();

    private final Map<WeekType, Schedule> schedules;

    private WeekType currentWeek = ODD;

    private final StudyWeeks weeks = new StudyWeeks();

    public SmartScheduleImpl() {
        schedules = new EnumMap<WeekType, Schedule>(WeekType.class) {{
            put(ODD, new Schedule(ODD));
            put(EVEN, new Schedule(EVEN));
        }};
    }

    public StudyWeek getCurrentWeek() {
        return weeks.get(schedules.get(currentWeek), WeekTime.current());
    }

    public Schedule getOddSchedule() {
        return schedules.get(ODD);
    }

    public Schedule getEvenSchedule() {
        return schedules.get(EVEN);
    }

    public StudyTasks getTasks() {
        return tasks;
    }
}
