package com.vaddya.schedule.desktop.lessons;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;

import java.time.DayOfWeek;

/**
 * Класс для хранения созданного занятия
 *
 * @author vaddya
 */
public class CreatedLesson {

    private Lesson lesson;
    private boolean isOnce;
    private TypeOfWeek typeOfWeek;
    private DayOfWeek sourceDay;
    private DayOfWeek targetDay;

    public CreatedLesson(Lesson lesson, boolean isOnce, TypeOfWeek typeOfWeek, DayOfWeek sourceDay, DayOfWeek targetDay) {
        this.lesson = lesson;
        this.isOnce = isOnce;
        this.typeOfWeek = typeOfWeek;
        this.sourceDay = sourceDay;
        this.targetDay = targetDay;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isOnce() {
        return isOnce;
    }

    public TypeOfWeek getTypeOfWeek() {
        return typeOfWeek;
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