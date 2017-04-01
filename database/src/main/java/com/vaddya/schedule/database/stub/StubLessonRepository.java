package com.vaddya.schedule.database.stub;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.*;

/**
 * com.vaddya.schedule.database.stub at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class StubLessonRepository implements LessonRepository {

    @Override
    public Lesson findById(UUID id) {
        return null;
    }

    @Override
    public List<Lesson> findAll(WeekType week, DayOfWeek day) {
        return new ArrayList<>();
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> findAll(WeekType week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, new ArrayList<>());
        }
        return map;
    }

    @Override
    public void insert(WeekType week, DayOfWeek day, Lesson lesson) {

    }

    @Override
    public void save(WeekType week, DayOfWeek day, Lesson lesson) {

    }

    @Override
    public void delete(Lesson lesson) {

    }

    @Override
    public void deleteAll() {

    }
}
