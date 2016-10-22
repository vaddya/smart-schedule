package ru.vaddya.schedule.core.io.orchestrate;

import ru.vaddya.schedule.core.Task;

/**
 * Created by Vadim on 10/21/2016.
 */
public class TaskPOJO {

    private String subject;
    private String type;
    private String deadline;
    private String textTask;
    private boolean isComplete;

    public TaskPOJO() {
    }

    public TaskPOJO(String subject, String type, String deadline, String textTask, boolean isComplete) {
        this.subject = subject;
        this.type = type;
        this.deadline = deadline;
        this.textTask = textTask;
        this.isComplete = isComplete;
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
        return "TaskPOJO{" +
                "subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", deadline='" + deadline + '\'' +
                ", textTask='" + textTask + '\'' +
                ", isComplete='" + isComplete + '\'' +
                '}';
    }

    public static TaskPOJO of(Task task) {
        return new TaskPOJO(
                task.getSubject(),
                task.getType().toString(),
                task.getDeadlineStr(),
                task.getTextTask(),
                task.isComplete()
        );
    }
}
