package com.vaddya.schedule.core.lessons;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

/**
 * com.vaddya.schedule.core.lessons at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class StudyWeek {

    private final Map<DayOfWeek, List<Lesson>> lessons;

    public StudyWeek(Map<DayOfWeek, List<Lesson>> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> get(DayOfWeek day) {
        return lessons.get(day);
    }

}