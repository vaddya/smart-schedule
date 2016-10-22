package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.LessonType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Vadim on 10/5/2016.
 */
public class Task {

    private UUID id;
    private String subject;
    private LessonType type;
    private Date deadline;
    private String textTask;
    private boolean isComplete;

    private static final SimpleDateFormat EXTEND_DATE_FORMAT = new SimpleDateFormat("E dd.MM.Y", new Locale("ru"));
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final Comparator<Task> DATE_ORDER = (t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline());

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

    public Date getDeadline() {
        return deadline;
    }

    public String getDeadlineStr() {
        return DATE_FORMAT.format(deadline);
    }

    public void setDeadline(Date deadline) {
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

    @Override
    public String toString() {
        return EXTEND_DATE_FORMAT.format(deadline) +
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
            type = LessonType.valueOf(val);
            return this;
        }

        public Builder deadline(Date val) {
            deadline = val;
            return this;
        }

        public Builder deadline(String val) {
            try {
                deadline = DATE_FORMAT.parse(val);
            } catch (ParseException e) {
                deadline = new Date();
            }
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