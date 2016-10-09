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
    private LessonType lessonType;
    private Date deadline;
    private String textTask;
    private boolean isComplete;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.Y", new Locale("ru"));

    private Task(Builder builder) {
        id = builder.id != null
                ? builder.id
                : UUID.randomUUID();
        subject = builder.subject;
        lessonType = builder.lessonType;
        deadline = builder.deadline;
        textTask = builder.textTask;
        isComplete = builder.isComplete;
    }

    public static final Comparator<Task> DATE_ORDER = (t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline());

    public UUID getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public Date getDeadline() {
        return deadline;
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
        return new StringBuilder()
                .append(dateFormat.format(deadline))
                .append(" | ")
                .append(subject)
                .append(" [")
                .append(lessonType.getRu())
                .append("]")
                .append(": ")
                .append(textTask)
                .toString();
    }

    public static final class Builder {
        private UUID id;
        private String subject;
        private LessonType lessonType;
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

        public Builder lessonType(LessonType val) {
            lessonType = val;
            return this;
        }

        public Builder lessonType(String val) {
            lessonType = LessonType.valueOf(val);
            return this;
        }

        public Builder deadline(String val) {
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
            try {
                deadline = parser.parse(val);
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