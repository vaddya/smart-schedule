package ru.vaddya.schedule.core.tasks;

import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.LessonType;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

import static ru.vaddya.schedule.core.utils.Dates.BRIEF_DATE_FORMAT;

/**
 * Класс для представления задания
 *
 * @author vaddya
 */
public class Task {

    private final UUID id;
    private String subject;
    private LessonType type;
    private LocalDate deadline;
    private String textTask;
    private boolean isComplete;

    private Task(Builder builder) {
        this.id = builder.id != null
                ? builder.id
                : UUID.randomUUID();
        setSubject(builder.subject);
        setType(builder.type);
        setDeadline(builder.deadline);
        setTextTask(builder.textTask);
        setComplete(builder.isComplete);
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        long daysUntil = Dates.daysUntil(deadline);
        return builder.append(isComplete ? "☑ " : "☐ ")
                .append(BRIEF_DATE_FORMAT.format(deadline))
                .append(" (")
                .append(daysUntil >= 0
                        ? daysUntil + " days"
                        : (isComplete ? "done!" : "overdue!"))
                .append(") | ")
                .append(subject)
                .append(" [")
                .append(type)
                .append("]: ")
                .append(textTask)
                .toString();
    }

    public static final class Builder {
        private UUID id;
        private String subject;
        private LessonType type;
        private LocalDate deadline;
        private String textTask;
        private boolean isComplete;

        public Builder() {
        }

        public Builder id(UUID val) {
            id = val;
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