package com.vaddya.schedule.database;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;

import java.time.DayOfWeek;
import java.util.List;
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

    List<Lesson> findAll();

    List<Lesson> findAll(TypeOfWeek week, DayOfWeek day);

    Optional<TypeOfWeek> findTypeOfWeek(UUID id);

    Optional<DayOfWeek> findDayOfWeek(UUID id);

    void insert(TypeOfWeek week, DayOfWeek day, Lesson lesson);

    void save(Lesson lesson);

    void saveTypeOfWeek(Lesson lesson, TypeOfWeek week);

    void saveDayOfWeek(Lesson lesson, DayOfWeek day);

    void swapWeeks();

    void delete(UUID id);

    void deleteAll(TypeOfWeek week);

    void deleteAll(TypeOfWeek week, DayOfWeek day);

}