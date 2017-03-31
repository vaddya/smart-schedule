package com.vaddya.schedule.database.mongo;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * com.vaddya.schedule.database.mongo at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MongoLessonRepository implements LessonRepository {

    @Override
    public Map<DayOfWeek, List<Lesson>> getLessons(WeekType week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, new ArrayList<>());
        }
        return map;
    }

    @Override
    public void addLesson(WeekType week, DayOfWeek day, Lesson lesson) {

    }

    @Override
    public void updateLesson(WeekType week, DayOfWeek day, Lesson lesson) {

    }

    @Override
    public void changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson) {

    }

    @Override
    public void removeLesson(WeekType week, DayOfWeek day, Lesson lesson) {

    }
}
