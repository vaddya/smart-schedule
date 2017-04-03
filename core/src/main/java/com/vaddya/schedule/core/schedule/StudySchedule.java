package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Класс для представления расписания на неделю
 *
 * @author vaddya
 */
public class StudySchedule {

    private final LessonRepository lessons;

    private WeekType weekType;

    /**
     * Конструктор, принимающий тип недели
     */
    public StudySchedule(WeekType weekType, LessonRepository lessons) {
        this.weekType = weekType;
        this.lessons = lessons;
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
        lessons.insert(weekType, day, lesson);
    }

    /**
     * Найти занятие по ID
     */
    public Lesson findLesson(UUID id) {
        Optional<Lesson> result = lessons.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new NoSuchLessonException("No lesson with ID: " + id);
    }

    /**
     * Найти занятие по индексу
     */
    public Lesson findLesson(DayOfWeek day, int index) {
        return lessons.findAll(weekType).get(day).get(index);
    }

    /**
     * Обновить занятие
     */
    public void updateLesson(DayOfWeek day, Lesson lesson) {
        lessons.save(weekType, day, lesson);
    }

    /**
     * Изменить день занятия
     */
    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        lessons.delete(weekType, from, lesson);
        lessons.insert(weekType, to, lesson);
    }

    /**
     * Удалить занятие
     */
    public void removeLesson(DayOfWeek day, Lesson lesson) {
        lessons.delete(weekType, day, lesson);
    }

    /**
     * Получить занятие по дню недели
     */
    public List<Lesson> getLessons(DayOfWeek day) {
        return lessons.findAll(weekType, day);
    }

    /**
     * Возвращает строковое представление расписания
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Map<DayOfWeek, List<Lesson>> days = lessons.findAll(weekType);
        sb.append("Week Type: ").append(weekType).append("\n");
        for (DayOfWeek day : DayOfWeek.values()) {
            sb.append(day)
                    .append(":\n");
            days.get(day).forEach(lesson -> sb.append(lesson).append("\n"));
        }
        return sb.toString();
    }
}
