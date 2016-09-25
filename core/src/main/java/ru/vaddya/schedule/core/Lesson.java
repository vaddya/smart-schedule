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
}