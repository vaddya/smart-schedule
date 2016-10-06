package ru.vaddya.schedule.core;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule {

    private List<Task> tasks;
    private List<Task> completedTasks;
    private StudyWeek week;

    public Schedule() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            this.tasks = JsonParser.parseTasks(classLoader.getResource("tasks.json").getPath());
            this.completedTasks = JsonParser.parseTasks(classLoader.getResource("completed.json").getPath());
            this.week = JsonParser.parseWeek(classLoader.getResource("schedule.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public StudyWeek getWeek() {
        return week;
    }
}
