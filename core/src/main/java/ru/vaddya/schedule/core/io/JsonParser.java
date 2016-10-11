package ru.vaddya.schedule.core.io;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.StudyDay;
import ru.vaddya.schedule.core.StudyWeek;
import ru.vaddya.schedule.core.Task;
import ru.vaddya.schedule.core.utils.DaysOfWeek;

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
                tasks.add(new Task.Builder()
                        .subject(object.getString("subject"))
                        .lessonType(object.getString("lessonType"))
                        .deadline(object.getString("deadline"))
                        .textTask(object.getString("textTask"))
                        .isComplete(object.getBoolean("isComplete"))
                        .build()
                );
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
                lessons.add(new Lesson.Builder()
                        .startTime(object.getString("startTime"))
                        .endTime(object.getString("endTime"))
                        .subject(object.getString("subject"))
                        .lessonType(object.getString("lessonType"))
                        .place(object.getString("place"))
                        .teacher(object.getString("teacher"))
                        .build()
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new StudyDay(lessons);
    }
}
