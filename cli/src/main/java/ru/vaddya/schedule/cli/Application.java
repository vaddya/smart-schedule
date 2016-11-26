package ru.vaddya.schedule.cli;

import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.Schedule;
import ru.vaddya.schedule.core.lessons.StudyWeek;
import ru.vaddya.schedule.core.tasks.StudyTasks;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.LessonType;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Scanner;

import static ru.vaddya.schedule.core.utils.Dates.SHORT_DATE_FORMAT;

/**
 * Класс для консольного взаимодействия с пользователем
 *
 * @author vaddya
 */
public class Application {

    private final SmartSchedule schedule = new SmartScheduleImpl();
    private final StudyWeek week = schedule.getCurrentWeek();
    private final StudyTasks tasks = schedule.getTasks();
    private final Scanner in = new Scanner(System.in, "UTF-8");
    private final PrintStream out = System.out;

    private static final String CANCEL = "q";

    public Application() {
        printWeek("Current Week", week);
        printTasks("All tasks", tasks.getAllTasks());
        parseInput();
    }

    public void parseInput() {
        String request;
        out.print(">> ");
        while (!CANCEL.equals(request = in.next())) {
            switch (request) {
                case "current":
                    printSchedule("Current schedule", schedule.getCurrentSchedule());
                    break;
                case "next":
                    printWeek("Current week", schedule.getCurrentWeek());
                    break;
                case "tasks":
                    printTasks("All tasks", tasks.getAllTasks());
                    break;
                case "active":
                    printTasks("Active tasks", tasks.getActiveTasks());
                    break;
                case "completed":
                    printTasks("Completed tasks", tasks.getCompletedTasks());
                    break;
                case "overdue":
                    printTasks("Overdue tasks", tasks.getOverdueTasks());
                    break;
                case "add":
                    parseAdd();
                    break;
                case "done":
                    parseComplete(true);
                    break;
                case "undone":
                    parseComplete(false);
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

    public void printSchedule(String title, Schedule schedule) {
        out.println("\n" + title);
        out.println(schedule);
    }

    public void printWeek(String title, StudyWeek week) {
        out.println("\n" + title);
        out.println(week);
    }

    public void printTasks(String title, List<Task> tasks) {
        out.println("\n" + title + ":");
        tasks.forEach(out::println);
    }

    public void printHelp() {
        out.println("Usage of Smart Schedule: ");
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
            week.getDay(day).addLesson(parseLesson());
        } else if ("task".equals(kind)) {
            tasks.addTask(parseTask());
        } else {
            printHelp();
        }
    }


    private void parseComplete(boolean isComplete) {
        out.println("Task index: ");
        int index = in.nextInt();
        Task task = tasks.findTask(index);
        task.setComplete(isComplete);
        tasks.updateTask(task);
    }

    private void parseRemove() {
        String kind = in.next();
        if ("lesson".equals(kind)) {
            out.print("Day of week: ");
            DayOfWeek day = DayOfWeek.valueOf(in.next().toUpperCase());
            out.print("Lesson number: ");
            int index = in.nextInt();
            week.getDay(day).removeLesson(week.getDay(day).findLesson(index));
        } else if ("task".equals(kind)) {
            out.print("Task index: ");
            int index = in.nextInt();
            Task task = tasks.findTask(index);
            tasks.removeTask(task);
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
        builder.deadline(SHORT_DATE_FORMAT.parse(in.next()));
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
