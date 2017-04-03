package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Stream;

import static com.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * com.vaddya.schedule.database.memory at smart-schedule
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryLessonRepository implements LessonRepository {

    private final Map<DayOfWeek, List<Lesson>> odd;
    private final Map<DayOfWeek, List<Lesson>> even;

    public MemoryLessonRepository() {
        odd = new EnumMap<>(DayOfWeek.class);
        even = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            odd.put(day, new ArrayList<>());
            even.put(day, new ArrayList<>());
        }
    }

    @Override
    public Optional<Lesson> findById(UUID id) {
        Stream<Lesson> oddStream = odd.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream);
        Stream<Lesson> evenStream = even.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream);
        return Stream.concat(oddStream, evenStream)
                .filter(lesson -> lesson.getId().equals(id))
                .findFirst();

    }

    @Override
    public List<Lesson> findAll(WeekType week, DayOfWeek day) {
        return week == ODD ? odd.get(day) : even.get(day);
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> findAll(WeekType week) {
        return week == ODD ? odd : even;
    }

    @Override
    public void insert(WeekType week, DayOfWeek day, Lesson lesson) {
        if (week == ODD) {
            odd.get(day).add(lesson);
        } else {
            even.get(day).add(lesson);
        }
    }

    @Override
    public void save(WeekType week, DayOfWeek day, Lesson lesson) {
        if (week == ODD) {
            odd.get(day).add(lesson);
        } else {
            even.get(day).add(lesson);
        }
    }

    @Override
    public void delete(WeekType week, DayOfWeek day, Lesson lesson) {
        if (week == ODD) {
            odd.get(day).remove(lesson);
        } else {
            even.get(day).remove(lesson);
        }
    }

    @Override
    public void deleteAll() {
        odd.forEach((dayOfWeek, lessons) -> lessons.clear());
        even.forEach((dayOfWeek, lessons) -> lessons.clear());
    }
}
