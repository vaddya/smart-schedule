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

    private DayOfWeek sourceDay;

    private DayOfWeek targetDay;

    public CreatedLesson(Lesson lesson, boolean isOnce, DayOfWeek sourceDay, DayOfWeek targetDay) {
        this.lesson = lesson;
        this.isOnce = isOnce;
        this.sourceDay = sourceDay;
        this.targetDay = targetDay;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isOnce() {
        return isOnce;
    }

    public DayOfWeek getSourceDay() {
        return sourceDay;
    }

    public DayOfWeek getTargetDay() {
        return targetDay;
    }

    public boolean isDayChanged() {
        return sourceDay != targetDay;
    }

}
