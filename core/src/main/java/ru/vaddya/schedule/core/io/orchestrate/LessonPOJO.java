package ru.vaddya.schedule.core.io.orchestrate;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

/**
 * Created by Vadim on 10/21/2016.
 */
public class LessonPOJO {

    private String day;
    private String startTime;
    private String endTime;
    private String subject;
    private String type;
    private String place;
    private String teacher;

    public LessonPOJO() {
    }

    public LessonPOJO(String day, String startTime, String endTime, String subject, String type, String place, String teacher) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.type = type;
        this.place = place;
        this.teacher = teacher;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public static LessonPOJO of(DaysOfWeek day, Lesson lesson) {
        return new LessonPOJO(
                day.toString(),
                lesson.getStartTime().toString(),
                lesson.getEndTime().toString(),
                lesson.getSubject(),
                lesson.getType().toString(),
                lesson.getPlace(),
                lesson.getTeacher()
        );
    }

    @Override
    public String toString() {
        return "LessonPOJO{" +
                "day='" + day + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", place='" + place + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
