package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.utils.WeekTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения учебных недель
 *
 * @author vaddya
 */
public class StudyWeeks {

    private final Map<WeekTime, StudyWeek> datedWeeks;

    public StudyWeeks() {
        datedWeeks = new HashMap<>();
    }

    public StudyWeek get(Schedule schedule, WeekTime weekTime) {
        datedWeeks.putIfAbsent(weekTime, new StudyWeek(weekTime, schedule));
        return datedWeeks.get(weekTime);
    }
}
