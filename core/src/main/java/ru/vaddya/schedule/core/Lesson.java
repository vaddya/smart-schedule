package ru.vaddya.schedule.core;

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

    public Lesson(Timer startTime, Timer endTime, String subject, LessonType type, String place, String teacher) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.type = type;
        this.place = place;
        this.teacher = teacher;
    }

    public Lesson(String startTime, String endTime, String subject, String type, String place, String teacher) {
        this.startTime = new Timer(startTime);
        this.endTime = new Timer(endTime);
        this.subject = subject;
        this.type = LessonType.valueOfRu(type);
        this.place = place;
        this.teacher = teacher;
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
        StringBuilder builder = new StringBuilder();
        builder
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
            .append("]");
        return builder.toString();
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
}