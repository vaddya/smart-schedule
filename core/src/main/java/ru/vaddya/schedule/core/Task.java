package ru.vaddya.schedule.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vadim on 10/5/2016.
 */
public class Task {

    private String subject;
    private Date deadline;
    private String textTask;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.Y", new Locale("ru"));

    public Task(String subject, String deadline, String textTask) {
        this.subject = subject;
        this.textTask = textTask;

        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
        try {
            this.deadline = parser.parse(deadline);
        } catch (ParseException e) {
            this.deadline = new Date();
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    @Override
    public String toString() {
        return dateFormat.format(deadline)
                + " | " + subject
                + ": " + textTask;
    }
}