package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.LessonRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения созданных учебных недель
 *
 * @author vaddya
 * @see StudyWeek
 */
public class StudyWeeks {

    private final Map<WeekTime, StudyWeek> weeks = new HashMap<>();

    public StudyWeek get(WeekTime weekTime, StudySchedule schedule, LessonRepository lessons, ChangeRepository changes) {
        if (!weeks.containsKey(weekTime)) {
            weeks.put(weekTime, new StudyWeek(weekTime, schedule.getWeekType(), lessons, changes));
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