package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.io.JsonParser;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule implements ScheduleAPI {

    private List<Task> tasks;
    private StudyWeek week;

    public Schedule() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            this.tasks = JsonParser.parseTasks(classLoader.getResource("tasks.json").getPath())
                    .stream()
                    .sorted(Task.DATE_ORDER)
                    .collect(Collectors.toList());
            this.week = JsonParser.parseWeek(classLoader.getResource("schedule.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Schedule(String test) {
        this.tasks = new ArrayList<>();
        this.week = new StudyWeek();
    }

    public void addLesson(DaysOfWeek day, Lesson lesson) {
        week.addLesson(day, lesson);
    }

    public void removeLesson(DaysOfWeek day, Lesson lesson) {
        week.removeLesson(day, lesson);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void completeTask(Task task) {
        task.setComplete(true);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public Lesson getLessonByDay(DaysOfWeek day, int i) {
        return week.getDay(day).getLesson(i);
    }

    public Task getTaskByText(String taskText) throws NoSuchElementException {
        return tasks
                .stream()
                .filter(task -> task.getTextTask().equals(taskText))
                .findFirst()
                .get();
    }

    public StudyDay getDay(DaysOfWeek day) {
        return week.getDay(day);
    }

    public List<Task> getTasks() {
        return tasks
                .stream()
                .collect(Collectors.toList());
    }

    public List<Task> getActiveTasks() {
        return tasks
                .stream()
                .filter((task) -> !task.isComplete())
                .collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return tasks
                .stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        return tasks
                .stream()
                .filter(task -> !task.isComplete())
                .filter(task -> new Date().after(task.getDeadline()))
                .collect(Collectors.toList());
    }

    public StudyWeek getWeek() {
        return week;
    }
}
