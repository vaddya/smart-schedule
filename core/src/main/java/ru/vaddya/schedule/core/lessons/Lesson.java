package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.utils.Time;

import java.util.UUID;

/**
 * Класс для представления занятия
 *
 * @author vaddya
 */
public class Lesson {

    private final UUID id;
    private Time startTime;
    private Time endTime;
    private String subject;
    private LessonType type;
    private String place;
    private String teacher;

    private Lesson(Builder builder) {
        this.id = builder.id != null
                ? builder.id
                : UUID.randomUUID();
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.subject = builder.subject;
        this.type = builder.type;
        this.place = builder.place;
        this.teacher = builder.teacher;
    }

    public Lesson(Lesson lesson) {
        this.id = UUID.randomUUID();
        this.startTime = lesson.startTime;
        this.endTime = lesson.endTime;
        this.subject = lesson.subject;
        this.type = lesson.type;
        this.place = lesson.place;
        this.teacher = lesson.teacher;
    }

    public UUID getId() {
        return id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.valueOf(startTime) +
                " - " + endTime +
                " | " + subject +
                " [" + type +
                " | " + place +
                " | " + teacher +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        if (startTime != null ? !startTime.equals(lesson.startTime) : lesson.startTime != null) return false;
        if (endTime != null ? !endTime.equals(lesson.endTime) : lesson.endTime != null) return false;
        if (subject != null ? !subject.equals(lesson.subject) : lesson.subject != null) return false;
        if (type != lesson.type) return false;
        if (place != null ? !place.equals(lesson.place) : lesson.place != null) return false;
        return teacher != null ? teacher.equals(lesson.teacher) : lesson.teacher == null;
    }

    public boolean deepEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        return equals(lesson) && id.equals(lesson.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private UUID id;
        private Time startTime;
        private Time endTime;
        private String subject;
        private LessonType type;
        private String place;
        private String teacher;

        public Builder() {
        }

        public Builder id(String val) {
            id = UUID.fromString(val);
            return this;
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder startTime(String val) {
            startTime = Time.of(val);
            return this;
        }

        public Builder endTime(String val) {
            endTime = Time.of(val);
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

        public Builder place(String val) {
            place = val;
            return this;
        }

        public Builder teacher(String val) {
            teacher = val;
            return this;
        }

        public Lesson build() {
            return new Lesson(this);
        }
    }
}