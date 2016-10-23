package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.ScheduleAPI;

/**
 * Класс демонстрирующий текущее расписание занятий и задания
 *
 * @author vaddya
 */
public class Main {
    public static void main(String[] args) {
        // TODO: 10/23/2016 Добавить взаимодействие с пользователем
        ScheduleAPI schedule = new Schedule();

        System.out.println("Активные задания:");
        schedule.getActiveTasks().forEach(System.out::println);

        System.out.println("\nПросроченные задания:");
        schedule.getOverdueTasks().forEach(System.out::println);

        System.out.println("\nВыполненные задания:");
        schedule.getCompletedTasks().forEach(System.out::println);

        System.out.println("\nРасписание");
        System.out.println(schedule);
    }
}
