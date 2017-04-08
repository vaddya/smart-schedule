package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.*;

/**
 * com.vaddya.schedule.core.lessons at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class StudyLessons {

    private final LessonRepository lessons;

    /**
     * Конструктор, принимающий хранилище заданий
     */
    public StudyLessons(LessonRepository lessons) {
        this.lessons = lessons;
    }

    /**
     * Добавить занятие в указанный день по указанному типу недели
     */
    public void addLesson(TypeOfWeek week, DayOfWeek day, Lesson lesson) {
        lessons.insert(week, day, lesson);
    }

    /**
     * Найти занятие по ID
     */
    public Lesson findById(UUID id) {
        Optional<Lesson> optional = lessons.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchLessonException(id);
    }

    /**
     * Найти занятия по указанной неделе
     */
    public StudyWeek findAll(TypeOfWeek week) {
        Map<DayOfWeek, List<Lesson>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, lessons.findAll(week, day));
        }
        return new StudyWeek(map);
    }

    /**
     * Обновить задание
     */
    public void updateLesson(Lesson lesson) {
        lessons.save(lesson);
    }

    /**
     * Вернуть тип недели
     */
    public TypeOfWeek getWeekType(UUID id) {
        Optional<TypeOfWeek> optional = lessons.findTypeOfWeek(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchLessonException(id);
    }

    /**
     * Вернуть день занятия
     */
    public DayOfWeek getDayOfWeek(UUID id) {
        Optional<DayOfWeek> optional = lessons.findDayOfWeek(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchLessonException(id);
    }

    /**
     * Изменить день занятия
     */
    public void changeLessonDay(TypeOfWeek typeOfWeek, DayOfWeek newDay, Lesson lesson) {
        lessons.delete(lesson.getId());
        lessons.insert(typeOfWeek, newDay, lesson);
    }

    /**
     * Удалить занятие по ID
     */
    public void removeLesson(UUID id) {
        lessons.delete(id);
    }

    /**
     * Удалить все занятия для указанного дня недели и типа недели
     */
    public void removeAllLessons(TypeOfWeek typeOfWeek, DayOfWeek day) {
        lessons.deleteAll(typeOfWeek, day);
    }

    /**
     * Удалить все занятия по указанной неделе
     */
    public void removeAllLessons(TypeOfWeek typeOfWeek) {
        lessons.deleteAll(typeOfWeek);
    }

    /**
     * Поменять местами порядок недель (четную и нечетную)
     */
    public void swapWeekTypes() {
        lessons.swapWeeks();
    }

}