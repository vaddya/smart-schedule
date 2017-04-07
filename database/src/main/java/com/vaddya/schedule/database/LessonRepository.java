package com.vaddya.schedule.database;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * com.vaddya.schedule.database at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public interface LessonRepository {

    Optional<Lesson> findById(UUID id);

    List<Lesson> findAll(WeekType week, DayOfWeek day);

    Map<DayOfWeek, List<Lesson>> findAll(WeekType week);

    void insert(WeekType week, DayOfWeek day, Lesson lesson);

    void save(WeekType week, DayOfWeek day, Lesson lesson);

    void swapWeeks();

    void delete(WeekType week, DayOfWeek day, Lesson lesson);

    void deleteAll();

}