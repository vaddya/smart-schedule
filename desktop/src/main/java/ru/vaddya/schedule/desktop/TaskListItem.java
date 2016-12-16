package ru.vaddya.schedule.desktop;

import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;

import java.util.UUID;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 16, 2016
 */
public class TaskListItem {

    // TODO: 12/16/2016 i18n
    private static final String DONE ="Выполнено";
    private static final String UNDONE ="Не выполнено";

    private UUID id;
    private String status;
    private String subject;
    private String type;
    private String deadline;
    private String text;

    public TaskListItem(Task task) {
        this.id = task.getId();
        this.status = task.isComplete() ? DONE : UNDONE;
        this.deadline = task.getDeadline().toString();
        this.subject = task.getSubject();
        this.type = task.getType().toString();
        this.text = task.getTextTask();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task toTask() {
        return new Task.Builder()
                .id(id)
                .isComplete(status.equals(DONE))
                .subject(subject)
                .type(LessonType.valueOf(type))
                .deadline(Dates.FULL_DATE_FORMAT.parse(deadline))
                .textTask(text)
                .build();
    }
}
