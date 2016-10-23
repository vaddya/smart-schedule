package ru.vaddya.schedule.core.io.json;

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
import java.util.List;
import java.util.Scanner;

/**
 * Реализация взаимодействия с Json файлами
 * Не ревьюить
 *
 * @author vaddya
 */
public class JsonParser {

    public static List<Task> parseTasks(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path), "UTF-8");
        StringBuilder builder = new StringBuilder();

        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }
        in.close();

        List<Task> tasks = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                tasks.add(new Task.Builder()
                        .subject(object.getString("subject"))
                        .type(object.getString("type"))
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
        StringBuilder builder = new StringBuilder();

        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }
        in.close();

        StudyWeek week = new StudyWeek();
        try {
            JSONObject object = new JSONObject(builder.toString());
            for (DaysOfWeek value : DaysOfWeek.values()) {
                JSONArray day = object.getJSONArray(value.toString().toLowerCase());
                week.set(value, parseDay(day));
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
                        .type(object.getString("type"))
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
