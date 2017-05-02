package com.vaddya.schedule.rest.controllers;

import com.google.gson.JsonSyntaxException;
import com.vaddya.schedule.core.exceptions.DuplicateIdException;
import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.tasks.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.from;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

/**
 * Контроллер для заданий
 *
 * @author vaddya
 */
@RestController
@RequestMapping(Paths.TASKS)
public class TasksController extends Controller {

    @GetMapping(produces = JSON)
    public ResponseEntity<String> getAllTasks(@RequestParam(defaultValue = "all") String filter,
                                              @RequestParam(required = false) String subject,
                                              @RequestParam(required = false) String deadline) {
        List<Task> tasks;
        try {
            tasks = findTasks(filter);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, e.getMessage());
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
            } catch (DateTimeParseException e) {
                return getMessageResponse(BAD_REQUEST, "Deadline format is invalid");
            }
        }
        return getBodyResponse(OK, gson.toJson(tasks));
    }

    @PostMapping(consumes = JSON, produces = JSON)
    public ResponseEntity<String> createTask(@RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            schedule.getTasks().addTask(task);
            return getResponseCreated(CREATED, "Task created", Paths.TASKS, task.getId());
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (DuplicateIdException e) {
            return getMessageResponse(CONFLICT, "Task with specified ID already exists");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTasks() {
        schedule.getTasks().removeAllTasks();
        return getResponse(NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<String> getTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Task task = schedule.getTasks().findTask(uuid);
            return getBodyResponse(OK, gson.toJson(task));
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    @PutMapping(value = "/{id}", consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateTask(@PathVariable String id,
                                             @RequestBody String body) {
        try {
            Task task = gson.fromJson(body, Task.class);
            UUID pathId = UUID.fromString(id);
            if (!task.getId().equals(pathId)) {
                return getMessageResponse(BAD_REQUEST, "ID in path and in body do not match");
            }
            schedule.getTasks().updateTask(task);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (JsonSyntaxException e) {
            return getMessageResponse(BAD_REQUEST, "Task syntax is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    @DeleteMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Task task = schedule.getTasks().findTask(uuid);
            schedule.getTasks().removeTask(task);
            return getResponse(NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return getMessageResponse(BAD_REQUEST, "Task ID format is invalid");
        } catch (NoSuchTaskException e) {
            return getMessageResponse(NOT_FOUND, "Task does not exist");
        }
    }

    private List<Task> findTasks(String filter) {
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
                throw new IllegalArgumentException("Filter is invalid");
        }
        return tasks;
    }

}