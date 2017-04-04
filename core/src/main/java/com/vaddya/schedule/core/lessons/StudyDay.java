package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.exceptions.NoSuchLessonException;
import com.vaddya.schedule.core.utils.WeekType;
import com.vaddya.schedule.database.ChangeRepository;
import com.vaddya.schedule.database.LessonRepository;

import java.time.LocalDate;
import java.util.*;

import static com.vaddya.schedule.core.lessons.ChangeType.*;

/**
 * Класс для представления учебного дня (списка занятий)
 *
 * @author vaddya
 * @see Lesson
 */
public class StudyDay implements Iterable<Lesson> {

    private static final Comparator<Lesson> TIME_ORDER = Comparator.comparing(Lesson::getStartTime);
    private final LocalDate date;
    private WeekType weekType;
    private final LessonRepository lessons;
    private final ChangeRepository changes;

    /**
     * Конструктор, принимающий дату, тип недели и хранилища уроков и изменений
     */
    public StudyDay(LocalDate date, WeekType weekType, LessonRepository lessons, ChangeRepository changes) {
        this.date = date;
        this.weekType = weekType;
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
    public void addLesson(Lesson lesson) {
        Change change = new Change(ADD, date, lesson);
        changes.insert(change);
    }

    /**
     * Добавить все занятия в учебный день
     */
    public void addAllLessons(Lesson... lessons) {
        Arrays.asList(lessons).forEach(this::addLesson);
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
        throw new NoSuchLessonException("Wrong task ID: " + id);
    }

    /**
     * Найти занятие по индеку
     *
     * @throws NoSuchLessonException если указан неверный индекс
     */
    public Lesson findLesson(int index) {
        List<Lesson> list = getLessonsAfterChanges();
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        throw new NoSuchLessonException("Wrong lesson index: " + index +
                ", Size: " + list.size());
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
    public void updateLesson(Lesson lesson) {
        Change change = new Change(UPDATE, date, lesson);
        changes.insert(change);
    }

    /**
     * Удалить занятие
     */
    public void removeLesson(Lesson lesson) {
        Change change = new Change(REMOVE, date, lesson);
        changes.insert(change);
    }

    /**
     * Удалить все занятия
     */
    public void removeAllLessons() {
        changes.deleteAll();
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
                return findLesson(index++);
            }
        };
    }

    private List<Lesson> getLessonsAfterChanges() {
        List<Lesson> list = lessons.findAll(weekType, date.getDayOfWeek());
        changes.findAll(date).forEach(change -> {
            switch (change.getChangeType()) {
                case ADD:
                    list.add(change.getLesson());
                    break;
                case UPDATE:
                    UUID id = change.getLesson().getId();
                    Optional<Lesson> optional = lessons.findById(id);
                    if (optional.isPresent()) {
                        list.remove(optional.get());
                        list.add(change.getLesson());
                    } else {
                        changes.delete(change);
                    }
                    break;
                case REMOVE:
                    boolean removed = list.remove(change.getLesson());
                    if (!removed) {
                        changes.delete(change);
                    }
                    break;
            }
        });
        list.sort(TIME_ORDER);
        return list;
    }

}