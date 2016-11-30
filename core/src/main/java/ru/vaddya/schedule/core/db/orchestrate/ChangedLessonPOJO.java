package ru.vaddya.schedule.core.db.orchestrate;

import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.LessonChanges;
import ru.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Plain Old Java Object для класса ChangedLesson
 *
 * @author vaddya
 * @since November 30, 2016
 */
public class ChangedLessonPOJO {

    private String changes;
    private String date;
    private String lessonId;
    private String startTime;
    private String endTime;
    private String subject;
    private String type;
    private String place;
    private String teacher;

    public ChangedLessonPOJO() {
    }

    public ChangedLessonPOJO(String changes, String date, String lessonId, String startTime, String endTime, String subject, String type, String place, String teacher) {
        this.changes = changes;
        this.date = date;
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.type = type;
        this.place = place;
        this.teacher = teacher;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
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

    public static ChangedLessonPOJO of(ChangedLesson changedLesson) {
        Lesson lesson = changedLesson.getLesson();
        return new ChangedLessonPOJO(
                changedLesson.getChanges().toString(),
                Dates.FULL_DATE_FORMAT.format(changedLesson.getDate()),
                lesson.getId().toString(),
                lesson.getStartTime().toString(),
                lesson.getEndTime().toString(),
                lesson.getSubject(),
                lesson.getType().toString(),
                lesson.getPlace(),
                lesson.getTeacher()
        );
    }

    public static ChangedLesson parse(String key, ChangedLessonPOJO pojo) {
        Lesson lesson = new Lesson.Builder()
                .id(pojo.getLessonId())
                .startTime(pojo.getStartTime())
                .endTime(pojo.getEndTime())
                .subject(pojo.getSubject())
                .type(pojo.getType())
                .place(pojo.getPlace())
                .teacher(pojo.getTeacher())
                .build();
        return new ChangedLesson(
                UUID.fromString(key),
                LessonChanges.valueOf(pojo.getChanges()),
                LocalDate.from(Dates.FULL_DATE_FORMAT.parse(pojo.getDate())),
                lesson
        );
    }

    @Override
    public String toString() {
        return "ChangedLessonPOJO{" +
                "changes='" + changes + '\'' +
                ", date='" + date + '\'' +
                ", lessonId='" + lessonId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", place='" + place + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
