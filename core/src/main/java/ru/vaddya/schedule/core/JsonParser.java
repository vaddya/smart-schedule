package ru.vaddya.schedule.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * Created by Vadim on 10/6/2016.
 */
public class JsonParser {

    public static List<Task> parseTasks(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path), "UTF-8");
        String data = "";

        while (in.hasNextLine()) {
            data += in.nextLine();
        }
        in.close();

        List<Task> tasks = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String subject = object.getString("subject");
                String deadline = object.getString("deadline");
                String textTask = object.getString("textTask");
                tasks.add(new Task(subject, deadline, textTask));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static StudyWeek parseWeek(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path), "UTF-8");
        String data = "";

        while (in.hasNextLine()) {
            data += in.nextLine();
        }
        in.close();

        StudyWeek week = new StudyWeek();
        try {
            JSONObject object = new JSONObject(data);
            for (DaysOfWeek value : DaysOfWeek.values()) {
                JSONArray day = object.getJSONArray(value.toString().toLowerCase());
                week.setDay(value, parseDay(day));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return week;
    }

    private static StudyDay parseDay(JSONArray array) {

        List<Lesson> lessons = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String startTime = object.getString("startTime");
                String endTime = object.getString("endTime");
                String subject = object.getString("subject");
                String type = object.getString("type");
                String place = object.getString("place");
                String teacher = object.getString("teacher");
                lessons.add(new Lesson(startTime, endTime, subject, type, place, teacher));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new StudyDay(lessons);
    }
}
