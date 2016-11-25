package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс для представления учебного дня (списка занятий)
 *
 * @author vaddya
 * @see Lesson
 */
public class StudyDay implements Iterable<Lesson> {

    private static final Database db = Database.getConnection();

    private List<Lesson> lessons;

    private LocalDate date;

    public StudyDay(List<Lesson> lessons, LocalDate date) {
        this.lessons = lessons;
        this.date = date;
    }

    /**
     * Проверить пуст ли список занятий
     *
     * @return есть ли занятия
     */
    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    /**
     * Получить количество занятий
     *
     * @return количество занятий
     */
    public int getNumberOfLessons() {
        return lessons.size();
    }

    /**
     * Получить дату учебного дня
     *
     * @return дата учебного дня
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Добавить занятие в расписание
     *
     * @param lesson добавляемое занятие
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Найти занятие по ID
     *
     * @param id UUID занятия
     * @return запрашиваемое занятие
     * @throws NoSuchLessonException если указан несуществующий ID
     */
    public Lesson findLesson(UUID id) {
        Optional<Lesson> res = lessons.stream()
                .filter((lesson -> id.equals(lesson.getId())))
                .findFirst();
        if (res.isPresent()) {
            return res.get();
        } else {
            throw new NoSuchLessonException("Wrong task ID: " + id);
        }
    }

    /**
     * Найти занятие по индеку
     *
     * @param index порядковый номер занятия
     * @return запрашиваемое занятие
     * @throws NoSuchLessonException если указан неверный индекс
     */
    public Lesson findLesson(int index) {
        if (index >= 0 && index < lessons.size()) {
            return lessons.get(index);
        } else {
            throw new NoSuchLessonException("Wrong lesson index: " + index +
                    ", Size: " + lessons.size());
        }
    }

    /**
     * Получить спсок занятий
     *
     * @return список занятий
     */
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    /**
     * Обновить занятие
     *
     * @param lesson обновляемое занятие
     * @throws NoSuchLessonException если указано несуществующее занятие
     */
    public void updateLesson(Lesson lesson) {
        Lesson prev = findLesson(lesson.getId());
        lessons.set(lessons.indexOf(prev), lesson);
    }

    /**
     * Удалить занятие
     *
     * @param lesson удаляемое занятие
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Lesson lesson : lessons) {
            builder.append(lesson.toString()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public Iterator<Lesson> iterator() {
        return new Iterator<Lesson>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getNumberOfLessons();
            }

            @Override
            public Lesson next() {
                return findLesson(index++);
            }
        };
    }
}
