package com.vaddya.schedule.core.lessons;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для представления перенесенного, измененного или отмененного урока
 *
 * @author vaddya
 */
public class ChangedLesson {

    private final UUID id;

    private final LessonChange change;

    private final LocalDate date;

    private final Lesson lesson;

    public ChangedLesson(UUID id, LessonChange change, LocalDate date, Lesson lesson) {
        this.id = id;
        this.change = change;
        this.date = date;
        this.lesson = lesson;
    }

    public ChangedLesson(LessonChange change, LocalDate date, Lesson lesson) {
        this(UUID.randomUUID(), change, date, lesson);
    }

    public UUID getId() {
        return id;
    }

    public LessonChange getChange() {
        return change;
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public Lesson getLesson() {
        return lesson;
    }
}
