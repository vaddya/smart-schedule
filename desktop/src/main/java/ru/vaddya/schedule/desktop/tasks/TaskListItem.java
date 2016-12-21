package ru.vaddya.schedule.desktop.tasks;

import javafx.beans.property.SimpleStringProperty;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.desktop.Main;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс-обертка для задания
 *
 * @author vaddya
 * @see Task
 */
public class TaskListItem {

    private UUID id;
    private SimpleStringProperty progress;
    private SimpleStringProperty subject;
    private SimpleStringProperty type;
    private SimpleStringProperty deadline;
    private SimpleStringProperty text;

    public TaskListItem() {
        this.id = UUID.randomUUID();
        this.progress = new SimpleStringProperty(Main.bundle.getString("task_incomplete"));
        this.deadline = new SimpleStringProperty(Dates.FULL_DATE_FORMAT.format(LocalDate.now()));
        this.subject = new SimpleStringProperty("");
        this.type = new SimpleStringProperty(LessonType.ANOTHER.toString());
        this.text = new SimpleStringProperty("");
    }

    public TaskListItem(Task task) {
        this.id = task.getId();
        this.progress = new SimpleStringProperty(Main.bundle.getString(task.isComplete() ? "task_complete" : "task_incomplete"));
        this.deadline = new SimpleStringProperty(Dates.FULL_DATE_FORMAT.format(task.getDeadline()));
        this.subject = new SimpleStringProperty(task.getSubject());
        this.type = new SimpleStringProperty(task.getType().toString());
        this.text = new SimpleStringProperty(task.getTextTask());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProgress() {
        return progress.get();
    }

    public boolean isDone() {
        return progress.get().equals(Main.bundle.getString("task_complete"));
    }

    public void setProgress(String progress) {
        this.progress.set(progress);
    }

    public void setDone(boolean done) {
        this.progress.set(Main.bundle.getString(done ? "task_complete" : "task_incomplete"));
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setType(LessonType type) {
        this.type.set(type.toString());
    }

    public String getDeadline() {
        return deadline.get();
    }

    public LocalDate getDateDeadline() {
        return LocalDate.from(Dates.FULL_DATE_FORMAT.parse(deadline.get()));
    }

    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public SimpleStringProperty progressProperty() {
        return progress;
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleStringProperty deadlineProperty() {
        return deadline;
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public Task toTask() {
        return new Task.Builder()
                .id(id)
                .isComplete(progress.get().equals(Main.bundle.getString("task_complete")))
                .subject(subject.get())
                .type(LessonType.valueOf(type.get()))
                .deadline(Dates.FULL_DATE_FORMAT.parse(deadline.get()))
                .textTask(text.get())
                .build();
    }
}
