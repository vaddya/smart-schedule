package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.exceptions.NoSuchLessonException;

import java.time.LocalDate;
import java.util.*;

import static ru.vaddya.schedule.core.lessons.LessonChanges.*;

/**
 * Класс для представления учебного дня (списка занятий)
 *
 * @author vaddya
 * @see Lesson
 */
public class StudyDay implements Iterable<Lesson> {

    private static final Database db = Database.getConnection();

    private static final Comparator<Lesson> TIME_ORDER = Comparator.comparing(Lesson::getStartTime);

    private final List<Lesson> lessons;

    private final LocalDate date;

    public StudyDay(List<Lesson> lessons, LocalDate date) {
        this.lessons = lessons;
        this.lessons.sort(TIME_ORDER);
        this.date = date;
        for (ChangedLesson change : db.getChanges(date)) {
            switch (change.getChanges()) {
                case ADD:
                    lessons.add(change.getLesson());
                    break;
                case UPDATE:
                    UUID id = change.getLesson().getId();
                    int index = lessons.indexOf(findLesson(id));
                    if (index != -1) {
                        lessons.add(index, change.getLesson());
                    }
                    break;
                case REMOVE:
                    lessons.remove(change.getLesson());
                    break;
            }
        }
    }

    /**
     * Проверить пуст ли список занятий
     */
    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    /**
     * Получить количество занятий
     */
    public int getNumberOfLessons() {
        return lessons.size();
    }

    /**
     * Получить дату учебного дня
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Добавить занятие в учебный день
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lessons.sort(TIME_ORDER);
        db.addChange(new ChangedLesson(ADD, date, lesson));
    }

    /**
     * Добавить все занятия в учебный день
     */
    public void addAllLessons(Lesson... lessons) {
        Collections.addAll(this.lessons, lessons);
    }

    /**
     * Найти занятие по ID
     *
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
     */
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    /**
     * Обновить информацию о занятии
     *
     * @throws NoSuchLessonException если указано несуществующее занятие
     */
    public void updateLesson(Lesson lesson) {
        Lesson prev = findLesson(lesson.getId());
        lessons.set(lessons.indexOf(prev), lesson);
        lessons.sort(TIME_ORDER);
        db.addChange(new ChangedLesson(UPDATE, date, lesson));
    }

    /**
     * Удалить занятие
     */
    public void removeLesson(Lesson lesson) {
        if (lessons.remove(lesson)) {
            lessons.sort(TIME_ORDER);
            db.addChange(new ChangedLesson(REMOVE, date, lesson));
        }
    }

    /**
     * Удалить все занятия
     */
    public void removeAllLessons() {
        for (Lesson lesson : lessons) {
            db.addChange(new ChangedLesson(REMOVE, date, lesson));
        }
        lessons.clear();
    }

    /**
     * Возвращает строковое представление учебного дня
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        lessons.forEach(lesson -> sb.append(lesson.toString()).append("\n"));
        return sb.toString();
    }

    @Override
    public Iterator<Lesson> iterator() {
        return new Iterator<Lesson>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < lessons.size();
            }

            @Override
            public Lesson next() {
                return findLesson(index++);
            }
        };
    }
}
