package ru.vaddya.schedule.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule implements ScheduleAPI {

    private List<Task> activeTasks;
    private List<Task> completedTasks;
    private StudyWeek week;

    public Schedule() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            this.activeTasks = JsonParser.parseTasks(classLoader.getResource("tasks.json").getPath());
            this.completedTasks = JsonParser.parseTasks(classLoader.getResource("completed.json").getPath());
            this.week = JsonParser.parseWeek(classLoader.getResource("schedule.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Schedule(String test) {
        this.activeTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
        this.week = new StudyWeek();
    }

    public void addLesson(DaysOfWeek day, Lesson lesson) {
        week.addLesson(day, lesson);
    }

    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        week.removeLesson(day, lesson);
    }

    public void addTask(Task task) {
        activeTasks.add(task);
    }

    public void completeTask(Task task) {
        activeTasks.remove(task);
        completedTasks.add(task);
    }

    public void removeTask(Task task) {
        activeTasks.remove(task);
        completedTasks.remove(task);
    }

    public Task getTaskByText(String taskText) {
        for (Task task : activeTasks) {
            if (task.getTextTask().equals(taskText)) {
                return task;
            }
        }
        for (Task task : completedTasks) {
            if (task.getTextTask().equals(taskText)) {
                return task;
            }
        }
        return null;
    }

    public StudyDay getDay(DaysOfWeek day) {
        return week.getDay(day);
    }

    public List<Task> getActiveTasks() {
        return activeTasks;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public List<Task> getOverdueTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Task task : activeTasks) {
            if (task.getDeadline().before(new Date())) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public StudyWeek getWeek() {
        return week;
    }
}
