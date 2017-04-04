package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.LessonRepository;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

import static com.vaddya.schedule.core.lessons.ChangeType.ADD;
import static com.vaddya.schedule.core.lessons.ChangeType.REMOVE;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    private final WeekTime weekTime;
    private WeekType weekType;
    private final Map<DayOfWeek, StudyDay> days;
    private final ChangeRepository changes;

    /**
     * Конструктор, получающий недельный период времени и расписание
     */
    public StudyWeek(WeekTime weekTime, WeekType weekType, LessonRepository lessons, ChangeRepository changes) {
        this.weekTime = weekTime;
        this.weekType = weekType;
        this.changes = changes;
        this.days = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            StudyDay studyDay = new StudyDay(weekTime.getDateOf(day), weekType, lessons, changes);
            days.put(day, studyDay);
        }
    }

    /**
     * Получить тип недели (четная или нечетная)
     */
    public WeekType getWeekType() {
        return weekType;
    }

    /**
     * Обновить тип недели (четная или нечетная)
     */
    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
        days.forEach((key, value) -> value.setWeekType(weekType));
    }

    /**
     * Изменить тип недели на противоположный (четная - нечетная)
     */
    public void swapWeekType() {
        this.weekType = weekType.opposite();
    }

    /**
     * Получить период времени учебной недели
     */
    public WeekTime getWeekTime() {
        return weekTime;
    }

    /**
     * Получить учебный день
     */
    public StudyDay getDay(DayOfWeek day) {
        return days.get(day);
    }

    /**
     * Получить <code>Map</code>, ключи которой - дни недели,
     * а значения - учебные дни
     */
    public Map<DayOfWeek, StudyDay> getAllDays() {
        return new EnumMap<>(days);
    }

    /**
     * Изменить день занятия на данной неделе
     */
    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        Change remove = new Change(REMOVE, weekTime.getDateOf(from), lesson);
        changes.insert(remove);
        Change add = new Change(ADD, weekTime.getDateOf(to), lesson);
        changes.insert(add);
    }

    /**
     * Возвращает строковое представление учебной недели
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Week Type: %s (%s)%n", weekType, weekTime));
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
