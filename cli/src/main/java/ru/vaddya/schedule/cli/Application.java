package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.lessons.LessonType;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.Scanner;

/**
 * Класс для консольного взаимодействия с пользователем
 *
 * @author vaddya
 */
public class Application {

    private Schedule schedule = new SmartSchedule();
    private Scanner in = new Scanner(System.in, "UTF-8");
    private PrintStream out = System.out;

    private static final String CANCEL = "q";

    public void parseInput() {
        String request;
        out.print(">> ");
        while (!CANCEL.equals(request = in.next())) {
            switch (request) {
                case "schedule":
                    printSchedule();
                    break;
                case "tasks":
                    printTasks();
                    break;
                case "active":
                    printActive();
                    break;
                case "completed":
                    printCompleted();
                    break;
                case "overdue":
                    printOverdue();
                    break;
                case "add":
                    parseAdd();
                    break;
                case "remove":
                    parseRemove();
                    break;
                default:
                    printHelp();
                    break;
            }
            out.print(">> ");
        }
    }

    public void printSchedule() {
        out.println("\nРасписание");
        out.println(schedule.getCurrentWeek());
    }

    public void printTasks() {
        out.println("\nЗадания:");
        out.println(schedule.getTasks());
    }

    public void printActive() {
        out.println("\nАктивные задания:");
        schedule.getTasks().getActiveTasks().forEach(System.out::println);
    }

    public void printCompleted() {
        out.println("\nВыполненные задания:");
        schedule.getTasks().getCompletedTasks().forEach(System.out::println);
    }

    public void printOverdue() {
        out.println("\nПросроченные задания:");
        schedule.getTasks().getOverdueTasks().forEach(System.out::println);
    }

    public void printHelp() {
        out.println("Usage of Smart SmartSchedule: ");
        out.println("\t>> schedule - print schedule");
        out.println("\t>> tasks - print all tasks");
        out.println("\t>> active - print active tasks");
        out.println("\t>> completed - print completed tasks");
        out.println("\t>> overdue - print overdue tasks");
        out.println("\t>> add lesson - add lesson to the schedule");
        out.println("\t>> add task - add task to the task list");
        out.println("\t>> done - mark the task completed");
        out.println("\t>> undone - mark the task uncompleted");
        out.println("\t>> remove lesson - remove lesson from the schedule");
        out.println("\t>> remove task - remove task from the task list");
        out.println("\t>> help - print help");
    }

    private void parseAdd() {
        String kind = in.next();
        if ("lesson".equals(kind)) {
            out.print("Day of week: ");
            DayOfWeek day = DayOfWeek.valueOf(in.next().toUpperCase());
            schedule.getDay(day).addLesson(parseLesson());
        } else if ("task".equals(kind)) {
            schedule.getTasks().addTask(parseTask());
        } else {
            printHelp();
        }
    }

    private void parseRemove() {
        String kind = in.next();
        if ("lesson".equals(kind)) {
            out.print("Day of week: ");
            DayOfWeek day = DayOfWeek.valueOf(in.next().toUpperCase());
            out.print("Lesson number: ");
            int index = in.nextInt();
            schedule.getDay(day).removeLesson(index);
        } else if ("task".equals(kind)) {
            out.print("Task index: ");
            int index = in.nextInt();
            Task task = schedule.getTasks().findTask(index);
            schedule.getTasks().removeTask(task);
        } else {
            printHelp();
        }
    }

    private Task parseTask() {
        Task.Builder builder = new Task.Builder();
        out.print("Subject: ");
        builder.subject(in.next());
        out.print("Lesson type: ");
        builder.type(LessonType.valueOf(in.next()));
        out.print("Deadline: ");
        builder.deadline(Dates.parseShort(in.next()));
        out.print("Task: ");
        builder.textTask(in.next());
        return builder.build();
    }

    private Lesson parseLesson() {
        Lesson.Builder builder = new Lesson.Builder();
        out.print("Start time: ");
        builder.startTime(in.next());
        out.print("End time: ");
        builder.endTime(in.next());
        out.print("Subject: ");
        builder.subject(in.next());
        out.print("Lesson type: ");
        builder.type(in.next());
        out.print("Place: ");
        builder.place(in.next());
        out.print("Teacher: ");
        builder.teacher(in.next());
        return builder.build();
    }
}
