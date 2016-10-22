package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.LessonType;
import ru.vaddya.schedule.core.utils.Timer;

import java.util.UUID;

/**
 * Created by Vadim on 9/25/2016.
 */
public class Lesson {

    private UUID id;
    private Timer startTime;
    private Timer endTime;
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

    public String getId() {
        return id.toString();
    }

    public Timer getStartTime() {
        return startTime;
    }

    public void setStartTime(Timer startTime) {
        this.startTime = startTime;
    }

    public Timer getEndTime() {
        return endTime;
    }

    public void setEndTime(Timer endTime) {
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
                " [" + type.ru() +
                " | " + place +
                " | " + teacher +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        if (id != null ? !id.equals(lesson.id) : lesson.id != null) return false;
        if (startTime != null ? !startTime.equals(lesson.startTime) : lesson.startTime != null) return false;
        if (endTime != null ? !endTime.equals(lesson.endTime) : lesson.endTime != null) return false;
        if (subject != null ? !subject.equals(lesson.subject) : lesson.subject != null) return false;
        if (type != lesson.type) return false;
        if (place != null ? !place.equals(lesson.place) : lesson.place != null) return false;
        return teacher != null ? teacher.equals(lesson.teacher) : lesson.teacher == null;

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
        private Timer startTime;
        private Timer endTime;
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

        public Builder startTime(String val) {
            startTime = Timer.of(val);
            return this;
        }

        public Builder endTime(String val) {
            endTime = Timer.of(val);
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