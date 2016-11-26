package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.utils.WeekTime;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static ru.vaddya.schedule.core.utils.Dates.SMALL_DATE_FORMAT;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    private static final Database db = Database.getConnection();

    private WeekType weekType;

    private final WeekTime weekTime;

    private final Map<DayOfWeek, StudyDay> days = new EnumMap<>(DayOfWeek.class);


    public StudyWeek(Schedule schedule, WeekTime weekTime) {
        this.weekType = schedule.getWeekType();
        this.weekTime = weekTime;
        for (DayOfWeek day : DayOfWeek.values()) {
            StudyDay studyDay = new StudyDay(schedule.getLessons(day), weekTime.getDateOf(day));
            days.put(day, studyDay);
        }
    }

    /**
     * Получить тип недели (четная или нечетная)
     *
     * @return тип недели
     */
    public WeekType getWeekType() {
        return weekType;
    }

    /**
     * Обновить тип недели (четная или нечетная)
     *
     * @param weekType тип недели
     */
    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    /**
     * Получить период времени учебной недели
     * @return период времени
     */
    public WeekTime getWeekTime() {
        return weekTime;
    }

    /**
     * Найти занятие по ID
     *
     * @param id UUID занятия
     * @return запрашиваемое занятие
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

    public StudyDay getDay(DayOfWeek day) {
        return days.get(day);
    }

    /**
     * Получить все уроки
     *
     * @return Map, ключи которой - дни недели,
     * а значения - список уроков в этот день
     */
    public Map<DayOfWeek, List<Lesson>> getAllLessons() {
        return days.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getLessons())
                );
    }

    /**
     * Изменить день занятия
     *
     * @param from   исходный день недели
     * @param to     новый день недели
     * @param lesson обновляемое занятие
     */
    public void changeLessonDay(DayOfWeek from, DayOfWeek to, Lesson lesson) {
        days.get(from).removeLesson(lesson);
        days.get(to).addLesson(lesson);
        db.changeLessonDay(from, to, lesson);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week Type: ")
                .append(weekType)
                .append(" (")
                .append(weekTime)
                .append(")\n");
        days.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .forEach(entry -> sb
                        .append(entry.getKey())
                        .append(" (")
                        .append(entry.getValue().getDate().format(SMALL_DATE_FORMAT))
                        .append("):\n")
                        .append(entry.getValue()));
        return sb.toString();
    }
}
