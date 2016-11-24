package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.utils.WeekTime;

import java.util.HashMap;
import java.util.Map;

/**
 * ru.vaddya.schedule.core.lessons at smart-schedule
 *
 * @author vaddya
 * @since November 25, 2016
 */
public class StudyWeeks {

    private final Map<WeekTime, StudyWeek> datedWeeks;

    public StudyWeeks() {
        datedWeeks = new HashMap<>();
    }

    public StudyWeek get(Schedule schedule, WeekTime weekTime) {
        if (!datedWeeks.containsKey(weekTime)) {
            datedWeeks.put(weekTime, new StudyWeek(schedule.getWeekType(), weekTime));
        }
        return datedWeeks.get(weekTime);
    }
}
