package com.vaddya.schedule.rest.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.exception.DuplicateIdException;
import com.vaddya.schedule.rest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.from;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @Autowired
    private Gson gson;

    @Autowired
    private SmartSchedule schedule;

    @RequestMapping(method = GET, produces = "application/json")
    public ResponseEntity<String> getAllTasks(@RequestParam(defaultValue = "all") String filter,
                                              @RequestParam(required = false) String subject,
                                              @RequestParam(required = false) String deadline) {
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
                return getMessageResponse(BAD_REQUEST, "Filter is invalid");
        }
        if (subject != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getSubject().equals(subject))
                    .collect(toList());
        }
        if (deadline != null) {
            try {
                LocalDate date = from(DATE_FORMAT.parse(deadline));
                tasks = tasks.stream()
                        .filter(task -> task.getDeadline().isBefore(date))
                        .collect(toList());
            } catch (Exception e) {
                return getMessageResponse(BAD_REQUEST, "Deadline format is invalid");
            }
        }
        if (tasks.isEmpty()) {
            return getResponse(NO_CONTENT);
        }
        return getBodyResponse(OK, gson.toJson(tasks));
    }

    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createTask(@RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            schedule.getTasks().addTask(task);
            return getMessageResponse(CREATED, "Task created, id=" + task.getId());
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (DuplicateIdException e) {
            return getMessageResponse(CONFLICT, "Task with specified ID already exists");
        }
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<String> deleteAllTasks() {
        schedule.getTasks().removeAllTasks();
        return getResponse(NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
    public ResponseEntity<String> getTask(@PathVariable String id) {
        try {
            Task task = schedule.getTasks().findTask(UUID.fromString(id));
            return new ResponseEntity<>(gson.toJson(task), OK);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    @RequestMapping(value = "/{id}", method = PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateTask(@PathVariable String id, @RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            UUID pathId = UUID.fromString(id);
            if (!task.getId().equals(pathId)) {
                return getMessageResponse(BAD_REQUEST, "ID in path and in body do not match");
            }
            schedule.getTasks().updateTask(task);
            return getResponse(NO_CONTENT);
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            Task task = schedule.getTasks().findTask(UUID.fromString(id));
            schedule.getTasks().removeTask(task);
            return getResponse(NO_CONTENT);
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    private ResponseEntity<String> getResponse(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    private ResponseEntity<String> getBodyResponse(HttpStatus status, String body) {
        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<String> getMessageResponse(HttpStatus status, String message) {
        Response response = new Response(status, message);
        return new ResponseEntity<>(gson.toJson(response), response.getStatus());
    }

}