package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.io.json.JsonParser;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule implements ScheduleAPI {

    private StudyTasks tasks;
    private StudyWeek week;

    public Schedule() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            this.tasks = new StudyTasks(
                    JsonParser.parseTasks(classLoader.getResource("tasks.json").getPath())
                            .stream()
                            .sorted(Task.DATE_ORDER)
                            .collect(Collectors.toList()));
            this.week = JsonParser.parseWeek(classLoader.getResource("schedule.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Schedule(String test) {
        this.tasks = new StudyTasks();
        this.week = new StudyWeek();
    }

    public void addLesson(DaysOfWeek day, Lesson lesson) {
        week.get(day).add(lesson);
    }

    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        week.get(day).remove(lesson);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void completeTask(Task task) {
        task.setComplete(true);
    }

    public void updateTask(Task task) {
        tasks.update(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public Lesson getLessonByDay(DaysOfWeek day, int index) {
        return week.get(day).get(index);
    }

    public List<StudyDay> getAllDays() {
        return week.getAll();
    }

    public StudyDay getDay(DaysOfWeek day) {
        return week.get(day);
    }

    public List<Task> getTasks() {
        return tasks.getAll();
    }

    public List<Task> getActiveTasks() {
        return tasks.getActive();
    }

    public List<Task> getCompletedTasks() {
        return tasks.getCompleted();
    }

    public List<Task> getOverdueTasks() {
        return tasks.getOverdue();
    }
}
