package ru.vaddya.schedule.core;

import java.time.DayOfWeek;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Vadim on 10/5/2016.
 */
public class StudyDay {

    private List<Lesson> lessons = new ArrayList<>();

    public StudyDay(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }
}
