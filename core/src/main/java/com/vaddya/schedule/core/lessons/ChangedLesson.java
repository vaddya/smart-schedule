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

    private final LessonChange changes;

    private final LocalDate date;

    private final Lesson lesson;

    public ChangedLesson(UUID id, LessonChange changes, LocalDate date, Lesson lesson) {
        this.id = id;
        this.changes = changes;
        this.date = date;
        this.lesson = lesson;
    }

    public ChangedLesson(LessonChange changes, LocalDate date, Lesson lesson) {
        this.id = UUID.randomUUID();
        this.changes = changes;
        this.date = date;
        this.lesson = lesson;
    }

    public UUID getId() {
        return id;
    }

    public LessonChange getChanges() {
        return changes;
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
