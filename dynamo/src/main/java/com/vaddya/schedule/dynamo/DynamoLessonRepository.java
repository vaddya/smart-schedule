package com.vaddya.schedule.dynamo;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DynamoLessonRepository implements LessonRepository {
    @Override
    public Optional<Lesson> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Lesson> findAll() {
        return null;
    }

    @Override
    public List<Lesson> findAll(TypeOfWeek week, DayOfWeek day) {
        return null;
    }

    @Override
    public Optional<TypeOfWeek> findTypeOfWeek(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<DayOfWeek> findDayOfWeek(UUID id) {
        return Optional.empty();
    }

    @Override
    public void insert(TypeOfWeek week, DayOfWeek day, Lesson lesson) {

    }

    @Override
    public void save(Lesson lesson) {

    }

    @Override
    public void saveTypeOfWeek(Lesson lesson, TypeOfWeek week) {

    }

    @Override
    public void saveDayOfWeek(Lesson lesson, DayOfWeek day) {

    }

    @Override
    public void swapWeeks() {

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void deleteAll(TypeOfWeek week) {

    }

    @Override
    public void deleteAll(TypeOfWeek week, DayOfWeek day) {

    }
}
