package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Реализация интерфейса приложения Smart Schedule
 *
 * @author vaddya
 * @see ScheduleAPI
 */
public class Schedule implements ScheduleAPI {

    private final StudyTasks tasks = new StudyTasks();

    private final StudyWeek week = new StudyWeek();

    private static final Database db = Database.getConnection();

    public static Database db() {
        return db;
    }

    @Override
    public void addLesson(DaysOfWeek day, Lesson lesson) {
        week.addLesson(day, lesson);
    }

    @Override
    public Lesson findLesson(UUID id) {
        return week.findLesson(id);
    }

    @Override
    public Lesson findLesson(DaysOfWeek day, int index) {
        return week.findLesson(day, index);
    }

    @Override
    public void updateLesson(DaysOfWeek day, Lesson lesson) {
        week.updateLesson(day, lesson);
    }

    @Override
    public void changeLessonDay(DaysOfWeek from, DaysOfWeek to, Lesson lesson) {
        week.changeLessonDay(from, to, lesson);
    }

    @Override
    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        week.removeLesson(day, lesson);
    }

    @Override
    public List<Lesson> getLessons(DaysOfWeek day) {
        return week.getLessons(day);
    }

    @Override
    public Map<DaysOfWeek, List<Lesson>> getAllLessons() {
        return week.getAllLessons();
    }

    @Override
    public void addTask(Task task) {
        tasks.addTask(task);
    }

    @Override
    public Task findTask(UUID id) {
        return tasks.findTask(id);
    }

    @Override
    public Task findTask(int index) {
        return tasks.findTask(index);
    }

    @Override
    public void completeTask(Task task) {
        tasks.completeTask(task);
    }

    @Override
    public void updateTask(Task task) {
        tasks.updateTask(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.removeTask(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.getAllTasks();
    }

    @Override
    public List<Task> getActiveTasks() {
        return tasks.getActiveTasks();
    }

    @Override
    public List<Task> getCompletedTasks() {
        return tasks.getCompletedTasks();
    }

    @Override
    public List<Task> getOverdueTasks() {
        return tasks.getOverdueTasks();
    }

    @Override
    public String toString() {
        return week.toString();
    }
}
