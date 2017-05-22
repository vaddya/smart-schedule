package com.vaddya.schedule.android.model;

import com.android.internal.util.Predicate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaddya.schedule.android.rest.LocalDateSerializer;
import com.vaddya.schedule.android.rest.ScheduleService;
import com.vaddya.schedule.android.rest.TaskService;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
public class Storage {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MM-YYYY");


    private static List<Task> tasks = new ArrayList<>();
    private static List<Lesson> lessons = new ArrayList<>();

    private static TaskService taskService;
    private static ScheduleService scheduleService;

    static {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://schedule.vaddya.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        taskService = retrofit.create(TaskService.class);
        scheduleService = retrofit.create(ScheduleService.class);

        // TODO: 5/4/2017 stub
//        for (int i = 0; i < 10; i++) {
//            Task task = new Task(UUID.randomUUID(),
//                    "Subject " + i,
//                    LessonType.values()[i % LessonType.values().length],
//                    new LocalDate().plusDays(i),
//                    "Text " + i,
//                    i % 2 == 0);
//            tasks.add(task);
//        }
//
//        for (int i = 0; i < 3; i++) {
//            Lesson lesson = new Lesson(UUID.randomUUID(),
//                    "" + (i+9) + ":00",
//                    "11:30",
//                    "Subject " + i,
//                    LessonType.values()[i % LessonType.values().length],
//                    "Place " + i,
//                    "Teacher " + i);
//            lessons.add(lesson);
//        }
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static void callTasks(final TasksType type, final Callback<List<Task>> callback) {
//        callback.onResponse(null, Response.success(getTasks(type)));
        taskService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                tasks = response.body();
                callback.onResponse(call, Response.success(getTasks(type)));
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public static List<Task> getTasks(TasksType type) {
        switch (type) {
            case ACTIVE:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return !task.isComplete();
                    }
                });
            case COMPLETED:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return task.isComplete();
                    }
                });
            default:
                return getTasks(new Predicate<Task>() {
                    @Override
                    public boolean apply(Task task) {
                        return true;
                    }
                });
        }
    }

    private static List<Task> getTasks(Predicate<Task> predicate) {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            if (predicate.apply(task)) {
                list.add(task);
            }
        }
        return list;
    }

    public static void callLessons(final LocalDate date, final Callback<Day> callback) {
//        callback.onResponse(null, Response.success(new Day(date, DayOfWeek.MONDAY, lessons)));
        scheduleService.getDay(DATE_FORMAT.print(date)).enqueue(callback);
    }

    public static List<Lesson> getLessons(LocalDate date) {
        return lessons;
    }

}