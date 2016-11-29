package ru.vaddya.schedule.core.schedule;

import ru.vaddya.schedule.core.utils.WeekType;

import java.util.EnumMap;
import java.util.Map;

import static ru.vaddya.schedule.core.utils.WeekType.EVEN;
import static ru.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Класс для хранения расписаний
 *
 * @author vaddya
 * @see StudySchedule
 */
public class StudySchedules {

    private final Map<WeekType, StudySchedule> schedules;

    public StudySchedules() {
        schedules = new EnumMap<>(WeekType.class);
    }

    public StudySchedule get(WeekType weekType) {
        schedules.putIfAbsent(weekType, new StudySchedule(weekType));
        return schedules.get(weekType);
    }

    public void swapSchedules() {
        schedules.get(ODD).setWeekType(EVEN);
        schedules.get(EVEN).setWeekType(ODD);
        schedules.put(EVEN, schedules.put(ODD, schedules.get(EVEN)));
    }
}
