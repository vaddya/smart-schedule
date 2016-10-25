package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;
import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для представления учебной недели (списка учебных дней)
 *
 * @author vaddya
 * @see StudyDay
 */
public class StudyWeek {

    private Database db = Schedule.db();

    private Map<DaysOfWeek, StudyDay> days = new EnumMap<>(DaysOfWeek.class);

    public StudyWeek() {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            days.put(day, new StudyDay(db.getLessons(day)));
        }
    }

    public void addLesson(DaysOfWeek day, Lesson lesson) {
        days.get(day).add(lesson);
        db.addLesson(day, lesson);
    }

    public Lesson findLesson(UUID id) {
        Optional<Lesson> res = days.entrySet().stream()
                .flatMap(entrySet -> entrySet.getValue().getAllLessons().stream())
                .filter(lesson -> lesson.getId().equals(id))
                .findFirst();
        if (res.isPresent()) {
            return res.get();
        } else {
            throw new NoSuchLessonException("Wrong lesson ID: " + id);
        }
    }

    public Lesson findLesson(DaysOfWeek day, int index) {
        return days.get(day).getLesson(index);
    }

    public void updateLesson(DaysOfWeek day, Lesson lesson) {
        days.get(day).updateLesson(lesson);
        db.updateLesson(day, lesson);
    }

    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        days.get(day).remove(lesson);
        db.removeLesson(day, lesson);
    }

    public Map<DaysOfWeek, List<Lesson>> getAllLessons() {
        return days.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getAllLessons())
                );
    }

    public void changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {
        days.get(from).remove(lesson);
        days.get(to).add(lesson);
        db.changeLessonDay(from, to, lesson);
    }

    public List<Lesson> getLessons(DaysOfWeek day) {
        return days.get(day).getAllLessons();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        days.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .forEach(entry -> builder
                        .append(entry.getKey().ru())
                        .append(":\n")
                        .append(entry.getValue()));
        return builder.toString();
    }
}
