package ru.vaddya.schedule.core.tasks;

import ru.vaddya.schedule.core.utils.DateFormat;
import ru.vaddya.schedule.core.utils.LessonType;

import java.util.Date;
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
    private Date deadline;
    private String textTask;
    private boolean isComplete;

    private Task(Builder builder) {
        this.id = builder.id != null
                ? builder.id
                : UUID.randomUUID();
        this.subject = builder.subject;
        this.type = builder.type;
        this.deadline = builder.deadline;
        this.textTask = builder.textTask;
        this.isComplete = builder.isComplete;
    }

    public String getId() {
        return id.toString();
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

    public Date getDeadlineDate() {
        return (Date) deadline.clone();
    }

    public String getDeadline() {
        return DateFormat.formatShort(deadline);
    }

    public void setDeadline(Date deadline) {
        this.deadline = (Date) deadline.clone();
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

    @Override
    public String toString() {
        return DateFormat.formatExtend(deadline) +
                " | " + subject +
                " [" + type.ru() +
                "]" + ": " + textTask;
    }

    public static final class Builder {
        private UUID id;
        private String subject;
        private LessonType type;
        private Date deadline;
        private String textTask;
        private boolean isComplete;

        public Builder() {
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
            type = LessonType.valueOf(val.toUpperCase());
            return this;
        }

        public Builder deadline(Date val) {
            deadline = (Date) val.clone();
            return this;
        }

        public Builder deadline(String val) {
            deadline = DateFormat.parseShort(val);
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