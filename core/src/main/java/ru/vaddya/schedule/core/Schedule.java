package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.io.Database;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see ScheduleAPI
 */
public class Schedule implements ScheduleAPI {

    private StudyTasks tasks;
    private StudyWeek week;

    // TODO: 10/23/2016 куда ее?
    private static Database db = Database.getConnection();

    public Schedule() {
        this.tasks = new StudyTasks();
        this.week = new StudyWeek();
    }

    public static Database db() {
        return db;
    }

    @Override
    public void addLesson(DaysOfWeek day, Lesson lesson) {
        week.get(day).add(lesson);
    }

    @Override
    public void updateLesson(DaysOfWeek day, Lesson lesson) {
        week.get(day).update(lesson);
    }

    @Override
    public void changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {

    }

    @Override
    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        week.get(day).remove(lesson);
    }

    @Override
    public Lesson getLesson(DaysOfWeek day, String id) {
        return week.get(day).getById(id);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public Task getTask(String id) {
        return tasks.get(id);
    }

    @Override
    public void completeTask(Task task) {
        task.setComplete(true);
    }

    @Override
    public void updateTask(Task task) {
        tasks.update(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public List<StudyDay> getAllDays() {
        return week.getAll();
    }

    @Override
    public StudyDay getDay(DaysOfWeek day) {
        return week.get(day);
    }

    @Override
    public List<Task> getTasks() {
        return tasks.getAll();
    }

    @Override
    public List<Task> getActiveTasks() {
        return tasks.getActive();
    }

    @Override
    public List<Task> getCompletedTasks() {
        return tasks.getCompleted();
    }

    @Override
    public List<Task> getOverdueTasks() {
        return tasks.getOverdue();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (!week.get(day).isEmpty()) {
                builder.append(day.ru())
                        .append('\n')
                        .append(week.get(day));
            }
        }
        return builder.toString();
    }
}
