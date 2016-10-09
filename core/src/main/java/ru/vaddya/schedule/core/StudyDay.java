package ru.vaddya.schedule.core;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyDay {

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

    public int getNumberOfLessons() {
        return lessons.size();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public Lesson getLesson(int i) {
        return lessons.get(i-1);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Lesson lesson : lessons) {
            builder.append(lesson.toString() + "\n");
        }
        return builder.toString();
    }
}
