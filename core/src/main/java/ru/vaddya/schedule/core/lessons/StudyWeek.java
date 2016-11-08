package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.db.Database;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    private Database db = SmartSchedule.db();

    private Map<DayOfWeek, StudyDay> days = new EnumMap<>(DayOfWeek.class);

    public StudyWeek() {
        for (DayOfWeek day : DayOfWeek.values()) {
            days.put(day, new StudyDay(db.getLessons(day)));
        }
    }

    public void addLesson(DayOfWeek day, Lesson lesson) {
        days.get(day).addLesson(lesson);
        db.addLesson(day, lesson);
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

    public List<Lesson> getLessons(DayOfWeek day) {
        return days.get(day).getLessons();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        days.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .forEach(entry -> builder
                        .append(entry.getKey().toString())
                        .append(":\n")
                        .append(entry.getValue()));
        return builder.toString();
    }
}
