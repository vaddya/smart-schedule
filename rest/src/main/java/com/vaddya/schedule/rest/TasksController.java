package com.vaddya.schedule.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoClient;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.mongo.MongoDatabase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 05, 2017
 */
@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    public static final DateTimeFormatter DATE_FORMAT = ofPattern("dd-MM-yyyy");

    private Gson gson;
    private SmartSchedule schedule;

    public TasksController() {
        schedule = new SmartScheduleImpl(new MongoDatabase(new MongoClient()));

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Task.class, new TaskSerializer());
        gson = builder.create();
    }

    @RequestMapping(method = GET, produces = "application/json")
    public ResponseEntity<String> tasks(@RequestParam(defaultValue = "all") String filter,
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
                return new ResponseEntity<>("Query parameters is invalid: filter=" + filter, BAD_REQUEST);
        }

        if (subject.isPresent()) {
            tasks = tasks.stream()
                    .filter(task -> task.getSubject().equals(subject.get()))
                    .collect(toList());
        }

        if (deadline.isPresent()) {
            try {
                LocalDate date = LocalDate.from(DATE_FORMAT.parse(deadline.get()));
                tasks = tasks.stream()
                        .filter(task -> task.getDeadline().isBefore(date))
                        .collect(toList());
            } catch (Exception e) {
                return new ResponseEntity<>("Query parameter is invalid: deadline=" + deadline.get(), BAD_REQUEST);
            }
        }

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }

        return new ResponseEntity<>(gson.toJson(tasks), OK);
    }

    @RequestMapping(method = POST, produces = "application/json")
    public ResponseEntity<String> createTask(@RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            schedule.getTasks().addTask(task);
            return new ResponseEntity<>("Task created, id=" + task.getId(), OK);
        } catch (JsonSyntaxException e) {
            return new ResponseEntity<>("Task syntax is invalid: " + e.getMessage(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
    public ResponseEntity<String> task(@PathVariable String id) {
        try {
            Task task = schedule.getTasks().findTask(UUID.fromString(id));
            return new ResponseEntity<>(gson.toJson(task), OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Query parameter is invalid: id=" + id, BAD_REQUEST);
        } catch (NoSuchTaskException e) {
            return new ResponseEntity<>("Task doesn't exist: id=" + id, NOT_FOUND);
        }
    }

}