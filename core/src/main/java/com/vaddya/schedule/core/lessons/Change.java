package com.vaddya.schedule.core.lessons;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для представления перенесенного, измененного или отмененного урока
 *
 * @author vaddya
 */
public class Change {

    private final UUID id;

    private final ChangeType change;

    private final LocalDate date;

    private final Lesson lesson;

    public Change(UUID id, ChangeType change, LocalDate date, Lesson lesson) {
        this.id = id;
        this.change = change;
        this.date = date;
        this.lesson = lesson;
    }

    public Change(ChangeType change, LocalDate date, Lesson lesson) {
        this(UUID.randomUUID(), change, date, lesson);
    }

    public UUID getId() {
        return id;
    }

    public ChangeType getChange() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Change)) return false;

        Change change1 = (Change) o;

        if (!id.equals(change1.id)) return false;
        if (change != change1.change) return false;
        if (!date.equals(change1.date)) return false;
        return lesson.equals(change1.lesson);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + change.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + lesson.hashCode();
        return result;
    }
}
