package com.vaddya.schedule.database;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface LessonRepository {

    Map<DayOfWeek, List<Lesson>> getLessons(WeekType week);

    void addLesson(WeekType week, DayOfWeek day, Lesson lesson);

    void updateLesson(WeekType week, DayOfWeek day, Lesson lesson);

    void changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson);

    void removeLesson(WeekType week, DayOfWeek day, Lesson lesson);

}
