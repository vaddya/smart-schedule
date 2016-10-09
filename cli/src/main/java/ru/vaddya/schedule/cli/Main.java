package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.ScheduleAPI;

/**
 * Created by Vadim on 9/22/2016.
 */
public class Main {
    public static void main(String[] args) {

        ScheduleAPI schedule = new Schedule();
        System.out.println("Активные задания:");
        schedule.getActiveTasks().forEach(System.out::println);

        System.out.println("\nПросроченные задания:");
        schedule.getOverdueTasks().forEach(System.out::println);

        System.out.println("\nРасписание");
        System.out.println(schedule.getWeek());

        System.out.println("Выполненные задания:");
        schedule.getCompletedTasks().forEach(System.out::println);
    }
}
