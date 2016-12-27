package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.schedule.StudySchedule;
import ru.vaddya.schedule.core.utils.WeekTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения учебных недель
 *
 * @author vaddya
 * @see StudyWeek
 */
public class StudyWeeks {

    private final Map<WeekTime, StudyWeek> weeks;

    public StudyWeeks() {
        weeks = new HashMap<>();
    }

    public StudyWeek get(WeekTime weekTime, StudySchedule schedule) {
        if (!weeks.containsKey(weekTime)) {
            weeks.put(weekTime, new StudyWeek(weekTime, schedule));
        }
        return weeks.get(weekTime);
    }

    public void swapWeekTypes() {
        weeks.forEach((k, v) -> v.swapWeekType());
    }

    public void clear() {
        weeks.clear();
    }
}
