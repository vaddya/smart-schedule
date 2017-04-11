package com.vaddya.schedule.core.tasks;

import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

/**
 * Класс для представления задания
 *
 * @author vaddya
 */
public class Task {

    private UUID id;
    private String subject;
    private LessonType type;
    private LocalDate deadline;
    private String textTask;
    private boolean isComplete;

    private Task(Builder builder) {
        setId(builder.id);
        setSubject(builder.subject);
        setType(builder.type);
        setDeadline(builder.deadline);
        setTextTask(builder.textTask);
        setComplete(builder.isComplete);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isOverdue() {
        return Dates.isPast(deadline);
    }

    /**
     * (+) Tue 29.11 (done!) | Subject [LessonType]: Task
     */
    @Override
    public String toString() {
        long daysUntil = Dates.daysUntil(deadline);
        return String.format("%s %s, %s | %s [%s]: %s",
                isComplete ? "(+)" : "(-)",
                Dates.BRIEF_DATE_FORMAT.format(deadline),
                (daysUntil >= 0
                        ? daysUntil + " days"
                        : (isComplete ? "done!" : "overdue!")),
                subject,
                type,
                textTask
        );
    }

    public static final class Builder {
        private UUID id = UUID.randomUUID();
        private String subject = "";
        private LessonType type = LessonType.ANOTHER;
        private LocalDate deadline = LocalDate.now();
        private String textTask = "";
        private boolean isComplete = false;

        public Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder id(String val) {
            id = UUID.fromString(val);
            return this;
        }

        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder type(LessonType val) {
            type = val;
            return this;
        }

        public Builder type(String val) {
            type = LessonType.valueOf(val);
            return this;
        }

        public Builder deadline(LocalDate val) {
            deadline = val;
            return this;
        }

        public Builder deadline(TemporalAccessor val) {
            deadline = LocalDate.from(val);
            return this;
        }

        public Builder textTask(String val) {
            textTask = val;
            return this;
        }

        public Builder isComplete(boolean val) {
            isComplete = val;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

}