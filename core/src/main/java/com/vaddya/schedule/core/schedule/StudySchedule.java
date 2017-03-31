package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Класс для представления расписания на неделю
 *
 * @author vaddya
 */
public class StudySchedule {

    private final LessonRepository repository;

    private WeekType weekType;

    private final Map<DayOfWeek, List<Lesson>> days;

    /**
     * Конструктор, принимающий тип недели
     */
    public StudySchedule(WeekType weekType, LessonRepository repository) {
        this.weekType = weekType;
        this.repository = repository;
        this.days = repository.findAll(weekType);
    }

    /**
     * Получить тип недели
     */
    public WeekType getWeekType() {
        return weekType;
    }

    /**
     * Установить тип недели
     */
    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    /**
     * Добавить занятие
     */
    public void addLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        repository.insert(weekType, day, lesson);
    }

    /**
     * Найти занятие по ID
     */
    public Lesson findLesson(UUID id) {
        Optional<Lesson> result = days.entrySet().stream()
                .map(Map.Entry::getValue).flatMap(Collection::stream)
                .filter(lesson -> lesson.getId().equals(id)).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw new NoSuchLessonException("No lesson with ID: " + id);
    }

    /**
     * Найти занятие по индексу
     */
    public Lesson findLesson(DayOfWeek day, int index) {
        return days.get(day).get(index);
    }

    /**
     * Обновить занятие
     */
    public void updateLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        repository.save(weekType, day, lesson);
    }

    /**
     * Изменить день занятия
     */
    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        days.get(from).remove(lesson);
        days.get(to).add(lesson);
        repository.save(weekType, to, lesson);
    }

    /**
     * Удалить занятие
     */
    public void removeLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).remove(lesson);
        repository.delete(lesson);
    }

    /**
     * Получить занятие по дню недели
     */
    public List<Lesson> getLessons(DayOfWeek day) {
        List<Lesson> clone = new ArrayList<>();
        clone.addAll(days.get(day));
        return clone;
    }

    /**
     * Возвращает строковое представление расписания
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week Type: ").append(weekType).append("\n");
        for (DayOfWeek day : DayOfWeek.values()) {
            sb.append(day)
                    .append(":\n");
            days.get(day).forEach(lesson -> sb.append(lesson).append("\n"));
        }
        return sb.toString();
    }
}
