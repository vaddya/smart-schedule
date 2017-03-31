package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.DatabaseDeprecated;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    private static final DatabaseDeprecated db = DatabaseDeprecated.getConnection();

    private final WeekTime weekTime;

    private WeekType weekType;

    private final Map<DayOfWeek, StudyDay> days = new EnumMap<>(DayOfWeek.class);

    /**
     * Конструктор, получающий недельный период времени и расписание
     */
    public StudyWeek(WeekTime weekTime, StudySchedule schedule) {
        this.weekTime = weekTime;
        this.weekType = schedule.getWeekType();
        for (DayOfWeek day : DayOfWeek.values()) {
            StudyDay studyDay = new StudyDay(schedule.getLessons(day), weekTime.getDateOf(day));
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
     * Найти занятие по ID
     *
     * @throws NoSuchLessonException если указан несуществующий ID
     */
    public Lesson findLesson(UUID id) {
        Optional<Lesson> res = days.entrySet().stream()
                .flatMap(entrySet -> entrySet.getValue().getLessons().stream())
                .filter(lesson -> lesson.getId().equals(id))
                .findFirst();
        if (res.isPresent()) {
            return res.get();
        } else {
            throw new NoSuchLessonException("Wrong lesson ID: " + id);
        }
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
        UUID id = lesson.getId();
        try {
            days.get(from).removeLesson(findLesson(id));
            days.get(to).addLesson(lesson);
            ChangedLesson remove = new ChangedLesson(LessonChanges.REMOVE, weekTime.getDateOf(from), lesson);
            ChangedLesson add = new ChangedLesson(LessonChanges.ADD, weekTime.getDateOf(to), lesson);
            db.addChange(remove);
            db.addChange(add);
        } catch (NoSuchLessonException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает строковое представление учебной недели
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Week Type: %s (%s)%n", weekType, weekTime)
        );
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
