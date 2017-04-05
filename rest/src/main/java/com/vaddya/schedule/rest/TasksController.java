package com.vaddya.schedule.rest;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.mongo.MongoDatabase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 05, 2017
 */
@RestController
public class TasksController {

    private static Gson gson = new Gson();

    private SmartSchedule schedule = new SmartScheduleImpl(new MongoDatabase(new MongoClient()));

    @RequestMapping("/tasks")
    public String tasks(@RequestParam(value = "filter", required = false) String filter,
                        @RequestParam(value = "subject", required = false) String subject,
                        @RequestParam(value = "deadline", required = false) String deadline) {
        List<Task> tasks;

        if (filter != null) {
            switch (filter.toLowerCase()) {
                case "active":
                    tasks = schedule.getTasks().getActiveTasks();
                    break;
                case "completed":
                    tasks = schedule.getTasks().getCompletedTasks();
                    break;
                case "overdue":
                    tasks = schedule.getTasks().getOverdueTasks();
                    break;
                default:
                    return "filter error";
            }
        } else {
            tasks = schedule.getTasks().getAllTasks();
        }

        if (subject != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getSubject().equals(subject))
                    .collect(toList());
        }

        if (deadline != null) {
            try {
                List<Integer> dates = Stream.of(deadline.split("-"))
                        .map(Integer::parseInt)
                        .collect(toList());
                LocalDate date = LocalDate.of(dates.get(2), dates.get(1), dates.get(0));
                tasks = tasks.stream()
                        .filter(task -> task.getDeadline().isBefore(date))
                        .collect(toList());
            } catch (Exception e) {
                return "deadline error " + e.getMessage();
            }
        }

        return gson.toJson(tasks, ArrayList.class);
    }

}