package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.core.utils.LocalWeek;
import com.vaddya.schedule.core.utils.TypeOfWeek;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

/**
 * Класс для представления учебной недели (списка учебных дней) на конкрентую неделю
 *
 * @author vaddya
 */
public class ScheduleWeek {

    private final LocalWeek week;
    private TypeOfWeek typeOfWeek;
    private final Map<DayOfWeek, ScheduleDay> days;
    private final StudyChanges changes;

    /**
     * Конструктор, принимающий недельный период времени, тип недели и хранилища уроков и изменений
     */
    public ScheduleWeek(LocalWeek week, TypeOfWeek typeOfWeek, StudyLessons lessons, StudyChanges changes) {
        this.week = week;
        this.typeOfWeek = typeOfWeek;
        this.changes = changes;
        this.days = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleDay scheduleDay = new ScheduleDay(week.getDateOf(day), typeOfWeek, changes, lessons);
            days.put(day, scheduleDay);
        }
    }

    /**
     * Получить тип недели (четная или нечетная)
     */
    public TypeOfWeek getTypeOfWeek() {
        return typeOfWeek;
    }

    /**
     * Обновить тип недели (четная или нечетная)
     */
    public void setTypeOfWeek(TypeOfWeek typeOfWeek) {
        this.typeOfWeek = typeOfWeek;
        days.forEach((key, value) -> value.setTypeOfWeek(typeOfWeek));
    }

    /**
     * Изменить тип недели на противоположный (четная - нечетная)
     */
    public void swapWeekType() {
        this.typeOfWeek = typeOfWeek.opposite();
    }

    /**
     * Получить период времени учебной недели
     */
    public LocalWeek getWeek() {
        return week;
    }

    /**
     * Получить учебный день
     */
    public ScheduleDay getDay(DayOfWeek day) {
        return days.get(day);
    }

    /**
     * Получить <code>Map</code>, ключи которой - дни недели,
     * а значения - учебные дни
     */
    public Map<DayOfWeek, ScheduleDay> getAllDays() {
        return new EnumMap<>(days);
    }

    /**
     * Изменить день занятия на данной неделе
     */
    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        changes.changeLessonDate(week.getDateOf(from), week.getDateOf(to), lesson);
    }

    /**
     * Возвращает строковое представление учебной недели
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Week Type: %s (%s)%n", typeOfWeek, week));
        days.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .forEach(entry -> sb
                        .append(entry.getKey())
                        .append(" (")
                        .append(entry.getValue().getDate().format(Dates.SHORT_DATE_FORMAT))
                        .append("):\n")
                        .append(entry.getValue()));
        return sb.toString();
    }

}