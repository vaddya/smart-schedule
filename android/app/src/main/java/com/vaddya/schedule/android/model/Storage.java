package com.vaddya.schedule.android.model;

import java.util.ArrayList;
import java.util.List;

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
public class Storage {

    private static List<Task> tasks = new ArrayList<>();

    public static void addTasks(List<Task> newTasks) {
        tasks.addAll(newTasks);
    }

    public static List<Task> getTasks() {
        return tasks;
    }

}