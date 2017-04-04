package com.vaddya.schedule.core.lessons;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс для представления однократного изменения в расписании
 *
 * @author vaddya
 * @see ChangeType
 */
public class Change {

    private final UUID id;
    private final ChangeType changeType;
    private final LocalDate date;
    private final Lesson lesson;

    public Change(UUID id, ChangeType changeType, LocalDate date, Lesson lesson) {
        this.id = id;
        this.changeType = changeType;
        this.date = date;
        this.lesson = lesson;
    }

    public Change(ChangeType changeType, LocalDate date, Lesson lesson) {
        this(UUID.randomUUID(), changeType, date, lesson);
    }

    public UUID getId() {
        return id;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public LocalDate getDate() {
        return date;
    }

    public Lesson getLesson() {
        return lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Change)) return false;

        Change change = (Change) o;

        if (!id.equals(change.id)) return false;
        if (this.changeType != change.changeType) return false;
        if (!date.equals(change.date)) return false;
        return lesson.equals(change.lesson);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + changeType.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + lesson.hashCode();
        return result;
    }

}