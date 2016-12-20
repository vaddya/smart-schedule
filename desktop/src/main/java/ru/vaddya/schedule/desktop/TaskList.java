package ru.vaddya.schedule.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.tasks.Task;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 16, 2016
 */
public class TaskList {

    private StudyTasks tasks;

    private ObservableList<TaskListItem> items = FXCollections.observableArrayList();

    public TaskList(StudyTasks tasks) {
        this.tasks = tasks;
        for (Task task : tasks) {
            items.add(new TaskListItem(task));
        }
        items.addListener((ListChangeListener<TaskListItem>) c -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    for (TaskListItem item : c.getList()) {
                        tasks.updateTask(item.toTask());
                    }
                } else {
                    for (TaskListItem item : c.getRemoved()) {
                        tasks.removeTask(item.toTask());
                    }
                    for (TaskListItem item : c.getAddedSubList()) {
                        tasks.addTask(item.toTask());
                    }
                }
            }
        });
    }

    public ObservableList<TaskListItem> getItems() {
        return items;
    }

    public int count() {
        return items.size();
    }
}
