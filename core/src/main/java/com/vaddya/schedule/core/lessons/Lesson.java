package com.vaddya.schedule.core.lessons;

import com.vaddya.schedule.core.utils.Time;

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

        if (!id.equals(lesson.id)) return false;
        if (!startTime.equals(lesson.startTime)) return false;
        if (!endTime.equals(lesson.endTime)) return false;
        if (!subject.equals(lesson.subject)) return false;
        if (type != lesson.type) return false;
        if (!place.equals(lesson.place)) return false;
        return teacher.equals(lesson.teacher);
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
        private UUID id = UUID.randomUUID();
        private Time startTime = Time.now();
        private Time endTime = Time.now();
        private String subject = "";
        private LessonType type = LessonType.ANOTHER;
        private String place = "";
        private String teacher = "";

        public Builder() {
        }

        public Builder(Lesson lesson) {
            id = lesson.id;
            startTime = lesson.startTime;
            endTime = lesson.endTime;
            subject = lesson.subject;
            type = lesson.type;
            place = lesson.place;
            teacher = lesson.teacher;
        }

        public Builder id(String val) {
            id = UUID.fromString(val);
            return this;
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder startTime(Time val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Time val) {
            endTime = val;
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