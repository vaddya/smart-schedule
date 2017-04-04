package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.util.EnumMap;
import java.util.Map;

import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Класс для хранения расписаний
 *
 * @author vaddya
 * @see StudySchedule
 */
public class StudySchedules {

    private final Map<WeekType, StudySchedule> schedules;

    public StudySchedules(LessonRepository lessons) {
        schedules = new EnumMap<>(WeekType.class);
        schedules.put(ODD, new StudySchedule(ODD, lessons));
        schedules.put(EVEN, new StudySchedule(EVEN, lessons));
    }

    public StudySchedule get(WeekType weekType) {
        return schedules.get(weekType);
    }

    public void swapWeekTypes() {
        schedules.get(ODD).setWeekType(EVEN);
        schedules.get(EVEN).setWeekType(ODD);
        schedules.put(EVEN, schedules.put(ODD, schedules.get(EVEN)));
    }

}