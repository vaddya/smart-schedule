package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.db.Database;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс для представления расписания
 *
 * @author vaddya
 */
public class StudySchedule {

    private static final Database db = Database.getConnection();

    private WeekType weekType;

    private final Map<DayOfWeek, List<Lesson>> days;

    /**
     * Конструктор, принимающий тип недели
     */
    public StudySchedule(WeekType weekType) {
        this.weekType = weekType;
        this.days = db.getLessons(weekType);
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
        db.addLesson(weekType, day, lesson);
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
        db.updateLesson(weekType, day, lesson);
    }

    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        // TODO: 12/22/2016
    }

    /**
     * Удалить занятие
     */
    public void removeLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).remove(lesson);
        db.removeLesson(weekType, day, lesson);
    }

    /**
     * Получить занятие по дню недели
     */
    public List<Lesson> getLessons(DayOfWeek day) {
        List<Lesson> clone = new ArrayList<>();
        days.get(day).forEach(clone::add);
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
