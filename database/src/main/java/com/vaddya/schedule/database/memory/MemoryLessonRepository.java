package com.vaddya.schedule.database.memory;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Stream;

import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Хранилище уроков, хранящееся в памяти
 *
 * @author vaddya
 * @since March 31, 2017
 */
public class MemoryLessonRepository implements LessonRepository {

    private Map<DayOfWeek, List<Lesson>> odd;
    private Map<DayOfWeek, List<Lesson>> even;

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
        Stream<Lesson> oddStream = getSchedule(ODD).entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream);
        Stream<Lesson> evenStream = getSchedule(EVEN).entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream);
        return Stream.concat(oddStream, evenStream)
                .filter(lesson -> lesson.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Lesson> findAll(WeekType week, DayOfWeek day) {
        return new ArrayList<>(getSchedule(week).get(day));
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> findAll(WeekType week) {
        Map<DayOfWeek, List<Lesson>> source = getSchedule(week);
        Map<DayOfWeek, List<Lesson>> clone = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            clone.put(day, new ArrayList<>(source.get(day)));
        }
        return clone;
    }

    @Override
    public Optional<DayOfWeek> findLessonDay(UUID id) {
        Optional<Lesson> optional = findById(id);
        if (optional.isPresent()) {
            Lesson lesson = optional.get();
            for (DayOfWeek day : DayOfWeek.values()) {
                if (getSchedule(ODD).get(day).contains(lesson) || getSchedule(EVEN).get(day).contains(lesson)) {
                    return Optional.of(day);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(WeekType week, DayOfWeek day, Lesson lesson) {
        getSchedule(week).get(day).add(lesson);
    }

    @Override
    public void save(WeekType week, DayOfWeek day, Lesson lesson) {
        Optional<Lesson> optional = findById(lesson.getId());
        if (optional.isPresent()) {
            getSchedule(week).get(day).remove(optional.get());
            getSchedule(week).get(day).add(lesson);
        }
    }

    @Override
    public void swapWeeks() {
        Map<DayOfWeek, List<Lesson>> temp = odd;
        odd = even;
        even = odd;
    }

    @Override
    public void delete(UUID id) {
        Optional<Lesson> optional = findById(id);
        if (optional.isPresent()) {
            Lesson lesson = optional.get();
            for (DayOfWeek day : DayOfWeek.values()) {
                List<Lesson> lessons = getSchedule(ODD).get(day);
                if (lessons.contains(lesson)) {
                    lessons.remove(lesson);
                    return;
                }
                lessons = getSchedule(EVEN).get(day);
                if (lessons.contains(lesson)) {
                    lessons.remove(lesson);
                    return;
                }
            }
        }
        throw new NoSuchLessonException("Lesson does not exist: " + id);
    }

    @Override
    public void delete(WeekType week, DayOfWeek day, Lesson lesson) {
        getSchedule(week).get(day).remove(lesson);
    }

    @Override
    public void deleteAll(WeekType week, DayOfWeek day) {
        getSchedule(week).get(day).clear();
    }

    @Override
    public void deleteAll() {
        getSchedule(ODD).forEach((dayOfWeek, lessons) -> lessons.clear());
        getSchedule(EVEN).forEach((dayOfWeek, lessons) -> lessons.clear());
    }

    private Map<DayOfWeek, List<Lesson>> getSchedule(WeekType weekType) {
        return weekType == ODD ? odd : even;
    }

}