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
import java.util.Optional;
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
    public String tasks(@RequestParam(defaultValue = "all") String filter,
                        @RequestParam Optional<String> subject,
                        @RequestParam Optional<String> deadline) {
        List<Task> tasks;

        switch (filter.toLowerCase()) {
            case "all":
                tasks = schedule.getTasks().getAllTasks();
                break;
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

        if (subject.isPresent()) {
            tasks = tasks.stream()
                    .filter(task -> task.getSubject().equals(subject.get()))
                    .collect(toList());
        }

        if (deadline.isPresent()) {
            try {
                List<Integer> dates = Stream.of(deadline.get().split("-"))
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