package com.vaddya.schedule.cli;

import com.mongodb.MongoClient;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.schedule.StudySchedule;
import com.vaddya.schedule.core.tasks.StudyTasks;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekTime;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.mongo.MongoDatabase;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Scanner;

import static com.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;
import static com.vaddya.schedule.core.utils.WeekType.EVEN;
import static com.vaddya.schedule.core.utils.WeekType.ODD;

/**
 * Класс для консольного взаимодействия с пользователем
 *
 * @author vaddya
 */
public class Application {

    private final SmartSchedule schedule;
    private final StudyWeek week;
    private final StudyTasks tasks;
    private final Scanner in = new Scanner(System.in, "UTF-8");
    private final PrintStream out = System.out;
    private WeekTime weekTime = WeekTime.current();
    private static final String CANCEL = "q";

    public Application() {
        MongoClient client = new MongoClient();
        Database database = new MongoDatabase(client);
        schedule = new SmartScheduleImpl(database);
        week = schedule.getCurrentWeek();
        tasks = schedule.getTasks();

        printWeek("Current Week", week);
        printTasks("All tasks", tasks.getAllTasks());
        parseInput();
    }

    public void parseInput() {
        String request;
        out.print(">> ");
        while (!CANCEL.equals(request = in.nextLine())) {
            switch (request) {
                case "schedule":
                    printSchedule("Current schedule", schedule.getCurrentSchedule());
                    break;
                case "odd":
                    printSchedule("Odd schedule", schedule.getSchedule(ODD));
                    break;
                case "even":
                    printSchedule("Even schedule", schedule.getSchedule(EVEN));
                    break;
                case "current":
                    printWeek("Current week", schedule.getCurrentWeek());
                    break;
                case "next":
                    weekTime = WeekTime.after(weekTime);
                    printWeek("Next week", schedule.getWeek(weekTime));
                    break;
                case "prev":
                    weekTime = WeekTime.before(weekTime);
                    printWeek("Previous week", schedule.getWeek(weekTime));
                    break;
                case "swapWeekTypes":
                    schedule.swapSchedules();
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

    public void printSchedule(String title, StudySchedule schedule) {
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
        out.println("Usage of Smart StudySchedule: ");
        out.println("\t>> schedule - print schedule");
        out.println("\t>> tasks - print all tasks");
        out.println("\t>> active - print active tasks");
        out.println("\t>> completed - print completed tasks");
        out.println("\t>> overdue - print overdue tasks");
        out.println("\t>> insert lesson - insert lesson to the schedule");
        out.println("\t>> insert task - insert task to the task list");
        out.println("\t>> done - mark the task completed");
        out.println("\t>> undone - mark the task uncompleted");
        out.println("\t>> remove lesson - remove lesson from the schedule");
        out.println("\t>> remove task - remove task from the task list");
        out.println("\t>> help - print help");
    }

    private void parseAdd() {
        String kind = in.nextLine();
        if ("lesson".equals(kind)) {
            out.print("Day of week: ");
            DayOfWeek day = DayOfWeek.valueOf(in.nextLine().toUpperCase());
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
        String kind = in.nextLine();
        if ("lesson".equals(kind)) {
            out.print("Day of week: ");
            DayOfWeek day = DayOfWeek.valueOf(in.nextLine().toUpperCase());
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
        builder.subject(in.nextLine());
        out.print("Lesson type: ");
        builder.type(LessonType.valueOf(in.nextLine()));
        out.print("Deadline: ");
        builder.deadline(FULL_DATE_FORMAT.parse(in.nextLine()));
        out.print("Task: ");
        builder.textTask(in.nextLine());
        return builder.build();
    }

    private Lesson parseLesson() {
        Lesson.Builder builder = new Lesson.Builder();
        out.print("Start time: ");
        builder.startTime(in.nextLine());
        out.print("End time: ");
        builder.endTime(in.nextLine());
        out.print("Subject: ");
        builder.subject(in.nextLine());
        out.print("Lesson type: ");
        builder.type(in.nextLine());
        out.print("Place: ");
        builder.place(in.nextLine());
        out.print("Teacher: ");
        builder.teacher(in.nextLine());
        return builder.build();
    }
}
