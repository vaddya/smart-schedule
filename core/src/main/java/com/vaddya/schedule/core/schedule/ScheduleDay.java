package com.vaddya.schedule.core.schedule;

import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.changes.StudyChanges;
import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyLessons;
import com.vaddya.schedule.core.utils.TypeOfWeek;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс для представления учебного дня (списка занятий) на конкретную дату
 *
 * @author vaddya
 */
public class ScheduleDay implements Iterable<Lesson> {

    private static final Comparator<Lesson> TIME_ORDER = Comparator.comparing(Lesson::getStartTime);
    private final LocalDate date;
    private TypeOfWeek typeOfWeek;
    private final StudyLessons lessons;
    private final StudyChanges changes;

    /**
     * Конструктор, принимающий дату, тип недели и хранилища уроков и изменений
     */
    public ScheduleDay(LocalDate date, TypeOfWeek typeOfWeek, StudyChanges changes, StudyLessons lessons) {
        this.date = date;
        this.typeOfWeek = typeOfWeek;
        this.lessons = lessons;
        this.changes = changes;
    }

    /**
     * Получить дату учебного дня
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Получить тип недели
     */
    public TypeOfWeek getTypeOfWeek() {
        return typeOfWeek;
    }

    /**
     * Установить тип недели
     */
    public void setTypeOfWeek(TypeOfWeek typeOfWeek) {
        this.typeOfWeek = typeOfWeek;
    }

    /**
     * Проверить пуст ли список занятий
     */
    public boolean isEmpty() {
        return getLessonsAfterChanges().isEmpty();
    }

    /**
     * Получить количество занятий
     */
    public long getNumberOfLessons() {
        return getLessonsAfterChanges().size();
    }

    /**
     * Добавить занятие в учебный день
     */
    public Change addLesson(Lesson lesson) {
        return changes.addLesson(date, lesson);
    }

    /**
     * Добавить все занятия в учебный день
     */
    public void addAllLessons(List<Lesson> lessons) {
        lessons.forEach(this::addLesson);
    }

    /**
     * Найти занятие по ID
     *
     * @throws NoSuchLessonException если указан несуществующий ID
     */
    public Lesson findLesson(UUID id) {
        Optional<Lesson> res = getLessonsAfterChanges().stream()
                .filter((lesson -> id.equals(lesson.getId())))
                .findFirst();
        if (res.isPresent()) {
            return res.get();
        }
        throw new NoSuchLessonException(id);
    }

    /**
     * Получить спсок занятий
     */
    public List<Lesson> getLessons() {
        return getLessonsAfterChanges();
    }

    /**
     * Обновить информацию о занятии
     *
     * @throws NoSuchLessonException если указано несуществующее занятие
     */
    public Change updateLesson(Lesson lesson) {
        return changes.updateLesson(date, lesson);
    }

    /**
     * Удалить занятие
     */
    public Change removeLesson(Lesson lesson) {
        return changes.removeLesson(date, lesson);
    }

    /**
     * Удалить все занятия
     */
    public List<Change> removeAllLessons() {
        return changes.removeAllLessons(date, getLessons());
    }

    /**
     * Возвращает строковое представление учебного дня
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Lesson> list = getLessonsAfterChanges();
        list.forEach(lesson -> sb.append(lesson.toString()).append("\n"));
        return sb.toString();
    }

    /**
     * Возвращает итератор по занятиям
     */
    @Override
    public Iterator<Lesson> iterator() {
        return new Iterator<Lesson>() {
            private int index = 0;
            private List<Lesson> list = getLessonsAfterChanges();

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public Lesson next() {
                return list.get(index++);
            }
        };
    }

    private List<Lesson> getLessonsAfterChanges() {
        List<Lesson> list = lessons.findAll(typeOfWeek).get(date.getDayOfWeek());
        changes.findAll(date).forEach(change -> {
            switch (change.getChangeType()) {
                case ADD:
                    list.add(change.getLesson());
                    break;
                case UPDATE:
                    UUID id = change.getLesson().getId();
                    try {
                        Lesson lesson = lessons.findById(id);
                        list.remove(lesson);
                        list.add(change.getLesson());
                    } catch (NoSuchLessonException e) {
                        changes.removeChange(change);
                    }
                    break;
                case REMOVE:
                    boolean removed = list.remove(change.getLesson());
                    if (!removed) {
                        changes.removeChange(change);
                    }
                    break;
            }
        });
        list.sort(TIME_ORDER);
        return list;
    }

}