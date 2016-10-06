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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Lesson lesson : lessons) {
            sb.append(lesson.toString() + "\n");
        }
        return sb.toString();
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
