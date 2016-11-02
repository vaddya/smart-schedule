package ru.vaddya.schedule.core.lessons;

import java.util.*;

/**
 * Класс для представления учебного дня (списка занятий)
 *
 * @author vaddya
 * @see Lesson
 */
public class StudyDay implements Iterable<Lesson> {

    private List<Lesson> lessons;

    public StudyDay(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    public int getSize() {
        return lessons.size();
    }

    public void add(Lesson lesson) {
        lessons.add(lesson);
    }

    public Lesson getLesson(int index) {
        return lessons.get(index - 1);
    }

    public Lesson getLesson(UUID id) {
        Optional<Lesson> res = lessons.stream()
                .filter((lesson -> id.equals(lesson.getId())))
                .findFirst();
        return res.isPresent() ? res.get() : null;
    }
    public List<Lesson> getAllLessons() {
        return new ArrayList<>(lessons);
    }
    public void updateLesson(Lesson lesson) {
        Lesson prev = getLesson(lesson.getId());
        if (prev != null) {
            lessons.set(lessons.indexOf(prev), lesson);
        }
    }

    public void remove(Lesson lesson) {
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

            private int index = 1;

            @Override
            public boolean hasNext() {
                return index <= getSize();
            }

            @Override
            public Lesson next() {
                return getLesson(index++);
            }

            @Override
            public void remove() {
                StudyDay.this.remove(lessons.get(index));
            }
        };
    }
}
