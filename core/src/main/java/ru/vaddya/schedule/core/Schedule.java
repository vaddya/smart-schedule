package ru.vaddya.schedule.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Vadim on 9/18/2016.
 */
public class Schedule {

    private List<Lesson> lessons = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Task> completedTasks = new ArrayList<>();

    public Schedule() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            readTasks(tasks, classLoader.getResource("tasks.json").getPath());
            readTasks(completedTasks, classLoader.getResource("completed.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    private void readTasks(Collection<Task> collection, String path) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path), "UTF-8");
        String data = "";

        while (in.hasNextLine()) {
            data += in.nextLine();
        }
        in.close();

        try {
            JSONArray jsonarray = new JSONArray(data);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String subject = jsonobject.getString("subject");
                String deadline = jsonobject.getString("deadline");
                String textTask = jsonobject.getString("textTask");
                collection.add(new Task(subject, deadline, textTask));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
