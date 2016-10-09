package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.utils.LessonType;
import ru.vaddya.schedule.core.utils.Timer;

/**
 * Created by Vadim on 9/25/2016.
 */
public class Lesson {

    private Timer startTime;
    private Timer endTime;
    private String subject;
    private LessonType type;
    private String place;
    private String teacher;

    private Lesson(Builder builder) {
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setSubject(builder.subject);
        setType(builder.type);
        setPlace(builder.place);
        setTeacher(builder.teacher);
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
        return new StringBuilder()
            .append(startTime)
            .append(" - ")
            .append(endTime)
            .append(" | ")
            .append(subject)
            .append(" [")
            .append(type.getRu())
            .append(" | ")
            .append(place)
            .append(" | ")
            .append(teacher)
            .append("]")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        return startTime.equals(lesson.startTime)
                && endTime.equals(lesson.endTime)
                && subject.equals(lesson.subject)
                && type == lesson.type
                && place.equals(lesson.place)
                && teacher.equals(lesson.teacher);
    }

    @Override
    public int hashCode() {
        int result = startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + place.hashCode();
        result = 31 * result + teacher.hashCode();
        return result;
    }

    public static final class Builder {
        private Timer startTime;
        private Timer endTime;
        private String subject;
        private LessonType type;
        private String place;
        private String teacher;

        public Builder() {
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