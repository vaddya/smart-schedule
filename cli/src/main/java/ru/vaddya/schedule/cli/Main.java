package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Lesson;
import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Vadim on 9/22/2016.
 */
public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        System.out.println("Активные задачи:");
        schedule.getTasks().forEach(System.out::println);

        System.out.println("\nВыполненные задачи:");
        schedule.getCompletedTasks().forEach(System.out::println);

        System.out.println("Расписание");
        // TODO: 10/5/2016   
    }
}
