package ru.vaddya.schedule.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyDay implements Iterable<Lesson> {

    private List<Lesson> lessons;

    public StudyDay() {
        lessons = new ArrayList<>();
    }

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

    public Lesson get(int index) {
        return lessons.get(index - 1);
    }

    public Lesson getById(String id) {
        return lessons.stream()
                .filter((lesson -> id.equals(lesson.getId())))
                .findFirst()
                .get();
    }

    public void update(Lesson lesson) {
        Lesson prev = getById(lesson.getId());
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
                return get(index++);
            }

            @Override
            public void remove() {
                StudyDay.this.remove(lessons.get(index));
            }
        };
    }


}
