package ru.vaddya.schedule.desktop.lessons;

import ru.vaddya.schedule.core.lessons.Lesson;

import java.time.DayOfWeek;

/**
 * ru.vaddya.schedule.desktop.lessons at smart-schedule
 *
 * @author vaddya
 * @since December 22, 2016
 */
public class CreatedLesson {

    private Lesson lesson;

    private boolean isOnce;

    private DayOfWeek dayOfWeek;

    public CreatedLesson(Lesson lesson, boolean isOnce, DayOfWeek dayOfWeek) {
        this.lesson = lesson;
        this.isOnce = isOnce;
        this.dayOfWeek = dayOfWeek;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isOnce() {
        return isOnce;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
